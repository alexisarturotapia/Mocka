package com.alexistapia.demomocka;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConcurrentService {

    private final Lock lock = new ReentrantLock();

    public void performTaskWithLock() {
        lock.lock();
        try {
            // Simular una tarea que toma tiempo
            System.out.println("Tarea con lock iniciada por " + Thread.currentThread().getName());
            Thread.sleep(2000); // Simula una tarea que toma 2 segundos
            System.out.println("Tarea con lock completada por " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void performTaskWithoutLock() {
        System.out.println("Tarea sin lock iniciada por " + Thread.currentThread().getName());
        try {
            // Simular una tarea que toma tiempo
            Thread.sleep(2000); // Simula una tarea que toma 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Tarea sin lock completada por " + Thread.currentThread().getName());
    }
}

