# WGU OA Test Simulator

> ⚠️ Disclaimer: This tool is intended for self-assessment and study preparation. It does **not** guarantee a passing score on any official WGU Objective Assessment.

---

## 📘 About

This is a lightweight Java-based terminal application built to simulate WGU Objective Assessments (OAs) under realistic testing conditions. It aims to help students evaluate their mastery of course material and develop the mental endurance required to pass high-stakes exams.

---

## ✨ Features

- ⏱️ **120-Minute Silent Timer**  
Runs in the background via Bash or PowerShell (configurable). Simulates real test pressure by not showing the remaining time.
  - Default:
    - First Sound: Standard Beep Indicating the start of the test
    - Second Sound: Standard Beep Indicating half way through the test
    - Third Sound: 2 Distinct Beeps indicating your remaining 15 minutes.
    - Final Sound: 3 Higher pitched Beeps will go off indicating the end of the test.

- 🔀 **Fully Randomized Questions and Answers**  
Question order and answer choices are shuffled each time the test runs.

- 🧠 **Mastery-Focused Design**  
The test does **not** show whether your answers are correct during the quiz. You’ll receive your score summary only at the end.

- 🛠️ **Configurable JSON-Based Question Bank**  
All questions and answers are stored in an easily editable JSON file, allowing you to tailor quizzes to any course/module.

---

## ✨ Upcoming Features

- 💻 **GUI**  
A full cross-platform GUI experience for both Linux and Windows, providing a smoother and more intuitive testing environment.

- ❓ **Mark For Review**  
The ability to flag questions for later review before submitting your final answers, just like the real WGU Objective Assessments.

- 🍎 **MacOS Support**
While currently optimized for Windows and Linux, full support and testing for macOS is planned for future updates. Compatibility improvements are underway.


---

## ⚙️ Installation

### 📦 Prerequisites

- Java 11 or higher
- PowerShell (Windows) or Bash (Linux/macOS)

### 🪟 Windows

1. Clone the repository:
   ```bash
   git clone https://github.com/ShadowMP252/wgu-oa-simulator.git
   ```
2. Make your out directory (for compiled files):
  ```bash
  mkdir out
  ```
3. Navigate to the project directory and compile:
    ```bash
    cd wgu-oa-simulator/java/src
    javac -cp "lib/*" -d out src/*.java    
    ```
4. Run the program:
    ```bash
    java -cp "out;lib/*" Main
    ```
### 🐧 Linux/macOS

1. Clone the repository:
    ```bash
    git clone https://github.com/ShadowMP252/wgu-oa-simulator.git
    ```
2. Ensure `paplay` (PulseAudio) is installed.
  ```bash
  sudo apt install pulseaudio-utils
  ```
3. Make your out directory (for compiled files):
  ```bash
  mkdir out
  ```
4. Navigate to the project directory and compile:
    ```Bash
    javac -cp "lib/*" -d out src/*.java
    ```
5. Run the program:
    ```bash
    java -cp "out:lib/*" Main
    ```

### 📝 Usage Tips
  - Treat the test seriously — no peeking at the JSON file! (_unless you create a custom one, but make enough questions that makes it difficult to remember them._)
  - Keep the timer script running in the background for true simulation.
  - Randomized questions and answers mean you'll need actual understanding, not memorization.
  - Add tricky wording and phrasing in your question bank to push your comprehension further.


### ❗ Warning
This tool is intended for personal study use only. It does not contain actual WGU test questions, and using it does **not** guarantee a passing grade on official exams.

The goal is to **train your knowledge under pressure**...not cheat the system.

### 💬 Final Thoughts
This project was built in a few hours as a personal study tool and is being shared publicly to help other students build their confidence and test-taking skills.

If you find it useful or want to contribute...feel free to fork, submit issues, or open pull requests.


### 💡 Good luck
