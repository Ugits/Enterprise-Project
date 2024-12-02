package org.jonas.enterpriseproject.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiWebClient {

    @Value("${API_URL}")
    private String apiUrl;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient
                .builder()
                .defaultHeader("Content-Type", "application/json")
                .baseUrl(apiUrl);
    }

}
