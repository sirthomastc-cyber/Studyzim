#!/usr/bin/env python3
"""
ZIMStudy AI — Web Edition
Pure Python standard library backend. No pip install required —
just Python 3.8+. Serves the frontend in html/ and a small JSON API
backed by a local SQLite file (created automatically in data/).
"""

import json
import os
import sqlite3
import time
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from urllib.parse import urlparse

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
HTML_DIR = os.path.join(BASE_DIR, "html")
DATA_DIR = os.path.join(BASE_DIR, "data")
DB_PATH = os.path.join(DATA_DIR, "zimstudy.db")

os.makedirs(DATA_DIR, exist_ok=True)

MIME_TYPES = {
    ".html": "text/html; charset=utf-8",
    ".css": "text/css; charset=utf-8",
    ".js": "application/javascript; charset=utf-8",
}


def get_db():
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    return conn


def init_db():
    conn = get_db()
    conn.executescript(
        """
        CREATE TABLE IF NOT EXISTS profile (
            id INTEGER PRIMARY KEY CHECK (id = 1),
            name TEXT,
            school TEXT,
            grade TEXT,
            exam_board TEXT,
            exam_year TEXT
        );

        CREATE TABLE IF NOT EXISTS subjects (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            target_grade TEXT DEFAULT 'A'
        );

        CREATE TABLE IF NOT EXISTS exams (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            subject_name TEXT NOT NULL,
            paper_number TEXT,
            exam_date TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS sessions (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            subject_name TEXT NOT NULL,
            topic TEXT,
            started_at INTEGER NOT NULL,
            duration_minutes INTEGER NOT NULL
        );
        """
    )
    conn.commit()
    conn.close()


class Handler(BaseHTTPRequestHandler):
    # --- helpers -----------------------------------------------------
    def _send_json(self, payload, status=200):
        body = json.dumps(payload).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def _send_file(self, path, content_type):
        try:
            with open(path, "rb") as f:
                body = f.read()
            self.send_response(200)
            self.send_header("Content-Type", content_type)
            self.send_header("Content-Length", str(len(body)))
            self.end_headers()
            self.wfile.write(body)
        except FileNotFoundError:
            self._send_json({"error": "not found"}, 404)

    def _read_json_body(self):
        length = int(self.headers.get("Content-Length", 0))
        if length == 0:
            return {}
        raw = self.rfile.read(length)
        try:
            return json.loads(raw)
        except json.JSONDecodeError:
            return {}

    def log_message(self, fmt, *args):
        pass  # keep the console quiet

    # --- routing -------------------------------------------------------
    def do_GET(self):
        path = urlparse(self.path).path

        if path in ("/", "/index.html"):
            return self._send_file(os.path.join(HTML_DIR, "index.html"), MIME_TYPES[".html"])
        if path in ("/style.css", "/app.js"):
            ext = os.path.splitext(path)[1]
            return self._send_file(os.path.join(HTML_DIR, path.lstrip("/")), MIME_TYPES[ext])

        if path == "/api/profile":
            conn = get_db()
            row = conn.execute("SELECT * FROM profile WHERE id = 1").fetchone()
            conn.close()
            return self._send_json(dict(row) if row else None)

        if path == "/api/subjects":
            conn = get_db()
            rows = conn.execute("SELECT * FROM subjects ORDER BY name").fetchall()
            conn.close()
            return self._send_json([dict(r) for r in rows])

        if path == "/api/exams":
            conn = get_db()
            rows = conn.execute("SELECT * FROM exams ORDER BY exam_date").fetchall()
            conn.close()
            return self._send_json([dict(r) for r in rows])

        if path == "/api/sessions":
            conn = get_db()
            rows = conn.execute("SELECT * FROM sessions ORDER BY started_at DESC").fetchall()
            conn.close()
            return self._send_json([dict(r) for r in rows])

        self._send_json({"error": "not found"}, 404)

    def do_POST(self):
        path = urlparse(self.path).path
        data = self._read_json_body()
        conn = get_db()

        if path == "/api/profile":
            conn.execute(
                """
                INSERT INTO profile (id, name, school, grade, exam_board, exam_year)
                VALUES (1, ?, ?, ?, ?, ?)
                ON CONFLICT(id) DO UPDATE SET
                    name=excluded.name, school=excluded.school, grade=excluded.grade,
                    exam_board=excluded.exam_board, exam_year=excluded.exam_year
                """,
                (
                    data.get("name", ""), data.get("school", ""), data.get("grade", ""),
                    data.get("exam_board", ""), data.get("exam_year", ""),
                ),
            )
            conn.commit(); conn.close()
            return self._send_json({"ok": True})

        if path == "/api/subjects":
            name = data.get("name", "").strip()
            if not name:
                conn.close()
                return self._send_json({"error": "name required"}, 400)
            conn.execute(
                "INSERT INTO subjects (name, target_grade) VALUES (?, ?)",
                (name, data.get("target_grade", "A")),
            )
            conn.commit(); conn.close()
            return self._send_json({"ok": True})

        if path == "/api/exams":
            conn.execute(
                "INSERT INTO exams (subject_name, paper_number, exam_date) VALUES (?, ?, ?)",
                (data.get("subject_name", ""), data.get("paper_number", ""), data.get("exam_date", "")),
            )
            conn.commit(); conn.close()
            return self._send_json({"ok": True})

        if path == "/api/sessions":
            conn.execute(
                "INSERT INTO sessions (subject_name, topic, started_at, duration_minutes) VALUES (?, ?, ?, ?)",
                (
                    data.get("subject_name", ""), data.get("topic", ""),
                    int(time.time()), int(data.get("duration_minutes", 0)),
                ),
            )
            conn.commit(); conn.close()
            return self._send_json({"ok": True})

        conn.close()
        self._send_json({"error": "not found"}, 404)

    def do_DELETE(self):
        parts = urlparse(self.path).path.strip("/").split("/")
        conn = get_db()

        if len(parts) == 3 and parts[0] == "api" and parts[1] == "subjects":
            conn.execute("DELETE FROM subjects WHERE id = ?", (parts[2],))
            conn.commit(); conn.close()
            return self._send_json({"ok": True})

        if len(parts) == 3 and parts[0] == "api" and parts[1] == "exams":
            conn.execute("DELETE FROM exams WHERE id = ?", (parts[2],))
            conn.commit(); conn.close()
            return self._send_json({"ok": True})

        conn.close()
        self._send_json({"error": "not found"}, 404)


def main():
    init_db()
    port = int(os.environ.get("PORT", 5000))
    server = ThreadingHTTPServer(("0.0.0.0", port), Handler)
    print(f"ZIMStudy AI running at http://localhost:{port}  (Ctrl+C to stop)")
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("\nShutting down.")
        server.shutdown()


if __name__ == "__main__":
    main()
