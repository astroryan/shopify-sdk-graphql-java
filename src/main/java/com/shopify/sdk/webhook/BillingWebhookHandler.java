package com.shopify.sdk.webhook;

import com.shopify.sdk.service.billing.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingWebhookHandler implements WebhookHandler {
    
    private final BillingService billingService;
    
    @Override
    public boolean canHandle(WebhookEvent event) {
        if (event.getEventType() == null) {
            return false;
        }
        
        switch (event.getEventType()) {
            case APP_SUBSCRIPTIONS_UPDATE:
            case APP_SUBSCRIPTIONS_APPROACHING_CAPPED_AMOUNT:
            case SUBSCRIPTION_BILLING_ATTEMPTS_SUCCESS:
            case SUBSCRIPTION_BILLING_ATTEMPTS_FAILURE:
            case SUBSCRIPTION_BILLING_ATTEMPTS_CHALLENGED:
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public Mono<Void> handle(WebhookEvent event) {
        return Mono.fromRunnable(() -> {
            switch (event.getEventType()) {
                case APP_SUBSCRIPTIONS_UPDATE -> handleAppSubscriptionUpdate(event);
                case APP_SUBSCRIPTIONS_APPROACHING_CAPPED_AMOUNT -> handleApproachingCappedAmount(event);
                case SUBSCRIPTION_BILLING_ATTEMPTS_SUCCESS -> handleBillingSuccess(event);
                case SUBSCRIPTION_BILLING_ATTEMPTS_FAILURE -> handleBillingFailure(event);
                case SUBSCRIPTION_BILLING_ATTEMPTS_CHALLENGED -> handleBillingChallenged(event);
                default -> log.warn("Unhandled billing webhook event: {}", event.getEventType());
            }
        });
    }
    
    @Override
    public int getPriority() {
        return 10; // Higher priority for billing events
    }
    
    @Override
    public WebhookEventType[] getSupportedEventTypes() {
        return new WebhookEventType[] {
            WebhookEventType.APP_SUBSCRIPTIONS_UPDATE,
            WebhookEventType.APP_SUBSCRIPTIONS_APPROACHING_CAPPED_AMOUNT,
            WebhookEventType.SUBSCRIPTION_BILLING_ATTEMPTS_SUCCESS,
            WebhookEventType.SUBSCRIPTION_BILLING_ATTEMPTS_FAILURE,
            WebhookEventType.SUBSCRIPTION_BILLING_ATTEMPTS_CHALLENGED
        };
    }
    
    @Override
    public Mono<Void> onError(WebhookEvent event, Throwable error) {
        return Mono.fromRunnable(() -> 
            log.error("Error processing billing webhook event {} from shop {}: {}", 
                event.getTopic(), event.getShop(), error.getMessage(), error));
    }
    
    private void handleAppSubscriptionUpdate(WebhookEvent event) {
        String subscriptionId = event.getPayloadFieldAsString("admin_graphql_api_id");
        String status = event.getPayloadFieldAsString("status");
        
        log.info("App subscription {} updated to status: {} for shop: {}", 
            subscriptionId, status, event.getShop());
        
        if ("cancelled".equals(status)) {
            log.warn("App subscription {} was cancelled for shop: {}", subscriptionId, event.getShop());
            // Handle subscription cancellation - cleanup, notifications, etc.
        } else if ("active".equals(status)) {
            log.info("App subscription {} is now active for shop: {}", subscriptionId, event.getShop());
            // Handle subscription activation - enable features, send welcome email, etc.
        }
    }
    
    private void handleApproachingCappedAmount(WebhookEvent event) {
        String subscriptionId = event.getPayloadFieldAsString("admin_graphql_api_id");
        String cappedAmount = event.getPayloadFieldAsString("capped_amount");
        String balanceUsed = event.getPayloadFieldAsString("balance_used");
        String balanceRemaining = event.getPayloadFieldAsString("balance_remaining");
        
        log.warn("App subscription {} approaching capped amount. Capped: {}, Used: {}, Remaining: {} for shop: {}", 
            subscriptionId, cappedAmount, balanceUsed, balanceRemaining, event.getShop());
        
        // Send notification to merchant about approaching usage limits
        // Consider automatically upgrading subscription or requesting approval
    }
    
    private void handleBillingSuccess(WebhookEvent event) {
        String subscriptionId = event.getPayloadFieldAsString("admin_graphql_api_id");
        String billingDate = event.getPayloadFieldAsString("billing_on");
        String amount = event.getPayloadFieldAsString("price");
        
        log.info("Billing successful for subscription {} on {}. Amount: {} for shop: {}", 
            subscriptionId, billingDate, amount, event.getShop());
        
        // Handle successful billing - send receipt, update internal records, etc.
    }
    
    private void handleBillingFailure(WebhookEvent event) {
        String subscriptionId = event.getPayloadFieldAsString("admin_graphql_api_id");
        String errorMessage = event.getPayloadFieldAsString("error_message");
        String errorCode = event.getPayloadFieldAsString("error_code");
        
        log.error("Billing failed for subscription {} for shop: {}. Error: {} ({})", 
            subscriptionId, event.getShop(), errorMessage, errorCode);
        
        // Handle billing failure - notify merchant, retry payment, suspend features, etc.
        if ("insufficient_funds".equals(errorCode)) {
            log.warn("Insufficient funds for subscription {} from shop: {}", subscriptionId, event.getShop());
            // Handle insufficient funds scenario
        } else if ("card_declined".equals(errorCode)) {
            log.warn("Card declined for subscription {} from shop: {}", subscriptionId, event.getShop());
            // Handle card declined scenario
        }
    }
    
    private void handleBillingChallenged(WebhookEvent event) {
        String subscriptionId = event.getPayloadFieldAsString("admin_graphql_api_id");
        String challengeType = event.getPayloadFieldAsString("challenge_type");
        
        log.warn("Billing challenged for subscription {} for shop: {}. Challenge type: {}", 
            subscriptionId, event.getShop(), challengeType);
        
        // Handle billing challenge - may require merchant action to resolve
    }
}