package com.wczaja.apidemo.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalPostsService {

    public ResponseEntity getPostById(String id) {
        final String uri = "https://jsonplaceholder.typicode.com/posts/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class, params);
    }

    public ResponseEntity getPosts() {
        final String uri = "https://jsonplaceholder.typicode.com/posts";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(uri, String.class);
    }
}
