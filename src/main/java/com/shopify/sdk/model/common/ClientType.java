package com.shopify.sdk.model.common;

/**
 * Types of API clients available.
 * Maps to Node.js ClientType enum.
 */
public enum ClientType {
    REST("rest"),
    GRAPHQL("graphql");

    private final String type;

    ClientType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}