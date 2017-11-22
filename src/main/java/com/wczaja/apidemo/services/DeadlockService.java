package com.wczaja.apidemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 *
 */
@Service
public class DeadlockService {

    private static final Logger log = LoggerFactory.getLogger(DeadlockService.class);

    public long[] getDeadlockedThreadIds() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        return threadMXBean.findDeadlockedThreads();
    }

    /**
     *
     * @param timeout
     */
    public void lockThreads(int timeout) {
        String resource1 = "Alice";
        String resource2 = "Bob";

        Thread thread1 = new Thread(() ->
                lockResources(resource1, resource2, timeout)
        );

        Thread thread2 = new Thread(() ->
                lockResources(resource2, resource1, timeout)
        );

        thread1.start();
        thread2.start();
    }

    /**
     *
     * @param resource1
     * @param resource2
     * @param timeout
     */
    private void lockResources(Object resource1, Object resource2, int timeout) {
        String threadName = Thread.currentThread().getName();
        log.info(threadName + " running job for " + resource1);
        synchronized (resource1) {
            log.info(resource1 + " is locked by thread " + threadName);

            runJob(timeout);

            log.info(threadName + " running job for " + resource2);
            synchronized (resource2) {
                log.info(resource2 + " is locked by thread " + threadName);

                runJob(timeout);
            }
            log.info(resource2 + " is now free");
        }
        log.info(resource1 + " is now free");
    }

    private void runJob(int timeout) {
        try {
            log.info("Job is running for " +  timeout + "ms");
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("An error occurred while sleeping the thread", e);
        }
    }
}
