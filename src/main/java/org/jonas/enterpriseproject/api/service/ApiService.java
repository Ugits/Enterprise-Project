package org.jonas.enterpriseproject.api.service;

import org.jonas.enterpriseproject.exception.WebServiceException;
import org.jonas.enterpriseproject.api.dto.ErrorResponseDTO;
import org.jonas.enterpriseproject.spell.model.SpellDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
    private final WebClient webClient;

    @Value("${INDEX_1}")
    private String index1;
    @Value("${INDEX_2}")
    private String index2;
    @Value("${INDEX_3}")
    private String index3;

    @Autowired
    public ApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<SpellDTO>> fetchAllSpells() {
        System.out.println("FETCH ALL SPELLS");
        return webClient.get()
                .uri("/all")
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(ErrorResponseDTO.class)
                                .flatMap(externalError -> {
                                    logger.error("External API Error: {}", externalError.message());
                                    System.out.println("External API Error: " + externalError.message());
                                    OffsetDateTime timestamp = parseTimestamp(externalError.timestamp());
                                    return Mono.error(new WebServiceException(
                                            externalError.statusCode(),
                                            externalError.message(),
                                            timestamp
                                    ));
                                })
                )
                .bodyToFlux(SpellDTO.class)
                .collectList()
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebServiceException) {
                        return Mono.error(throwable);
                    } else if (throwable instanceof WebClientRequestException) {
                        return Mono.error(new WebServiceException(
                                HttpStatus.BAD_GATEWAY.value(),
                                "Unable to connect to the external service.",
                                OffsetDateTime.now()
                        ));
                    } else {
                        return Mono.error(new WebServiceException(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "An unexpected error occurred.",
                                OffsetDateTime.now()
                        ));
                    }
                });
    }

    public Mono<List<SpellDTO>> fetchUnAuthSample() {

        return webClient.get()
                .uri("/sample/{index1}/{index2}/{index3}", index1, index2, index3)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(ErrorResponseDTO.class)
                                .flatMap(externalError -> {
                                    logger.error("External API Error: {}", externalError.message());
                                    OffsetDateTime timestamp = parseTimestamp(externalError.timestamp());
                                    return Mono.error(new WebServiceException(
                                            externalError.statusCode(),
                                            externalError.message(),
                                            timestamp
                                    ));
                                })
                )
                .bodyToFlux(SpellDTO.class)
                .collectList()
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebServiceException) {
                        return Mono.error(throwable);
                    } else if (throwable instanceof WebClientRequestException) {
                        return Mono.error(new WebServiceException(
                                HttpStatus.BAD_GATEWAY.value(),
                                "Unable to connect to the external service.",
                                OffsetDateTime.now()
                        ));
                    } else {
                        return Mono.error(new WebServiceException(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "An unexpected error occurred.",
                                OffsetDateTime.now()
                        ));
                    }
                });
    }

    private OffsetDateTime parseTimestamp(String timestamp) {
        try {
            return OffsetDateTime.parse(timestamp);
        } catch (Exception e) {
            logger.warn("Failed to parse timestamp: {}. Using current time.", timestamp);
            return OffsetDateTime.now();
        }
    }




}




