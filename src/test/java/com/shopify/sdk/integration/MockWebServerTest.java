package com.shopify.sdk.integration;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Simple test to verify MockWebServer setup.
 */
@Tag("integration")
@DisplayName("MockWebServer Basic Tests")
public class MockWebServerTest {
    
    private static MockWebServer mockWebServer;
    private WebClient webClient;
    
    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
    
    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
    
    @BeforeEach
    void init() {
        String baseUrl = String.format("http://%s:%s", 
            mockWebServer.getHostName(), mockWebServer.getPort());
        webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }
    
    @Test
    @DisplayName("Should successfully mock HTTP requests")
    void testBasicMocking() throws Exception {
        // Given
        String mockResponse = "{\"message\": \"Hello World\"}";
        mockWebServer.enqueue(new MockResponse()
            .setBody(mockResponse)
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200));
        
        // When
        StepVerifier.create(webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String.class))
            .expectNext(mockResponse)
            .verifyComplete();
        
        // Then
        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/test");
    }
}