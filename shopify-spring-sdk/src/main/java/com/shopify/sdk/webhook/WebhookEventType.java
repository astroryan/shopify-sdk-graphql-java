package com.shopify.sdk.webhook;

/**
 * Enumeration of Shopify webhook event types.
 */
public enum WebhookEventType {
    
    // Order events
    ORDERS_CREATE("orders/create"),
    ORDERS_DELETE("orders/delete"),
    ORDERS_UPDATED("orders/updated"),
    ORDERS_PAID("orders/paid"),
    ORDERS_CANCELLED("orders/cancelled"),
    ORDERS_FULFILLED("orders/fulfilled"),
    ORDERS_PARTIALLY_FULFILLED("orders/partially_fulfilled"),
    
    // Product events
    PRODUCTS_CREATE("products/create"),
    PRODUCTS_UPDATE("products/update"),
    PRODUCTS_DELETE("products/delete"),
    
    // Customer events
    CUSTOMERS_CREATE("customers/create"),
    CUSTOMERS_DELETE("customers/delete"),
    CUSTOMERS_UPDATE("customers/update"),
    CUSTOMERS_ENABLE("customers/enable"),
    CUSTOMERS_DISABLE("customers/disable"),
    
    // Collection events
    COLLECTIONS_CREATE("collections/create"),
    COLLECTIONS_UPDATE("collections/update"),
    COLLECTIONS_DELETE("collections/delete"),
    
    // Cart events
    CARTS_CREATE("carts/create"),
    CARTS_UPDATE("carts/update"),
    
    // Checkout events
    CHECKOUTS_CREATE("checkouts/create"),
    CHECKOUTS_DELETE("checkouts/delete"),
    CHECKOUTS_UPDATE("checkouts/update"),
    
    // Refund events
    REFUNDS_CREATE("refunds/create"),
    
    // App events
    APP_UNINSTALLED("app/uninstalled"),
    APP_SUBSCRIPTIONS_UPDATE("app_subscriptions/update"),
    APP_SUBSCRIPTIONS_APPROACHING_CAPPED_AMOUNT("app_subscriptions/approaching_capped_amount"),
    
    // Shop events
    SHOP_UPDATE("shop/update"),
    
    // Inventory events
    INVENTORY_ITEMS_CREATE("inventory_items/create"),
    INVENTORY_ITEMS_UPDATE("inventory_items/update"),
    INVENTORY_ITEMS_DELETE("inventory_items/delete"),
    INVENTORY_LEVELS_CONNECT("inventory_levels/connect"),
    INVENTORY_LEVELS_UPDATE("inventory_levels/update"),
    INVENTORY_LEVELS_DISCONNECT("inventory_levels/disconnect"),
    
    // Location events
    LOCATIONS_CREATE("locations/create"),
    LOCATIONS_UPDATE("locations/update"),
    LOCATIONS_DELETE("locations/delete"),
    
    // Fulfillment events
    FULFILLMENTS_CREATE("fulfillments/create"),
    FULFILLMENTS_UPDATE("fulfillments/update"),
    
    // Theme events
    THEMES_CREATE("themes/create"),
    THEMES_PUBLISH("themes/publish"),
    THEMES_UPDATE("themes/update"),
    THEMES_DELETE("themes/delete"),
    
    // Draft order events
    DRAFT_ORDERS_CREATE("draft_orders/create"),
    DRAFT_ORDERS_DELETE("draft_orders/delete"),
    DRAFT_ORDERS_UPDATE("draft_orders/update"),
    
    // Fulfillment service events
    FULFILLMENT_SERVICES_CREATE("fulfillment_services/create"),
    FULFILLMENT_SERVICES_UPDATE("fulfillment_services/update"),
    FULFILLMENT_SERVICES_DELETE("fulfillment_services/delete"),
    
    // Tender transaction events
    TENDER_TRANSACTIONS_CREATE("tender_transactions/create"),
    
    // Domain events
    DOMAINS_CREATE("domains/create"),
    DOMAINS_UPDATE("domains/update"),
    DOMAINS_DELETE("domains/delete"),
    
    // Dispute events
    DISPUTES_CREATE("disputes/create"),
    DISPUTES_UPDATE("disputes/update"),
    
    // Subscription billing attempt events
    SUBSCRIPTION_BILLING_ATTEMPTS_SUCCESS("subscription_billing_attempts/success"),
    SUBSCRIPTION_BILLING_ATTEMPTS_FAILURE("subscription_billing_attempts/failure"),
    SUBSCRIPTION_BILLING_ATTEMPTS_CHALLENGED("subscription_billing_attempts/challenged"),
    
    // Locales events
    LOCALES_CREATE("locales/create"),
    LOCALES_UPDATE("locales/update"),
    
    // Markets events
    MARKETS_CREATE("markets/create"),
    MARKETS_DELETE("markets/delete"),
    MARKETS_UPDATE("markets/update"),
    
    // Company events
    COMPANIES_CREATE("companies/create"),
    COMPANIES_DELETE("companies/delete"),
    COMPANIES_UPDATE("companies/update"),
    
    // Company contact events
    COMPANY_CONTACTS_CREATE("company_contacts/create"),
    COMPANY_CONTACTS_DELETE("company_contacts/delete"),
    COMPANY_CONTACTS_UPDATE("company_contacts/update"),
    
    // Company location events
    COMPANY_LOCATIONS_CREATE("company_locations/create"),
    COMPANY_LOCATIONS_DELETE("company_locations/delete"),
    COMPANY_LOCATIONS_UPDATE("company_locations/update"),
    
    // Selling plan group events
    SELLING_PLAN_GROUPS_CREATE("selling_plan_groups/create"),
    SELLING_PLAN_GROUPS_DELETE("selling_plan_groups/delete"),
    SELLING_PLAN_GROUPS_UPDATE("selling_plan_groups/update");
    
    private final String topic;
    
    WebhookEventType(String topic) {
        this.topic = topic;
    }
    
    public String getTopic() {
        return topic;
    }
    
    /**
     * Finds a webhook event type by topic string.
     *
     * @param topic the topic string
     * @return the matching WebhookEventType or null if not found
     */
    public static WebhookEventType fromTopic(String topic) {
        if (topic == null) {
            return null;
        }
        
        for (WebhookEventType eventType : values()) {
            if (eventType.topic.equals(topic)) {
                return eventType;
            }
        }
        
        return null;
    }
    
    /**
     * Checks if this event type is related to orders.
     *
     * @return true if this is an order-related event
     */
    public boolean isOrderEvent() {
        return topic.startsWith("orders/");
    }
    
    /**
     * Checks if this event type is related to products.
     *
     * @return true if this is a product-related event
     */
    public boolean isProductEvent() {
        return topic.startsWith("products/");
    }
    
    /**
     * Checks if this event type is related to customers.
     *
     * @return true if this is a customer-related event
     */
    public boolean isCustomerEvent() {
        return topic.startsWith("customers/");
    }
    
    /**
     * Checks if this event type is related to app lifecycle.
     *
     * @return true if this is an app-related event
     */
    public boolean isAppEvent() {
        return topic.startsWith("app/") || topic.startsWith("app_subscriptions/");
    }
    
    @Override
    public String toString() {
        return topic;
    }
}