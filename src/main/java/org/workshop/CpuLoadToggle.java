package org.workshop;

import java.util.Scanner;

/**
 * Startet einen Hintergrundthread, der in einer Schleife entweder:
 * Intensiv rechnet (Math.sqrt(), wenn busy == true)
 * Oder schläft (Thread.sleep(), wenn busy == false)
 * kann live im Terminal mit on, off, exit gesteuert werden
 * In VisualVM sieht mann dann:
 * CPU usage steigt stark an bei on
 * CPU usage fällt ab bei off
 */
public class CpuLoadToggle {

    private static volatile boolean busy = false;
    public static void main(String[] args) {
        // CPU-Thread
        Thread loadThread = new Thread(() -> {
            while (true) {
                if (busy) {
                    // CPU-Last
                    for (int i = 0; i < 1_000_000; i++) {
                        Math.sqrt(i); // Dummy-Berechnung
                    }
                } else {
                    try {
                        Thread.sleep(100); // Wenige CPU-Last
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }, "CpuWorker"); // <<< Thread-Name

        loadThread.setDaemon(true);
        loadThread.start();

        // Steuerung
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gib 'on' für CPU-Last, 'off' für Pause, 'exit' zum Beenden:");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("on")) {
                busy = true;
                System.out.println("CPU-Last aktiviert.");
            } else if (input.equalsIgnoreCase("off")) {
                busy = false;
                System.out.println("CPU-Last pausiert.");
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Programm wird beendet.");
                System.exit(0);
            } else {
                System.out.println("Ungültiger Befehl. Nutze 'on', 'off' oder 'exit'.");
            }
        }
    }
}
