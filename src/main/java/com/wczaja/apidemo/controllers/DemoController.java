package com.wczaja.apidemo.controllers;

import com.wczaja.apidemo.services.DeadlockService;
import com.wczaja.apidemo.services.ExternalPostsService;
import com.wczaja.apidemo.services.FibonacciService;
import com.wczaja.apidemo.services.UniqueWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
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

    @Autowired
    DeadlockService deadlockService;

    @Autowired
    ExternalPostsService externalPostsService;

    /**
     *
     * @return
     */
    @RequestMapping(path = "/hello-world", method = GET)
    public String hello() {
        return "Hello World!";
    }

    /**
     *
     * @param paragraphJson
     * @return
     */
    @RequestMapping(path = "/unique-words", method = POST)
    public ResponseEntity findUniqueWordsAndCounts(@RequestBody Map<String, String> paragraphJson) {
        String paragraph = paragraphJson.get("paragraph");
        if (null != paragraph) {
            return new ResponseEntity<>(uniqueWordService.getUniqueWordsFromParagraph(paragraph), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("JSON must contain paragraph", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param n
     * @return
     */
    @RequestMapping(path = "/fibonacci-numbers", method = POST)
    public List<BigInteger> findFibonacciNumbers(@RequestParam(value="N") Integer n) {
        return fibonacciService.getFibonacciNumbers(n);
    }

    /**
     *
     * @param timeout
     */
    @RequestMapping(path = "/deadlock-threads", method = POST)
    public void deadlockThreads(@RequestParam(value="timeout") Integer timeout) {
        deadlockService.lockThreads(timeout);
    }

    @RequestMapping(path = "/detect-deadlock-threads", method = GET)
    public String detectDeadlockThreads() {
        long[] deadlockedThreadIds = deadlockService.getDeadlockedThreadIds();
        if (deadlockedThreadIds != null) {
            return "The following thread ids are deadlocked: " + Arrays.toString(deadlockedThreadIds);
        } else {
            return "No threads are deadlocked";
        }
    }

    @RequestMapping(path = "/external-posts-service", method = GET)
    public ResponseEntity getPostsFromExternalService() {
        return externalPostsService.getPosts();
    }

    @RequestMapping(path = "/external-posts-service/{id}", method = GET)
    public ResponseEntity getPostFromExternalService(@PathVariable String id) {
        return externalPostsService.getPostById(id);
    }

}
