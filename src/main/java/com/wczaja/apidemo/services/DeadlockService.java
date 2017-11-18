package com.wczaja.apidemo.services;

import org.springframework.stereotype.Service;

@Service
public class DeadlockService {

    public void lockThreads(int timeout) {
        Object resource1 = new Object();
        Object resource2 = new Object();

        Thread thread1 = new Thread(() ->
                lockResources(resource1, resource2, timeout)
        );

        Thread thread2 = new Thread(() ->
                lockResources(resource2, resource1, timeout)
        );

        thread1.start();
        thread2.start();
    }

    private void lockResources(Object resource1, Object resource2, int timeout) {
        synchronized (resource1) {
            System.out.println("Locked first parameter");
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        synchronized (resource2) {
            System.out.println("Locked second parameter");
        }
    }
}
