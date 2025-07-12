package com.shopify.sdk.client;

import com.shopify.sdk.config.ShopifyAuthContext;
import com.shopify.sdk.exception.ShopifyHttpException;
import com.shopify.sdk.model.common.LogSeverity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Base HTTP client service for making requests to Shopify APIs.
 */
@Service
@RequiredArgsConstructor
public class HttpClientService {
    
    private final HttpClientConfig httpClientConfig;
    
    /**
     * Executes an HTTP request and returns the response.
     *
     * @param context the Shopify configuration context
     * @param request the HTTP request to execute
     * @return Mono of ShopifyHttpResponse
     */
    public Mono<ShopifyHttpResponse> execute(ShopifyAuthContext context, ShopifyHttpRequest request) {
        WebClient webClient = httpClientConfig.createWebClient(context);
        
        return executeRequest(webClient, context, request)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                .filter(this::shouldRetry)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> 
                    new ShopifyHttpException(500, "Max retries exceeded", retrySignal.failure())))
            .doOnSuccess(response -> logResponse(context, request, response))
            .doOnError(error -> logError(context, request, error));
    }
    
    private Mono<ShopifyHttpResponse> executeRequest(WebClient webClient, ShopifyAuthContext context, ShopifyHttpRequest request) {
        WebClient.RequestBodySpec requestSpec = webClient
            .method(request.getMethod())
            .uri(uriBuilder -> {
                uriBuilder.path(request.getUrl());
                if (request.getQueryParams() != null) {
                    request.getQueryParams().forEach((key, value) -> 
                        uriBuilder.queryParam(key, value));
                }
                return uriBuilder.build();
            });
        
        // Add headers
        if (request.getHeaders() != null) {
            request.getHeaders().forEach(requestSpec::header);
        }
        
        // Add body for POST/PUT requests
        if (request.getBody() != null) {
            requestSpec.contentType(MediaType.APPLICATION_JSON);
            requestSpec.bodyValue(request.getBody());
        }
        
        return requestSpec
            .retrieve()
            .toEntity(String.class)
            .map(responseEntity -> {
                Map<String, String> headers = new HashMap<>();
                responseEntity.getHeaders().forEach((key, values) -> {
                    if (!values.isEmpty()) {
                        headers.put(key, values.get(0));
                    }
                });
                
                return new ShopifyHttpResponse(
                    HttpStatus.resolve(responseEntity.getStatusCode().value()),
                    headers,
                    responseEntity.getBody()
                );
            })
            .onErrorMap(WebClientResponseException.class, ex -> {
                return new ShopifyHttpException(
                    ex.getStatusCode().value(),
                    ex.getMessage(),
                    ex.getResponseBodyAsString(),
                    convertHeaders(ex.getHeaders().toSingleValueMap())
                );
            });
    }
    
    private boolean shouldRetry(Throwable throwable) {
        if (throwable instanceof ShopifyHttpException) {
            ShopifyHttpException httpEx = (ShopifyHttpException) throwable;
            // Retry on rate limiting and server errors
            return httpEx.isRateLimited() || httpEx.isServerError();
        }
        return false;
    }
    
    private Map<String, String> convertHeaders(Map<String, String> headers) {
        return headers != null ? new HashMap<>(headers) : new HashMap<>();
    }
    
    private void logResponse(ShopifyAuthContext context, ShopifyHttpRequest request, ShopifyHttpResponse response) {
        if (context.isLogHttpRequests() && context.getLogFunction() != null) {
            String message = String.format(
                "HTTP %s %s -> %d %s",
                request.getMethod(),
                request.getUrl(),
                response.getStatusCode(),
                response.getStatus().getReasonPhrase()
            );
            context.getLogFunction().log(LogSeverity.DEBUG, message);
        }
    }
    
    private void logError(ShopifyAuthContext context, ShopifyHttpRequest request, Throwable error) {
        if (context.getLogFunction() != null) {
            String message = String.format(
                "HTTP %s %s failed: %s",
                request.getMethod(),
                request.getUrl(),
                error.getMessage()
            );
            context.getLogFunction().log(LogSeverity.ERROR, message);
        }
    }
}