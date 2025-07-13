package com.shopify.sdk.integration;

import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Simple integration test to verify Spring context loads correctly
 * and basic API mocking works with MockWebServer.
 */
@DisplayName("Simple API Integration Tests")
public class SimpleApiIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private ApplicationContext context;
    
    @Test
    @DisplayName("Should load Spring context successfully")
    void contextLoads() {
        assertThat(context).isNotNull();
    }
    
    @Test
    @DisplayName("Should connect to MockWebServer")
    void testMockServerConnection() throws Exception {
        // Given
        mockWebServer.enqueue(new MockResponse()
            .setBody("{\"status\":\"ok\"}")
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200));
        
        // Then
        assertThat(mockWebServer.getPort()).isPositive();
        assertThat(mockWebServer.getHostName()).isNotBlank();
        assertThat(mockWebServer.getRequestCount()).isEqualTo(0);
    }
}