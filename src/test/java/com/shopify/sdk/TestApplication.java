package com.shopify.sdk;

import com.shopify.sdk.config.ShopifyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Test application for integration tests.
 * This class is only used for testing purposes.
 */
@SpringBootApplication
@EnableConfigurationProperties(ShopifyProperties.class)
public class TestApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}