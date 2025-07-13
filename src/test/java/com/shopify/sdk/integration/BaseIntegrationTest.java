package com.shopify.sdk.integration;

import com.shopify.sdk.config.MockWebServerTestConfiguration;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Base class for integration tests using MockWebServer.
 * Provides common setup and teardown for mock server.
 */
@SpringBootTest
@Import(MockWebServerTestConfiguration.class)
@Tag("integration-disabled")
public abstract class BaseIntegrationTest {
    
    protected static MockWebServer mockWebServer;
    
    @BeforeAll
    static void setUpBase() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
    
    @AfterAll
    static void tearDownBase() throws IOException {
        mockWebServer.shutdown();
    }
    
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("shopify.auth.host-name", () -> 
            "localhost:" + mockWebServer.getPort());
    }
    
    @BeforeEach
    void clearRequests() {
        // Clear any queued responses between tests
        while (mockWebServer.getRequestCount() > 0) {
            try {
                mockWebServer.takeRequest(0, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}