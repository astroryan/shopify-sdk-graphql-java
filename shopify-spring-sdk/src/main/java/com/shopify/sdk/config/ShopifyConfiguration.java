package com.shopify.sdk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.client.RateLimiter;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(ShopifyProperties.class)
public class ShopifyConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient(ShopifyProperties properties) {
        ShopifyProperties.Api.Timeout timeout = properties.getApi().getTimeout();
        
        return new OkHttpClient.Builder()
                .connectTimeout(timeout.getConnect().toMillis(), TimeUnit.MILLISECONDS)
                .readTimeout(timeout.getRead().toMillis(), TimeUnit.MILLISECONDS)
                .writeTimeout(timeout.getWrite().toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public RateLimiter rateLimiter(ShopifyProperties properties) {
        return new RateLimiter(
                properties.getRateLimit().getMaxCallsPerSecond(),
                properties.getRateLimit().getBucketSize()
        );
    }
    
    @Bean
    @ConditionalOnMissingBean
    public GraphQLClient graphQLClient(
            OkHttpClient okHttpClient,
            ObjectMapper objectMapper,
            RateLimiter rateLimiter,
            ShopifyProperties properties) {
        
        return new ShopifyGraphQLClient(
                okHttpClient,
                objectMapper,
                rateLimiter,
                properties
        );
    }
}