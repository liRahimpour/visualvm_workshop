package org.workshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadDemo {

    private static final List<Thread> extraThreads = new ArrayList<>();
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    private static volatile boolean running = true;
    private static volatile boolean deadlockMode = false;

    public static void main(String[] args) {
        // 1. Running Thread
        new Thread(() -> {
            while (true) {
                // dauerhaft aktiv
            }
        }, "---BusyThread---").start();

        // 2. Sleeping Thread
        new Thread(() -> {
            try {
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "---SleepingThread---").start();

        // 3. Waiting Thread
        new Thread(() -> {
            synchronized (ThreadDemo.class) {
                try {
                    ThreadDemo.class.wait(); // wartet dauerhaft
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "---WaitingThread---").start();

        // Steuerung per Konsole
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Eingabe: 'add', 'deadlock', 'remove', 'exit'");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "add":
                    startNormalExtraThreads();
                    break;
                case "deadlock":
                    startDeadlockThreads();
                    break;
                case "remove":
                    stopExtraThreads();
                    break;
                case "exit":
                    stopExtraThreads();
                    System.out.println("Programm wird beendet.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ungültiger Befehl. Nutze 'add', 'deadlock', 'remove' oder 'exit'.");
            }
        }
    }

    private static void startNormalExtraThreads() {
        if (!extraThreads.isEmpty()) {
            System.out.println("Extra-Threads laufen bereits.");
            return;
        }

        running = true;
        deadlockMode = false;

        Thread extra1 = new Thread(() -> {
            while (running) {
                Math.sin(System.nanoTime());
            }
        }, "---ExtraThread1---");

        Thread extra2 = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "---ExtraThread2---");

        extraThreads.add(extra1);
        extraThreads.add(extra2);

        extra1.start();
        extra2.start();

        System.out.println("Zwei Zusatz-Threads gestartet (normal).");
    }

    private static void startDeadlockThreads() {
        if (!extraThreads.isEmpty()) {
            System.out.println("Beende zuerst laufende Extra-Threads mit 'remove'.");
            return;
        }

        deadlockMode = true;

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread1 hat lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                synchronized (lock2) {
                    System.out.println("Thread1 hat lock2");
                }
            }
        }, "---DeadlockThread1---");

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread2 hat lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                synchronized (lock1) {
                    System.out.println("Thread2 hat lock1");
                }
            }
        }, "---DeadlockThread2---");

        extraThreads.add(thread1);
        extraThreads.add(thread2);

        thread1.start();
        thread2.start();

        System.out.println("Zwei Deadlock-Threads gestartet.");
    }

    private static void stopExtraThreads() {
        if (extraThreads.isEmpty()) {
            System.out.println("Keine Extra-Threads aktiv.");
            return;
        }

        running = false;

        for (Thread t : extraThreads) {
            t.interrupt(); // auch im Deadlock kann das helfen, aufzuräumen
        }

        extraThreads.clear();
        System.gc();

        if (deadlockMode) {
            System.out.println("Deadlock-Threads wurden gestoppt (via interrupt – ggf. ohne echte Auflösung).");
        } else {
            System.out.println("Zusätzliche Threads wurden gestoppt.");
        }

        deadlockMode = false;
    }
}
