package com.shopify.sdk.service.billing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.client.graphql.GraphQLResponse;
import com.shopify.sdk.model.billing.AppSubscription;
import com.shopify.sdk.model.billing.RecurringApplicationCharge;
import com.shopify.sdk.model.billing.ApplicationCharge;
import com.shopify.sdk.model.billing.UsageRecord;
import com.shopify.sdk.model.billing.ApplicationCredit;
import com.shopify.sdk.model.billing.input.AppSubscriptionInput;
import com.shopify.sdk.model.billing.input.RecurringApplicationChargeInput;
import com.shopify.sdk.model.billing.input.ApplicationChargeInput;
import com.shopify.sdk.model.billing.input.UsageRecordInput;
import com.shopify.sdk.model.billing.input.ApplicationCreditInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {
    
    @Mock
    private ShopifyGraphQLClient graphQLClient;
    
    @Mock
    private ShopifyRestClient restClient;
    
    private ObjectMapper objectMapper;
    private BillingService billingService;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        billingService = new BillingService(graphQLClient, restClient, objectMapper);
    }
    
    @Test
    @DisplayName("Should create app subscription successfully")
    void testCreateAppSubscription() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        AppSubscriptionInput input = AppSubscriptionInput.builder()
            .name("Premium Plan")
            .lineItems(java.util.Collections.singletonList(
                AppSubscriptionInput.AppSubscriptionLineItemInput.builder()
                    .plan("Premium")
                    .pricingDetails(AppSubscriptionInput.PricingDetailsInput.builder()
                        .price(new BigDecimal("29.99"))
                        .pricingModel("RECURRING")
                        .interval("MONTHLY")
                        .build())
                    .build()
            ))
            .test(true)
            .trialDays(14)
            .returnUrl("https://example.com/return")
            .build();
        
        GraphQLResponse mockResponse = createMockAppSubscriptionResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<AppSubscription> result = billingService.createAppSubscription(shop, accessToken, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(subscription -> {
                assertThat(subscription).isNotNull();
                assertThat(subscription.getName()).isEqualTo("Premium Plan");
                assertThat(subscription.getPrice()).isEqualTo(new BigDecimal("29.99"));
                assertThat(subscription.getTrialDays()).isEqualTo(14);
                assertThat(subscription.getTest()).isTrue();
                assertThat(subscription.getConfirmationUrl()).isNotEmpty();
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should create recurring application charge successfully")
    void testCreateRecurringApplicationCharge() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        RecurringApplicationChargeInput input = RecurringApplicationChargeInput.builder()
            .name("Monthly Subscription")
            .price(new BigDecimal("19.99"))
            .test(false)
            .trialDays(7)
            .build();
        
        JsonNode mockResponse = createMockRecurringChargeResponse();
        
        when(restClient.post(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<RecurringApplicationCharge> result = billingService.createRecurringCharge(shop, accessToken, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(charge -> {
                assertThat(charge).isNotNull();
                assertThat(charge.getName()).isEqualTo("Monthly Subscription");
                assertThat(charge.getPrice()).isEqualTo(new BigDecimal("19.99"));
                assertThat(charge.getTrialDays()).isEqualTo(7);
                assertThat(charge.getConfirmationUrl()).isNotEmpty();
                assertThat(charge.getStatus()).isEqualTo("PENDING");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should create application charge successfully")
    void testCreateApplicationCharge() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ApplicationChargeInput input = ApplicationChargeInput.builder()
            .name("One-time Feature Upgrade")
            .price(new BigDecimal("99.00"))
            .test(true)
            .build();
        
        JsonNode mockResponse = createMockApplicationChargeResponse();
        
        when(restClient.post(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<ApplicationCharge> result = billingService.createOneTimeCharge(shop, accessToken, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(charge -> {
                assertThat(charge).isNotNull();
                assertThat(charge.getName()).isEqualTo("One-time Feature Upgrade");
                assertThat(charge.getPrice()).isEqualTo(new BigDecimal("99.00"));
                assertThat(charge.getTest()).isTrue();
                assertThat(charge.getConfirmationUrl()).isNotEmpty();
                assertThat(charge.getStatus()).isEqualTo("PENDING");
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should create usage record successfully")
    void testCreateUsageRecord() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        String subscriptionLineItemId = "gid://shopify/AppSubscriptionLineItem/123";
        UsageRecordInput input = UsageRecordInput.builder()
            .price(new BigDecimal("5.00"))
            .description("API calls for January")
            .build();
        
        GraphQLResponse mockResponse = createMockUsageRecordResponse();
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<UsageRecord> result = billingService.createUsageRecord(shop, accessToken, subscriptionLineItemId, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(record -> {
                assertThat(record).isNotNull();
                assertThat(record.getPrice()).isEqualTo(new BigDecimal("5.00"));
                assertThat(record.getDescription()).isEqualTo("API calls for January");
                assertThat(record.getCreatedAt()).isNotNull();
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should create application credit successfully")
    void testCreateApplicationCredit() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        ApplicationCreditInput input = ApplicationCreditInput.builder()
            .amount(new BigDecimal("50.00"))
            .description("Service credit for downtime")
            .test(false)
            .build();
        
        JsonNode mockResponse = createMockApplicationCreditResponse();
        
        when(restClient.post(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(mockResponse));
        
        // When
        Mono<ApplicationCredit> result = billingService.createApplicationCredit(shop, accessToken, input);
        
        // Then
        StepVerifier.create(result)
            .assertNext(credit -> {
                assertThat(credit).isNotNull();
                assertThat(credit.getAmount()).isEqualTo(new BigDecimal("50.00"));
                assertThat(credit.getDescription()).isEqualTo("Service credit for downtime");
                assertThat(credit.getTest()).isFalse();
            })
            .verifyComplete();
    }
    
    @Test
    @DisplayName("Should handle billing errors gracefully")
    void testHandleBillingErrors() {
        // Given
        String shop = "test-shop.myshopify.com";
        String accessToken = "test-token";
        
        GraphQLResponse errorResponse = new GraphQLResponse();
        GraphQLResponse.GraphQLError error = new GraphQLResponse.GraphQLError();
        error.setMessage("Invalid subscription parameters");
        error.setExtensions(Map.of("code", "INVALID_SUBSCRIPTION"));
        errorResponse.setErrors(java.util.Collections.singletonList(error));
        
        when(graphQLClient.query(eq(shop), eq(accessToken), anyString(), anyMap()))
            .thenReturn(Mono.just(errorResponse));
        
        AppSubscriptionInput input = AppSubscriptionInput.builder()
            .name("Invalid Plan")
            .lineItems(java.util.Collections.singletonList(
                AppSubscriptionInput.AppSubscriptionLineItemInput.builder()
                    .plan("Invalid")
                    .pricingDetails(AppSubscriptionInput.PricingDetailsInput.builder()
                        .price(new BigDecimal("-10.00")) // Invalid negative price
                        .pricingModel("RECURRING")
                        .interval("MONTHLY")
                        .build())
                    .build()
            ))
            .returnUrl("https://example.com/return")
            .build();
        
        // When
        Mono<AppSubscription> result = billingService.createAppSubscription(shop, accessToken, input);
        
        // Then
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
    }
    
    // Helper methods to create mock responses
    
    private GraphQLResponse createMockAppSubscriptionResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode subscriptionCreate = data.putObject("appSubscriptionCreate");
        
        ObjectNode subscription = subscriptionCreate.putObject("appSubscription");
        subscription.put("id", "gid://shopify/AppSubscription/1");
        subscription.put("name", "Premium Plan");
        subscription.put("price", "29.99");
        subscription.put("trial_days", 14);
        subscription.put("test", true);
        subscription.put("status", "PENDING");
        subscription.put("confirmation_url", "https://test-shop.myshopify.com/admin/charges/1/confirm");
        
        subscriptionCreate.putArray("userErrors");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private JsonNode createMockRecurringChargeResponse() {
        // REST API response structure
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode charge = response.putObject("recurring_application_charge");
        charge.put("id", "1");
        charge.put("name", "Monthly Subscription");
        charge.put("price", "19.99");
        charge.put("trial_days", 7);
        charge.put("status", "PENDING");
        charge.put("test", false);
        charge.put("confirmation_url", "https://test-shop.myshopify.com/admin/charges/1/confirm");
        return response;
    }
    
    private JsonNode createMockApplicationChargeResponse() {
        // REST API response structure
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode charge = response.putObject("application_charge");
        charge.put("id", "2");
        charge.put("name", "One-time Feature Upgrade");
        charge.put("price", "99.00");
        charge.put("status", "PENDING");
        charge.put("test", true);
        charge.put("confirmation_url", "https://test-shop.myshopify.com/admin/charges/2/confirm");
        return response;
    }
    
    private GraphQLResponse createMockUsageRecordResponse() {
        ObjectNode data = objectMapper.createObjectNode();
        ObjectNode usageRecordCreate = data.putObject("appUsageRecordCreate");
        
        ObjectNode usageRecord = usageRecordCreate.putObject("usageRecord");
        usageRecord.put("id", "gid://shopify/AppUsageRecord/1");
        usageRecord.put("price", "5.00");
        usageRecord.put("description", "API calls for January");
        usageRecord.put("created_at", "2024-01-15T10:00:00Z");
        
        usageRecordCreate.putArray("userErrors");
        
        GraphQLResponse response = new GraphQLResponse();
        response.setData(data);
        return response;
    }
    
    private JsonNode createMockApplicationCreditResponse() {
        // REST API response structure
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode credit = response.putObject("application_credit");
        credit.put("id", "1");
        credit.put("amount", "50.00");
        credit.put("description", "Service credit for downtime");
        credit.put("test", false);
        return response;
    }
}