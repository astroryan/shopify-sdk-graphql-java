package com.shopify.sdk.model.common;

/**
 * Functional interface for custom logging implementations.
 * Maps to Node.js LogFunction type.
 */
@FunctionalInterface
public interface LogFunction {
    
    /**
     * Log a message with the specified severity level.
     *
     * @param severity the severity level of the message
     * @param message the message to log
     */
    void log(LogSeverity severity, String message);
}