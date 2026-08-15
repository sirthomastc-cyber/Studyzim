# ZIMStudy AI — Web Edition

Same Phase 1 feature set as the Android app (onboarding, subjects,
exam countdown, study timer, persistent storage) as a browser-based
webapp instead. Pure Python standard library on the backend — no
`pip install` required, just Python 3.8+.

## Run it

```
cd web
python3 server.py
```

Then open **http://localhost:5000** in your browser.

Data is stored in a local SQLite file at `web/data/zimstudy.db`,
created automatically on first run.

To use a different port:

```
PORT=8080 python3 server.py
```

## Structure

```
web/
  server.py        — backend: routes + SQLite, stdlib only
  html/
    index.html     — onboarding / dashboard / subjects / timer screens
    style.css       
    app.js         — fetch()-based frontend logic, no build step
  data/            — zimstudy.db lives here (gitignored)
```

## Notes

- This was built and tested in a sandboxed environment without
  general internet access, so no external packages are used — this
  keeps it runnable anywhere Python 3 is installed, with nothing to
  download. If you'd rather use Flask/FastAPI later, the API surface
  (`/api/profile`, `/api/subjects`, `/api/exams`, `/api/sessions`) is
  small enough to port over directly.
- Same limitation as the Android app: this is Phase 1 (on-device/
  on-server basics) only. The AI tutor, document upload, past-paper
  analysis, YouTube learning, and voice mode all need a real AI
  provider API key and should live behind their own backend route —
  never shipped to the browser directly.
