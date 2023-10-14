package ru.netology;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RouteGenerator {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        Thread[] threads = new Thread[1000];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                String line = generateRoute("RLRFR", 100);
                int rCount = countR(line);
                updateSizeFreq(rCount);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        int maxFreq = 0;
        for (int freq : sizeToFreq.values()) {
            if (freq > maxFreq) {
                maxFreq = freq;
            }
        }

        System.out.println("Самое частое количество повторений " + maxFreq);
        System.out.println("--------------------------------------");
        System.out.println("Другие размеры:");

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            int size = entry.getKey();
            int freq = entry.getValue();
            if (freq < maxFreq) {
                System.out.println("- " + size + " (" + freq + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countR(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == 'R')
                count++;
        }
        return count;
    }

    public static synchronized void updateSizeFreq(int size) {
        sizeToFreq.put(size, sizeToFreq.getOrDefault(size, 0) + 1);
    }
}

