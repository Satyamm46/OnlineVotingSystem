package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAIService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://openrouter.ai/api/v1")
            .defaultHeader("Content-Type", "application/json")
            .build();

    public String askAI(String question) {

        try {

            Map<String, Object> requestBody = Map.of(
                    "model", "meta-llama/llama-3-8b-instruct",
                    "messages", List.of(
                            Map.of("role", "user", "content", question)
                    )
            );

            Map response = webClient.post()
                    .uri("/chat/completions") 
                    .header("Authorization", "Bearer " + apiKey)
                    .header("HTTP-Referer", "http://localhost:3000")
                    .header("X-Title", "OnlineVotingSystem")
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map> choices = (List<Map>) response.get("choices");
            Map message = (Map) choices.get(0).get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "OpenRouter Error: " + e.getMessage();
        }
    }
}