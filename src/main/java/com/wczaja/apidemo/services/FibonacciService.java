package com.wczaja.apidemo.services;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service which calculates n Fibonacci numbers using recursion + memoization
 */
@Service
public class FibonacciService {

    private static HashMap<Integer, BigInteger> memoizedValues = new HashMap<>();

    /**
     *  Populates a List of BigIntegers with Fibonacci numbers by calling the calculate method numberOfFibonacci times
     *
     * @param numberOfFibonacci The number of Fibonacci numbers to add
     * @return List of Fibonacci numbers
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
     * Calculates Fibonacci numbers using recursion method, and uses memoization for efficiency
     *
     * @param n Integer for which Fibonacci to calculate for
     * @return BigInteger calculated Fibonacci number
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
