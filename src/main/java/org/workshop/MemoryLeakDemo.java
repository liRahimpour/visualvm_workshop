package org.workshop;
import java.util.*;

/**
 * Künstliches Speicherleck durch stetige Allokation von byte[]-Arrays
 */
public class MemoryLeakDemo {

    private static final List<byte[]> storage = new ArrayList<>();
    private static volatile boolean allocate = false;

    public static void main(String[] args) {
        // Speicher-Thread
        Thread memoryThread = new Thread(() -> {
            try {
                while (true) {
                    if (allocate) {
                        storage.add(new byte[100 * 1024]); // 100 KB
                        if (storage.size() > 400) { //400 MB
                            System.out.println("Storage cleared at size: " + storage.size());
                            storage.clear(); // Speicher freigeben
                            System.gc(); // GC-Zyklus anstoßen
                        }
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "MemoryWorker");

        memoryThread.setDaemon(true);
        memoryThread.start();

        // Steuerung
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gib 'on' für Speicherzuteilung, 'off' für Pause, 'exit' zum Beenden:");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("on")) {
                allocate = true;
                System.out.println("Speicherzuteilung aktiviert.");
            } else if (input.equalsIgnoreCase("off")) {
                allocate = false;
                System.out.println("Speicherzuteilung pausiert.");
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Programm wird beendet.");
                System.exit(0);
            } else {
                System.out.println("Ungültiger Befehl. Nutze 'on', 'off' oder 'exit'.");
            }
        }
    }
}