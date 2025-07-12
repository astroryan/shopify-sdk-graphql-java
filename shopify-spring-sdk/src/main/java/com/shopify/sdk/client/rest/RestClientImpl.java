package com.shopify.sdk.client.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.HttpClientConfig;
import com.shopify.sdk.exception.ShopifyApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Implementation of RestClient for making REST API calls to Shopify.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestClientImpl implements RestClient {
    
    private final HttpClientConfig httpClientConfig;
    private final ObjectMapper objectMapper;
    
    @Override
    public Mono<JsonNode> get(String shop, String accessToken, String endpoint, Map<String, Object> queryParams) {
        String url = buildShopifyUrl(shop, endpoint);
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null) {
            queryParams.forEach((key, value) -> {
                if (value != null) {
                    uriBuilder.queryParam(key, value);
                }
            });
        }
        
        return httpClientConfig.getWebClient()
            .get()
            .uri(uriBuilder.build().toUri())
            .header("X-Shopify-Access-Token", accessToken)
            .header("User-Agent", httpClientConfig.getUserAgent())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .map(this::parseResponse)
            .doOnError(error -> log.error("GET request failed for endpoint: {}", endpoint, error))
            .onErrorMap(this::mapToShopifyApiException);
    }
    
    @Override
    public Mono<JsonNode> post(String shop, String accessToken, String endpoint, Object body) {
        String url = buildShopifyUrl(shop, endpoint);
        
        return httpClientConfig.getWebClient()
            .post()
            .uri(url)
            .header("X-Shopify-Access-Token", accessToken)
            .header("User-Agent", httpClientConfig.getUserAgent())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(body != null ? body : "{}")
            .retrieve()
            .bodyToMono(String.class)
            .map(this::parseResponse)
            .doOnError(error -> log.error("POST request failed for endpoint: {}", endpoint, error))
            .onErrorMap(this::mapToShopifyApiException);
    }
    
    @Override
    public Mono<JsonNode> put(String shop, String accessToken, String endpoint, Object body) {
        String url = buildShopifyUrl(shop, endpoint);
        
        return httpClientConfig.getWebClient()
            .put()
            .uri(url)
            .header("X-Shopify-Access-Token", accessToken)
            .header("User-Agent", httpClientConfig.getUserAgent())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(body != null ? body : "{}")
            .retrieve()
            .bodyToMono(String.class)
            .map(this::parseResponse)
            .doOnError(error -> log.error("PUT request failed for endpoint: {}", endpoint, error))
            .onErrorMap(this::mapToShopifyApiException);
    }
    
    @Override
    public Mono<JsonNode> delete(String shop, String accessToken, String endpoint) {
        String url = buildShopifyUrl(shop, endpoint);
        
        return httpClientConfig.getWebClient()
            .delete()
            .uri(url)
            .header("X-Shopify-Access-Token", accessToken)
            .header("User-Agent", httpClientConfig.getUserAgent())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .map(this::parseResponse)
            .doOnError(error -> log.error("DELETE request failed for endpoint: {}", endpoint, error))
            .onErrorMap(this::mapToShopifyApiException);
    }
    
    private String buildShopifyUrl(String shop, String endpoint) {
        String baseUrl = String.format("https://%s.myshopify.com/admin/api/2024-01", 
            shop.replace(".myshopify.com", ""));
        
        if (!endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }
        
        return baseUrl + endpoint;
    }
    
    private JsonNode parseResponse(String responseBody) {
        try {
            if (responseBody == null || responseBody.trim().isEmpty()) {
                return objectMapper.createObjectNode();
            }
            return objectMapper.readTree(responseBody);
        } catch (Exception e) {
            log.error("Failed to parse response body: {}", responseBody, e);
            throw new ShopifyApiException("Failed to parse REST API response", e);
        }
    }
    
    private Throwable mapToShopifyApiException(Throwable throwable) {
        if (throwable instanceof ShopifyApiException) {
            return throwable;
        }
        return new ShopifyApiException("REST API request failed", throwable);
    }
}