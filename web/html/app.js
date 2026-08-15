// ZIMStudy AI — Web Edition frontend
// Vanilla JS, no build step, no dependencies.

const screens = {
  onboarding: document.getElementById("screen-onboarding"),
  dashboard: document.getElementById("screen-dashboard"),
  subjects: document.getElementById("screen-subjects"),
  timer: document.getElementById("screen-timer"),
};

function showScreen(name) {
  Object.values(screens).forEach((s) => s.classList.add("hidden"));
  screens[name].classList.remove("hidden");
}

async function api(path, options = {}) {
  const res = await fetch(path, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  if (!res.ok && res.status !== 404) throw new Error(`API error ${res.status}`);
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

// ---------- Onboarding ----------

document.getElementById("onboarding-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  await api("/api/profile", {
    method: "POST",
    body: JSON.stringify({
      name: document.getElementById("ob-name").value || "Student",
      school: document.getElementById("ob-school").value,
      grade: document.getElementById("ob-grade").value,
      exam_board: document.getElementById("ob-board").value,
      exam_year: document.getElementById("ob-year").value,
    }),
  });
  await loadDashboard();
});

// ---------- Navigation ----------

document.querySelectorAll("[data-nav]").forEach((btn) => {
  btn.addEventListener("click", async () => {
    const target = btn.dataset.nav;
    if (target === "dashboard") await loadDashboard();
    if (target === "subjects") await loadSubjectsScreen();
  });
});

// ---------- Dashboard ----------

async function loadDashboard() {
  const [profile, subjects, exams] = await Promise.all([
    api("/api/profile"),
    api("/api/subjects"),
    api("/api/exams"),
  ]);

  document.getElementById("welcome-text").textContent =
    `Welcome back, ${profile?.name || "Student"}`;

  const examInfo = document.getElementById("exam-info");
  if (exams.length > 0) {
    const next = exams
      .slice()
      .sort((a, b) => new Date(a.exam_date) - new Date(b.exam_date))[0];
    const days = Math.max(
      0,
      Math.ceil((new Date(next.exam_date) - new Date()) / (1000 * 60 * 60 * 24))
    );
    examInfo.innerHTML = `<strong>${next.subject_name}${
      next.paper_number ? " — Paper " + next.paper_number : ""
    }</strong><br/>${days} days remaining`;
  } else {
    examInfo.textContent = "No exam dates added yet.";
  }

  const list = document.getElementById("subjects-list");
  list.innerHTML = "";
  if (subjects.length === 0) {
    list.innerHTML = `<p class="muted">No subjects yet. Tap "Manage Subjects" to add some.</p>`;
  } else {
    subjects.forEach((s) => {
      const card = document.createElement("div");
      card.className = "subject-card";
      card.innerHTML = `
        <div>
          <div class="name">${escapeHtml(s.name)}</div>
          <div class="target">Target: ${escapeHtml(s.target_grade)}</div>
        </div>
        <button class="btn primary">Start</button>
      `;
      card.querySelector("button").addEventListener("click", () =>
        startTimer(s.name, "Focused session")
      );
      list.appendChild(card);
    });
  }

  showScreen("dashboard");
}

document.getElementById("exam-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  await api("/api/exams", {
    method: "POST",
    body: JSON.stringify({
      subject_name: document.getElementById("exam-subject").value,
      paper_number: document.getElementById("exam-paper").value,
      exam_date: document.getElementById("exam-date").value,
    }),
  });
  e.target.reset();
  await loadDashboard();
});

// ---------- Subjects management ----------

async function loadSubjectsScreen() {
  const subjects = await api("/api/subjects");
  const list = document.getElementById("subject-manage-list");
  list.innerHTML = "";
  subjects.forEach((s) => {
    const li = document.createElement("li");
    li.innerHTML = `<span>${escapeHtml(s.name)}</span><button>Remove</button>`;
    li.querySelector("button").addEventListener("click", async () => {
      await api(`/api/subjects/${s.id}`, { method: "DELETE" });
      await loadSubjectsScreen();
    });
    list.appendChild(li);
  });
  showScreen("subjects");
}

document.getElementById("subject-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const input = document.getElementById("new-subject");
  if (!input.value.trim()) return;
  await api("/api/subjects", {
    method: "POST",
    body: JSON.stringify({ name: input.value.trim() }),
  });
  input.value = "";
  await loadSubjectsScreen();
});

// ---------- Timer ----------

let timerInterval = null;
let secondsElapsed = 0;
let timerRunning = true;
let timerSubject = "";
let timerTopic = "";

function startTimer(subject, topic) {
  timerSubject = subject;
  timerTopic = topic;
  secondsElapsed = 0;
  timerRunning = true;
  document.getElementById("timer-subject").textContent = subject;
  document.getElementById("timer-topic").textContent = topic;
  document.getElementById("timer-pause").textContent = "Pause";
  updateClock();

  clearInterval(timerInterval);
  timerInterval = setInterval(() => {
    if (timerRunning) {
      secondsElapsed++;
      updateClock();
    }
  }, 1000);

  showScreen("timer");
}

function updateClock() {
  const m = String(Math.floor(secondsElapsed / 60)).padStart(2, "0");
  const s = String(secondsElapsed % 60).padStart(2, "0");
  document.getElementById("timer-clock").textContent = `${m}:${s}`;
}

document.getElementById("timer-pause").addEventListener("click", (e) => {
  timerRunning = !timerRunning;
  e.target.textContent = timerRunning ? "Pause" : "Resume";
});

document.getElementById("timer-complete").addEventListener("click", async () => {
  clearInterval(timerInterval);
  const minutes = Math.max(1, Math.round(secondsElapsed / 60));
  await api("/api/sessions", {
    method: "POST",
    body: JSON.stringify({
      subject_name: timerSubject,
      topic: timerTopic,
      duration_minutes: minutes,
    }),
  });
  await loadDashboard();
});

document.getElementById("timer-cancel").addEventListener("click", async () => {
  clearInterval(timerInterval);
  await loadDashboard();
});

// ---------- Utilities ----------

function escapeHtml(str) {
  const div = document.createElement("div");
  div.textContent = str ?? "";
  return div.innerHTML;
}

// ---------- Boot ----------

(async function init() {
  const profile = await api("/api/profile");
  if (profile) {
    await loadDashboard();
  } else {
    showScreen("onboarding");
  }
})();
