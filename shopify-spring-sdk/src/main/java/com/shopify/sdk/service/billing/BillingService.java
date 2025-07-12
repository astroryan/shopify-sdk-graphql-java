package com.shopify.sdk.service.billing;

import com.shopify.sdk.auth.ShopifyAuthContext;
import com.shopify.sdk.client.GraphQLClient;
import com.shopify.sdk.model.billing.AppSubscription;
import com.shopify.sdk.model.billing.AppUsageRecord;
import com.shopify.sdk.model.graphql.GraphQLRequest;
import com.shopify.sdk.model.graphql.GraphQLResponse;
import lombok.Data;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingService {
    
    private final GraphQLClient graphQLClient;
    
    private static final String CREATE_USAGE_RECORD_MUTATION = """
        mutation appUsageRecordCreate($subscriptionLineItemId: ID!, $price: MoneyInput!, $description: String!) {
            appUsageRecordCreate(
                subscriptionLineItemId: $subscriptionLineItemId,
                price: $price,
                description: $description
            ) {
                appUsageRecord {
                    id
                    createdAt
                    description
                    price {
                        amount
                        currencyCode
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CREATE_SUBSCRIPTION_MUTATION = """
        mutation appSubscriptionCreate(
            $name: String!,
            $returnUrl: URL!,
            $lineItems: [AppSubscriptionLineItemInput!]!,
            $test: Boolean
        ) {
            appSubscriptionCreate(
                name: $name,
                returnUrl: $returnUrl,
                lineItems: $lineItems,
                test: $test
            ) {
                appSubscription {
                    id
                    name
                    status
                    createdAt
                    currentPeriodEnd
                    test
                    trialDays
                }
                confirmationUrl
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CANCEL_SUBSCRIPTION_MUTATION = """
        mutation appSubscriptionCancel($id: ID!, $prorate: Boolean) {
            appSubscriptionCancel(id: $id, prorate: $prorate) {
                appSubscription {
                    id
                    name
                    status
                    createdAt
                    currentPeriodEnd
                    cancelledAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    public AppUsageRecord createUsageRecord(
            ShopifyAuthContext context,
            String subscriptionLineItemId,
            String amount,
            String currencyCode,
            String description) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("subscriptionLineItemId", subscriptionLineItemId);
        
        Map<String, Object> price = new HashMap<>();
        price.put("amount", amount);
        price.put("currencyCode", currencyCode);
        variables.put("price", price);
        variables.put("description", description);
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_USAGE_RECORD_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<UsageRecordCreateData> response = graphQLClient.execute(
                request,
                UsageRecordCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create usage record: {}", response.getErrors());
            throw new RuntimeException("Failed to create usage record");
        }
        
        UsageRecordCreateResponse createResponse = response.getData().getAppUsageRecordCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating usage record: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create usage record: " + createResponse.getUserErrors());
        }
        
        return createResponse.getAppUsageRecord();
    }
    
    public AppSubscriptionCreateResult createSubscription(
            ShopifyAuthContext context,
            String name,
            String returnUrl,
            List<AppSubscriptionLineItemInput> lineItems,
            Boolean test) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("returnUrl", returnUrl);
        variables.put("lineItems", lineItems);
        if (test != null) {
            variables.put("test", test);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CREATE_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SubscriptionCreateData> response = graphQLClient.execute(
                request,
                SubscriptionCreateData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to create subscription: {}", response.getErrors());
            throw new RuntimeException("Failed to create subscription");
        }
        
        SubscriptionCreateResponse createResponse = response.getData().getAppSubscriptionCreate();
        if (createResponse.getUserErrors() != null && !createResponse.getUserErrors().isEmpty()) {
            log.error("User errors creating subscription: {}", createResponse.getUserErrors());
            throw new RuntimeException("Failed to create subscription: " + createResponse.getUserErrors());
        }
        
        return AppSubscriptionCreateResult.builder()
                .appSubscription(createResponse.getAppSubscription())
                .confirmationUrl(createResponse.getConfirmationUrl())
                .build();
    }
    
    public AppSubscription cancelSubscription(
            ShopifyAuthContext context,
            String subscriptionId,
            Boolean prorate) {
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", subscriptionId);
        if (prorate != null) {
            variables.put("prorate", prorate);
        }
        
        GraphQLRequest request = GraphQLRequest.builder()
                .query(CANCEL_SUBSCRIPTION_MUTATION)
                .variables(variables)
                .build();
        
        GraphQLResponse<SubscriptionCancelData> response = graphQLClient.execute(
                request,
                SubscriptionCancelData.class
        );
        
        if (response.hasErrors()) {
            log.error("Failed to cancel subscription: {}", response.getErrors());
            throw new RuntimeException("Failed to cancel subscription");
        }
        
        SubscriptionCancelResponse cancelResponse = response.getData().getAppSubscriptionCancel();
        if (cancelResponse.getUserErrors() != null && !cancelResponse.getUserErrors().isEmpty()) {
            log.error("User errors canceling subscription: {}", cancelResponse.getUserErrors());
            throw new RuntimeException("Failed to cancel subscription: " + cancelResponse.getUserErrors());
        }
        
        return cancelResponse.getAppSubscription();
    }
    
    @Data
    private static class UsageRecordCreateData {
        private UsageRecordCreateResponse appUsageRecordCreate;
    }
    
    @Data
    private static class SubscriptionCreateData {
        private SubscriptionCreateResponse appSubscriptionCreate;
    }
    
    @Data
    private static class SubscriptionCancelData {
        private SubscriptionCancelResponse appSubscriptionCancel;
    }
    
    @Data
    public static class UsageRecordCreateResponse {
        private AppUsageRecord appUsageRecord;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class SubscriptionCreateResponse {
        private AppSubscription appSubscription;
        private String confirmationUrl;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class SubscriptionCancelResponse {
        private AppSubscription appSubscription;
        private List<UserError> userErrors;
    }
    
    @Data
    public static class UserError {
        private List<String> field;
        private String message;
    }
    
    @Data
    public static class AppSubscriptionLineItemInput {
        private AppRecurringPricingInput plan;
    }
    
    @Data
    public static class AppRecurringPricingInput {
        private String interval;
        private MoneyInput price;
    }
    
    @Data
    public static class MoneyInput {
        private String amount;
        private String currencyCode;
    }
    
    @Data
    @Builder
    public static class AppSubscriptionCreateResult {
        private AppSubscription appSubscription;
        private String confirmationUrl;
    }
}