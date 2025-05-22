# VisualVM Workshop – Java-Profiling leicht gemacht

Dieses Repository enthält den vollständigen Code und begleitende Materialien für einen interaktiven Workshop zum Thema **Java-Profiling mit VisualVM**.

## 🧰 Inhalte

- Einfache, lauffähige Java-Beispiele zu:
    - CPU-Profiling
    - Speicheranalyse (Heap & GC)
    - Thread-Diagnose (Running, Sleeping, Waiting)
- Simulierte Probleme wie:
    - Hohe CPU-Last
    - Speicherlecks
    - Deadlocks
- Hands-on-Demos für:
    - **Sampler** und **Profiler**
    - **Heap-Analyse** und **Thread Dump**

## 📁 Enthaltene Dateien

| Datei | Zweck |
|-------|-------|
| `CpuLoadToggle.java` | Live steuerbare CPU-Last |
| `MemoryLeakDemo.java` | Simuliertes Speicherleck |
| `ThreadDemo.java` | Visualisierung von Thread-Zuständen und steuerbaren Zusatzthreads |
| `workshop-zusammenfassung.pdf` | Übersichtliche PDF mit Erklärungen und Links |


## 🛠 Voraussetzungen

- Java 17 oder höher (empfohlen: OpenJDK 21 oder 23)
- [VisualVM](https://visualvm.github.io) (mind. Version 2.1)
- IntelliJ IDEA, VS Code oder andere Java-IDE

## 🚀 Quick Start

```bash
# Repository klonen
git clone https://github.com/<dein-benutzername>/visualvm-workshop.git
cd visualvm-workshop

# Beispiel kompilieren und starten
javac org/workshop/CpuLoadToggle.java
java org.workshop.CpuLoadToggle

```
## 📎 Nützliche Links

- 🔗 [VisualVM offizielle Website](https://visualvm.github.io/)
- 📘 [Dokumentation von VisualVM](https://visualvm.github.io/documentation.html)
- 📦 [JMX Remote Monitoring Guide (Oracle)](https://docs.oracle.com/javase/8/docs/technotes/guides/management/agent.html)

## Workshop-Zusammenfassung

Eine kompakte Zusammenfassung des Workshops mit Erklärungen und Links ist hier verfügbar:

[workshop-zusammenfassung.pdf](./workshop-zusammenfassung.pdf)

---
