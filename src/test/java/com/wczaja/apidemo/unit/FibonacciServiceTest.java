package com.wczaja.apidemo.unit;

import com.wczaja.apidemo.services.FibonacciService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FibonacciServiceTest {

    @Autowired
    FibonacciService fibonacciService;

    @Test
    public void testGetFibonacciNumbers_FirstTen() {
        List<BigInteger> expectedFibonacciNumbers = new ArrayList<>();
        expectedFibonacciNumbers.add(BigInteger.valueOf(1));
        expectedFibonacciNumbers.add(BigInteger.valueOf(1));
        expectedFibonacciNumbers.add(BigInteger.valueOf(2));
        expectedFibonacciNumbers.add(BigInteger.valueOf(3));
        expectedFibonacciNumbers.add(BigInteger.valueOf(5));
        expectedFibonacciNumbers.add(BigInteger.valueOf(8));
        expectedFibonacciNumbers.add(BigInteger.valueOf(13));
        expectedFibonacciNumbers.add(BigInteger.valueOf(21));
        expectedFibonacciNumbers.add(BigInteger.valueOf(34));
        expectedFibonacciNumbers.add(BigInteger.valueOf(55));

        List<BigInteger> fibonacciNumbers = fibonacciService.getFibonacciNumbers(10);

        assertThat(fibonacciNumbers).isEqualTo(expectedFibonacciNumbers);
    }

    @Test
    public void testGetFibonacciNumbers_BigN() {
        Integer bigN = 100;

        List<BigInteger> fibonacciNumbers = fibonacciService.getFibonacciNumbers(bigN);

        assertThat(fibonacciNumbers.size()).isEqualTo(100);
        // BigInteger.valueOf() takes a Long and N=100 is too big for Long, so compare as Strings
        assertThat(fibonacciNumbers.get(99).toString()).isEqualTo("354224848179261915075");
    }
}
