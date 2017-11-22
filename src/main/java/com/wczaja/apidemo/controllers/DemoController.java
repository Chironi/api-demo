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
 * REST Controller for demoing various services
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
     * Endpoint for Hello World!
     *
     * @return String Hello World!
     */
    @RequestMapping(path = "/hello-world", method = GET)
    public String hello() {
        return "Hello World!";
    }

    /**
     * Endpoint to get unique words and their counts
     *
     * @param paragraphJson JSON object contain the paragraph to parse
     * @return ResponseEntity containing List of UniqueWordResource
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
     * Endpoint to retrieve a list of Fibonacci numbers
     *
     * @param n The number of Fibonacci numbers to retrieve
     * @return List of Fibonacci numbers
     */
    @RequestMapping(path = "/fibonacci-numbers", method = POST)
    public List<BigInteger> findFibonacciNumbers(@RequestParam(value="N") Integer n) {
        return fibonacciService.getFibonacciNumbers(n);
    }

    /**
     * Endpoint to deadlock threads
     *
     * @param timeout Time in ms for arbitrary job
     */
    @RequestMapping(path = "/deadlock-threads", method = POST)
    public void deadlockThreads(@RequestParam(value="timeout") Integer timeout) {
        deadlockService.deadlockThreads(timeout);
    }

    /**
     * Endpoint for checking if threads are deadlocked
     *
     * @return Returns message with deadlocked thread ids
     */
    @RequestMapping(path = "/detect-deadlock-threads", method = GET)
    public String detectDeadlockThreads() {
        long[] deadlockedThreadIds = deadlockService.getDeadlockedThreadIds();
        if (deadlockedThreadIds != null) {
            return "The following thread ids are deadlocked: " + Arrays.toString(deadlockedThreadIds);
        } else {
            return "No threads are deadlocked";
        }
    }

    /**
     * Endpoint get Posts from a call to an external service
     *
     * @return ResponseEntity containing Posts
     */
    @RequestMapping(path = "/external-posts-service", method = GET)
    public ResponseEntity getPostsFromExternalService() {
        return externalPostsService.getPosts();
    }

    /**
     * Endpoint to get Post by id from a call to an external service
     *
     * @param id Id of the Post to get
     * @return ResponseEntity containing the Post
     */
    @RequestMapping(path = "/external-posts-service/{id}", method = GET)
    public ResponseEntity getPostFromExternalService(@PathVariable String id) {
        return externalPostsService.getPostById(id);
    }

}
