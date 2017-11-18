package com.wczaja.apidemo.controllers;

import com.wczaja.apidemo.resources.UniqueWord;
import com.wczaja.apidemo.services.FibonacciService;
import com.wczaja.apidemo.services.UniqueWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 */
@RestController
public class DemoController {

    @Autowired
    UniqueWordService uniqueWordService;

    @Autowired
    FibonacciService fibonacciService;

    /**
     *
     * @return
     */
    @RequestMapping("/hello-world")
    public String hello() {
        return "Hello World!";
    }

    /**
     *
     * @param paragraph
     * @return
     */
    @RequestMapping(path = "/unique-words", method= POST)
    public List<UniqueWord> findUniqueWordsAndCounts(@RequestParam(value="paragraph") String paragraph) {
        return uniqueWordService.getUniqueWordsFromParagraph(paragraph);
    }

    @RequestMapping(path = "/fibonacci-numbers", method= POST)
    public List<BigInteger> findFibonacciNumbers(@RequestParam(value="N") Integer n) {
        return fibonacciService.getFibonacciNumbers(n);
    }
}
