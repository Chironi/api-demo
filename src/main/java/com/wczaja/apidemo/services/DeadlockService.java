package com.wczaja.apidemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Service which deadlocks threads and can get list of deadlock thread ids
 */
@Service
public class DeadlockService {

    private static final Logger log = LoggerFactory.getLogger(DeadlockService.class);

    /**
     * Creates two threads and deadlocks them on synchronized resources
     * The deadlock is caused by a cyclic dependency on shared resources. Each thread is waiting for the other to release
     * the resource it needs.
     *
     * @param timeout The amount of time in ms for an arbitrary job to run in the threads
     */
    public void deadlockThreads(int timeout) {
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
     * Detects deadlocked threads and returns a list of deadlocked thread ids
     *
     * @return Array of deadlocked thread ids
     */
    public long[] getDeadlockedThreadIds() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        return threadMXBean.findDeadlockedThreads();
    }

    /**
     * Locks resources, using embedded synchronized blocks, running an arbitrary job for a given timeout
     *
     * @param resource1 The first resource to lock
     * @param resource2 The second resource to lock
     * @param timeout time in ms for an arbitrary job to run
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

    /**
     * An arbitrary job for the threads to run
     *
     * @param timeout Time in ms for the job to run
     */
    private void runJob(int timeout) {
        try {
            log.info("Job is running for " +  timeout + "ms");
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("An error occurred while sleeping the thread", e);
        }
    }
}
