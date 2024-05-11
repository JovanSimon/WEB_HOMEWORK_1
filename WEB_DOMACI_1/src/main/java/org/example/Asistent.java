package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.*;

@Getter
@Setter
public class Asistent implements Callable<String> {
    private Student student;
    private ExecutorService executorService;

    public Asistent(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public String call() {
        int grade;
        try {
            Thread.sleep(student.getDefendTime());
            grade = ThreadLocalRandom.current().nextInt(5, 11);
            Main.COUNT.incrementAndGet();
            Main.SUM.addAndGet(grade);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Prof: <Asistent> TTC: <" + (float) student.getDefendTime() / 1000 + "s> Score: <"+ grade + ">";
    }
}
