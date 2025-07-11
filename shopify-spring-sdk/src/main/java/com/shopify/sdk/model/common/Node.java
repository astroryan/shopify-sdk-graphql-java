package com.shopify.sdk.model.common;

/**
 * Interface for all Shopify objects that have a globally unique ID.
 * This follows the GraphQL Node interface pattern.
 */
public interface Node {
    /**
     * Gets the globally unique identifier for this object.
     * @return the ID
     */
    String getId();
}