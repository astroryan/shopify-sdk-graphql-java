package com.shopify.sdk.model.billing;

/**
 * Billing intervals for app subscriptions.
 * Maps to Node.js BillingInterval enum.
 */
public enum BillingInterval {
    ONE_TIME("ONE_TIME"),
    EVERY_30_DAYS("EVERY_30_DAYS"),
    ANNUAL("ANNUAL"),
    USAGE("USAGE");

    private final String interval;

    BillingInterval(String interval) {
        this.interval = interval;
    }

    public String getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return interval;
    }

    public boolean isRecurring() {
        return this != ONE_TIME;
    }
}