package com.example.demo.api.ctrl;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${naver.client-di}")
    private String client;

    @Value("${naver.secret}")
    private String secret;

    @GetMapping("/naver/local/{name}")
    public String local(@PathVariable String name) {
        System.out.println("debug >>> endpoint : /naver/local/{name}");
        System.out.println("debug >>> params = " + name);
        search(name);
        return null;
    }

    public String search(String query) {
        try {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
            String encode = StandardCharsets.UTF_8.decode(buffer).toString();
            System.out.println("debug >>> query encode = " + encode);
            URI uri = UriComponentsBuilder
                    .fromUriString("https://openapi.naver.com")
                    .path("/v1/search/local")
                    .queryParam("query", encode)
                    .queryParam("display", 10)
                    .queryParam("start", 1)
                    .queryParam("sort", "random")
                    .encode().build().toUri();
            System.out.println("debug >>> 검색 URL 생성 완료");
            RestTemplate restTemplate = new RestTemplate();
            RequestEntity<Void> request = RequestEntity
                    .get(uri)
                    .header("X-Naver-Client-id", client)
                    .header("X-Naver-Client-Secret", secret)
                    .build();
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode items = rootNode.path("items");
            for (JsonNode item : items) {
                System.out.println(item.get("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
