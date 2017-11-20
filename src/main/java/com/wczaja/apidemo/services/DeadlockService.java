package com.wczaja.apidemo.services;

import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DeadlockService {

    /**
     *
     * @param timeout
     */
    public void lockThreads(int timeout) {
        String resource1 = "Alice";
        String resource2 = "Bob";

        Thread thread1 = new Thread(() ->
                lockResources(resource1, resource2, "Thread1", timeout)
        );

        Thread thread2 = new Thread(() ->
                lockResources(resource2, resource1, "Thread2", timeout)
        );

        thread1.start();
        thread2.start();
    }

    /**
     *
     * @param resource1
     * @param resource2
     * @param threadName
     * @param timeout
     */
    private void lockResources(Object resource1, Object resource2, String threadName, int timeout) {
        synchronized (resource1) {
            System.out.println(resource1 + " is locked by " + threadName);
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        synchronized (resource2) {
            System.out.println(resource2 + " is locked by " + threadName);
        }
    }
}
