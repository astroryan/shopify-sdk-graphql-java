package com.shopify.sdk.model.common;

/**
 * Severity levels for logging in the Shopify SDK.
 * Maps to Node.js LogSeverity enum.
 */
public enum LogSeverity {
    ERROR(0),
    WARNING(1),
    INFO(2),
    DEBUG(3);

    private final int level;

    LogSeverity(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isEnabled(LogSeverity minimumLevel) {
        return this.level <= minimumLevel.level;
    }
}