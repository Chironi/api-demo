package com.wczaja.apidemo.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Service which uses RestTemplate to call out to an external service to retrieve Post objects
 */
@Service
public class ExternalPostsService {

    /**
     * Calls out to external service using RestTemplate to get Post object by id
     *
     * @param id The id of the Post to get from the external service
     * @return ResponseEntity containing the fetched Post
     */
    public ResponseEntity getPostById(String id) {
        final String uri = "https://jsonplaceholder.typicode.com/posts/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class, params);
    }

    /**
     * Calls out to external service using RestTemplate to get list of Post objects
     *
     * @return ResponseEntity containing the fetched Posts
     */
    public ResponseEntity getPosts() {
        final String uri = "https://jsonplaceholder.typicode.com/posts";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class);
    }
}
