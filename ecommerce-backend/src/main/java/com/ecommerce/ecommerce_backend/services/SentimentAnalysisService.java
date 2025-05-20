package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.SentimentAnalysisResult;
import com.ecommerce.ecommerce_backend.enums.SentimentLabel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SentimentAnalysisService {

    private final RestTemplate restTemplate = new RestTemplate();

    public SentimentAnalysisResult analyzeText(String text) {
        String url = "http://localhost:5000/analyze";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of("text", text);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        Map<String, Object> responseBody = response.getBody();

        SentimentAnalysisResult result = new SentimentAnalysisResult();
        result.setScore(((Number) responseBody.get("polarity")).doubleValue());

        String sentimentStr = (String) responseBody.get("sentiment");
        result.setSentiment(SentimentLabel.valueOf(sentimentStr.toUpperCase()));

        return result;
    }
}
