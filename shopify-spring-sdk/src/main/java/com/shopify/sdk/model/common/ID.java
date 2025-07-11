package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a Shopify ID (Global ID)
 * Shopify uses global IDs in the format: gid://shopify/ResourceType/123456789
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ID {
    private String value;
    
    /**
     * Create an ID from a string value
     * @param value The ID string
     * @return A new ID instance
     */
    public static ID of(String value) {
        return new ID(value);
    }
    
    /**
     * Create a Shopify global ID
     * @param resourceType The resource type (e.g., "Product", "Order")
     * @param id The numeric ID
     * @return A new ID instance with global ID format
     */
    public static ID globalId(String resourceType, String id) {
        return new ID(String.format("gid://shopify/%s/%s", resourceType, id));
    }
    
    /**
     * Create a Shopify global ID
     * @param resourceType The resource type (e.g., "Product", "Order")
     * @param id The numeric ID
     * @return A new ID instance with global ID format
     */
    public static ID globalId(String resourceType, Long id) {
        return globalId(resourceType, String.valueOf(id));
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}