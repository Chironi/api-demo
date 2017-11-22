package com.wczaja.apidemo.unit;

import com.wczaja.apidemo.services.DeadlockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeadlockServiceTest {

    @Autowired
    DeadlockService deadlockService;

    @Test
    public void testGetDeadlockedThreadIds_NoneDeadlocked() {
        assertThat(deadlockService.getDeadlockedThreadIds()).isNull();
    }

    @Test
    public void testGetDeadlockedThreadIds_DeadlockedDetected() throws InterruptedException {
        deadlockService.deadlockThreads(100);
        Thread.sleep(1000);
        assertThat(deadlockService.getDeadlockedThreadIds()).isNotEmpty();
    }
}
