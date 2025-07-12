package com.shopify.sdk.service.billing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.billing.*;
import com.shopify.sdk.model.billing.input.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    private static final String APP_SUBSCRIPTION_QUERY = """
        query getAppSubscriptions($first: Int, $after: String) {
            currentAppInstallation {
                appSubscriptions(first: $first, after: $after) {
                    edges {
                        cursor
                        node {
                            id
                            name
                            status
                            createdAt
                            currentPeriodEnd
                            lineItems {
                                id
                                plan {
                                    pricingDetails {
                                        ... on AppRecurringPricing {
                                            price {
                                                amount
                                                currencyCode
                                            }
                                            interval
                                        }
                                        ... on AppUsagePricing {
                                            cappedAmount {
                                                amount
                                                currencyCode
                                            }
                                            terms
                                        }
                                    }
                                }
                                usageRecords(first: 10) {
                                    edges {
                                        node {
                                            id
                                            description
                                            price {
                                                amount
                                                currencyCode
                                            }
                                            createdAt
                                        }
                                    }
                                }
                            }
                        }
                    }
                    pageInfo {
                        hasNextPage
                        hasPreviousPage
                        startCursor
                        endCursor
                    }
                }
            }
        }
        """;
    
    private static final String CREATE_APP_SUBSCRIPTION_MUTATION = """
        mutation appSubscriptionCreate($name: String!, $lineItems: [AppSubscriptionLineItemInput!]!, $returnUrl: URL!, $trialDays: Int, $test: Boolean) {
            appSubscriptionCreate(name: $name, lineItems: $lineItems, returnUrl: $returnUrl, trialDays: $trialDays, test: $test) {
                appSubscription {
                    id
                    name
                    status
                    createdAt
                }
                confirmationUrl
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CANCEL_APP_SUBSCRIPTION_MUTATION = """
        mutation appSubscriptionCancel($id: ID!) {
            appSubscriptionCancel(id: $id) {
                appSubscription {
                    id
                    status
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    private static final String CREATE_USAGE_RECORD_MUTATION = """
        mutation appUsageRecordCreate($subscriptionLineItemId: ID!, $description: String!, $price: MoneyInput!) {
            appUsageRecordCreate(subscriptionLineItemId: $subscriptionLineItemId, description: $description, price: $price) {
                usageRecord {
                    id
                    description
                    price {
                        amount
                        currencyCode
                    }
                    createdAt
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    public Mono<AppSubscriptionConnection> getAppSubscriptions(String shop, String accessToken, Integer first, String after) {
        Map<String, Object> variables = Map.of(
            "first", first != null ? first : 10,
            "after", after != null ? after : ""
        );
        
        return graphQLClient.query(shop, accessToken, APP_SUBSCRIPTION_QUERY, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("currentAppInstallation").get("appSubscriptions");
                    return objectMapper.convertValue(data, AppSubscriptionConnection.class);
                } catch (Exception e) {
                    log.error("Failed to parse app subscriptions response", e);
                    throw new RuntimeException("Failed to parse app subscriptions response", e);
                }
            });
    }
    
    public Mono<AppSubscription> createAppSubscription(String shop, String accessToken, AppSubscriptionInput input) {
        Map<String, Object> variables = Map.of(
            "name", input.getName(),
            "lineItems", input.getLineItems(),
            "returnUrl", input.getReturnUrl(),
            "trialDays", input.getTrialDays() != null ? input.getTrialDays() : 0,
            "test", input.getTest() != null ? input.getTest() : false
        );
        
        return graphQLClient.query(shop, accessToken, CREATE_APP_SUBSCRIPTION_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("appSubscriptionCreate").get("appSubscription");
                    return objectMapper.convertValue(data, AppSubscription.class);
                } catch (Exception e) {
                    log.error("Failed to parse create app subscription response", e);
                    throw new RuntimeException("Failed to parse create app subscription response", e);
                }
            });
    }
    
    public Mono<AppSubscription> cancelAppSubscription(String shop, String accessToken, String subscriptionId) {
        Map<String, Object> variables = Map.of("id", subscriptionId);
        
        return graphQLClient.query(shop, accessToken, CANCEL_APP_SUBSCRIPTION_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("appSubscriptionCancel").get("appSubscription");
                    return objectMapper.convertValue(data, AppSubscription.class);
                } catch (Exception e) {
                    log.error("Failed to parse cancel app subscription response", e);
                    throw new RuntimeException("Failed to parse cancel app subscription response", e);
                }
            });
    }
    
    public Mono<UsageRecord> createUsageRecord(String shop, String accessToken, String subscriptionLineItemId, UsageRecordInput input) {
        Map<String, Object> priceInput = Map.of(
            "amount", input.getPrice(),
            "currencyCode", input.getCurrencyCode() != null ? input.getCurrencyCode() : "USD"
        );
        
        Map<String, Object> variables = Map.of(
            "subscriptionLineItemId", subscriptionLineItemId,
            "description", input.getDescription(),
            "price", priceInput
        );
        
        return graphQLClient.query(shop, accessToken, CREATE_USAGE_RECORD_MUTATION, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("appUsageRecordCreate").get("usageRecord");
                    return objectMapper.convertValue(data, UsageRecord.class);
                } catch (Exception e) {
                    log.error("Failed to parse create usage record response", e);
                    throw new RuntimeException("Failed to parse create usage record response", e);
                }
            });
    }
    
    public Mono<RecurringApplicationCharge> createRecurringCharge(String shop, String accessToken, RecurringApplicationChargeInput input) {
        return restClient.post(shop, accessToken, "/admin/api/2023-10/recurring_application_charges.json", 
            Map.of("recurring_application_charge", input))
            .map(response -> {
                try {
                    var data = response.get("recurring_application_charge");
                    return objectMapper.convertValue(data, RecurringApplicationCharge.class);
                } catch (Exception e) {
                    log.error("Failed to parse create recurring charge response", e);
                    throw new RuntimeException("Failed to parse create recurring charge response", e);
                }
            });
    }
    
    public Mono<RecurringApplicationCharge> getRecurringCharge(String shop, String accessToken, String chargeId) {
        return restClient.get(shop, accessToken, "/admin/api/2023-10/recurring_application_charges/" + chargeId + ".json")
            .map(response -> {
                try {
                    var data = response.get("recurring_application_charge");
                    return objectMapper.convertValue(data, RecurringApplicationCharge.class);
                } catch (Exception e) {
                    log.error("Failed to parse get recurring charge response", e);
                    throw new RuntimeException("Failed to parse get recurring charge response", e);
                }
            });
    }
    
    public Mono<RecurringApplicationCharge> activateRecurringCharge(String shop, String accessToken, String chargeId) {
        return restClient.post(shop, accessToken, "/admin/api/2023-10/recurring_application_charges/" + chargeId + "/activate.json", null)
            .map(response -> {
                try {
                    var data = response.get("recurring_application_charge");
                    return objectMapper.convertValue(data, RecurringApplicationCharge.class);
                } catch (Exception e) {
                    log.error("Failed to parse activate recurring charge response", e);
                    throw new RuntimeException("Failed to parse activate recurring charge response", e);
                }
            });
    }
    
    public Mono<Void> cancelRecurringCharge(String shop, String accessToken, String chargeId) {
        return restClient.delete(shop, accessToken, "/admin/api/2023-10/recurring_application_charges/" + chargeId + ".json")
            .then();
    }
    
    public Mono<ApplicationCharge> createOneTimeCharge(String shop, String accessToken, ApplicationChargeInput input) {
        return restClient.post(shop, accessToken, "/admin/api/2023-10/application_charges.json", 
            Map.of("application_charge", input))
            .map(response -> {
                try {
                    var data = response.get("application_charge");
                    return objectMapper.convertValue(data, ApplicationCharge.class);
                } catch (Exception e) {
                    log.error("Failed to parse create one-time charge response", e);
                    throw new RuntimeException("Failed to parse create one-time charge response", e);
                }
            });
    }
    
    public Mono<ApplicationCharge> getApplicationCharge(String shop, String accessToken, String chargeId) {
        return restClient.get(shop, accessToken, "/admin/api/2023-10/application_charges/" + chargeId + ".json")
            .map(response -> {
                try {
                    var data = response.get("application_charge");
                    return objectMapper.convertValue(data, ApplicationCharge.class);
                } catch (Exception e) {
                    log.error("Failed to parse get application charge response", e);
                    throw new RuntimeException("Failed to parse get application charge response", e);
                }
            });
    }
    
    public Mono<ApplicationCredit> createApplicationCredit(String shop, String accessToken, ApplicationCreditInput input) {
        return restClient.post(shop, accessToken, "/admin/api/2023-10/application_credits.json", 
            Map.of("application_credit", input))
            .map(response -> {
                try {
                    var data = response.get("application_credit");
                    return objectMapper.convertValue(data, ApplicationCredit.class);
                } catch (Exception e) {
                    log.error("Failed to parse create application credit response", e);
                    throw new RuntimeException("Failed to parse create application credit response", e);
                }
            });
    }
}