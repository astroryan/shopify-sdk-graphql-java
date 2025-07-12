package com.shopify.sdk.model.billing;

/**
 * Behavior when replacing billing subscriptions.
 * Maps to Node.js BillingReplacementBehavior enum.
 */
public enum BillingReplacementBehavior {
    APPLY_IMMEDIATELY("APPLY_IMMEDIATELY"),
    APPLY_ON_NEXT_BILLING_CYCLE("APPLY_ON_NEXT_BILLING_CYCLE"),
    STANDARD("STANDARD");

    private final String behavior;

    BillingReplacementBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getBehavior() {
        return behavior;
    }

    @Override
    public String toString() {
        return behavior;
    }
}