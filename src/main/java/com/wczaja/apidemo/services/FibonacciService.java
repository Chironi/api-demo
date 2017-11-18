package com.wczaja.apidemo.services;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Service
public class FibonacciService {

    private static HashMap<Integer, BigInteger> memoizedValues = new HashMap<>();

    /**
     *
     * @param numberOfFibonacci
     * @return
     */
    public List<BigInteger> getFibonacciNumbers(int numberOfFibonacci) {
        List<BigInteger> fibonacciNumbers = new ArrayList<>();

        for (int i = 0; i < numberOfFibonacci; i++) {
            fibonacciNumbers.add(
                    getFibonacciNumberWithRecursiveMemoization(i)
            );
        }

        return fibonacciNumbers;
    }

    /**
     *
     * @param n
     * @return
     */
    private BigInteger getFibonacciNumberWithRecursiveMemoization(Integer n) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        }

        if (memoizedValues.containsKey(n)) {
            return memoizedValues.get(n);
        } else {
            BigInteger fibonacciNumber =
                    getFibonacciNumberWithRecursiveMemoization(n - 2).add(
                            getFibonacciNumberWithRecursiveMemoization(n - 1)
                    );
            memoizedValues.put(n, fibonacciNumber);
            return  fibonacciNumber;
        }
    }
}
