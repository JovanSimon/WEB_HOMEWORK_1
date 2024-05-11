package org.example;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    public static AtomicInteger SUM = new AtomicInteger(0);
    public static AtomicInteger COUNT = new AtomicInteger(0);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite broj studenata: ");
        int numOfStudents = scanner.nextInt();

        ExecutorService generalPool = Executors.newFixedThreadPool(numOfStudents + 2);
        ExecutorService poolAsistent = Executors.newFixedThreadPool(1);
        ExecutorService poolProfesor = Executors.newFixedThreadPool(2);
        Asistent asistent = new Asistent(poolAsistent);
        Profesor profesor = new Profesor(cyclicBarrier, poolProfesor);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Odbrane su gotove.");
                generalPool.shutdownNow();

                System.out.println("Broj studenata koji su polagali su: " + COUNT.get() + "\nProsek ocena je: " + (float) SUM.get() / COUNT.get());
            }
        }, 5000);

        for (int i = 1; i <= numOfStudents; i++) {
            boolean choose = new Random().nextBoolean();
            if (choose) {
                generalPool.submit(new Student("Student " + i, ThreadLocalRandom.current().nextInt(0, 1001),
                        ThreadLocalRandom.current().nextInt(500, 1001), profesor, null, poolProfesor, null));
            } else {
                generalPool.submit(new Student("Student " + i, ThreadLocalRandom.current().nextInt(0, 1001),
                        ThreadLocalRandom.current().nextInt(500, 1001), null, asistent, null, poolAsistent));
            }
        }
    }
}
