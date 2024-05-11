package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.*;

@Getter
@Setter
public class Profesor implements Callable<String> {
    private CyclicBarrier cyclicBarrier;
    private Student student;
    private ExecutorService executorService;

    public Profesor(CyclicBarrier cyclicBarrier, ExecutorService executorService) {
        this.cyclicBarrier = cyclicBarrier;
        this.executorService = executorService;
    }

    @Override
    public String call() {
        int grade;
        try {
            cyclicBarrier.await();
            Thread.sleep(student.getDefendTime());
            grade = ThreadLocalRandom.current().nextInt(5, 11);
            Main.SUM.addAndGet(grade);
            Main.COUNT.incrementAndGet();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        return "Prof: <Profesor> TTC: <" + (float) student.getDefendTime() / 1000 + "s> Score: <"+ grade + "> ";
    }
}
