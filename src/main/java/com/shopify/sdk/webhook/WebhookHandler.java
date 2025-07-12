package com.shopify.sdk.webhook;

import reactor.core.publisher.Mono;

/**
 * Interface for handling webhook events.
 * Implementations should be annotated with @Component to be automatically detected.
 */
public interface WebhookHandler {
    
    /**
     * Checks if this handler can process the given webhook event.
     *
     * @param event the webhook event
     * @return true if this handler can process the event
     */
    boolean canHandle(WebhookEvent event);
    
    /**
     * Processes the webhook event.
     *
     * @param event the webhook event to process
     * @return Mono that completes when processing is done
     */
    Mono<Void> handle(WebhookEvent event);
    
    /**
     * Gets the priority of this handler (lower numbers = higher priority).
     * Default is 100. Handlers with higher priority will be processed first.
     *
     * @return the handler priority
     */
    default int getPriority() {
        return 100;
    }
    
    /**
     * Gets the supported event types for this handler.
     * Return null or empty array to handle all event types (based on canHandle method).
     *
     * @return array of supported event types
     */
    default WebhookEventType[] getSupportedEventTypes() {
        return null;
    }
    
    /**
     * Called when an error occurs during event processing.
     * Default implementation does nothing.
     *
     * @param event the webhook event that failed
     * @param error the error that occurred
     * @return Mono that completes when error handling is done
     */
    default Mono<Void> onError(WebhookEvent event, Throwable error) {
        return Mono.empty();
    }
}