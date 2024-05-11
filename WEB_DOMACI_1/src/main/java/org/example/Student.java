package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Getter
@Setter
public class Student implements Runnable{
    private String name;
    private long arrivalTime, defendTime;
    private Profesor profesor;
    private Asistent asistent;
    private ExecutorService profesorService, asistentService;

    public Student(String name, long arrivalTime, long defendTime, Profesor profesor, Asistent asistent, ExecutorService profesorService, ExecutorService asistentService) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.defendTime = defendTime;
        this.asistent = asistent;
        this.profesor = profesor;
        this.profesorService = profesorService;
        this.asistentService = asistentService;
    }

    @Override
    public void run() {
        if(profesor != null){
            try {
                Thread.sleep(this.getArrivalTime());
                profesor.setStudent(this);
                Future<String> future = profesorService.submit(profesor);

                System.out.println("Thread: <Student " + this.getName() + "> Arrival: <" + (float) this.getArrivalTime() / 1000 + "s> " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                Thread.sleep(this.getArrivalTime());
                asistent.setStudent(this);
                Future<String> future = asistentService.submit(asistent);

                System.out.println("Thread: <Student " + this.getName() + "> Arrival: <" + (float) this.getArrivalTime() / 1000 + "s> " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

