package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Shopify Global ID scalar type.
 * Represents a globally unique identifier in the format: gid://shopify/ResourceType/id
 */
@Getter
@EqualsAndHashCode
public class ID {
    
    private final String value;

    public ID(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ID of(String idString) {
        return new ID(idString);
    }

    public static ID of(String resourceType, String id) {
        return new ID(String.format("gid://shopify/%s/%s", resourceType, id));
    }

    public static ID of(String resourceType, Long id) {
        return new ID(String.format("gid://shopify/%s/%d", resourceType, id));
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Extracts the numeric ID from a Shopify Global ID.
     *
     * @return the numeric ID, or null if not extractable
     */
    public String getNumericId() {
        if (value == null || !value.startsWith("gid://shopify/")) {
            return null;
        }
        
        String[] parts = value.split("/");
        if (parts.length >= 4) {
            return parts[parts.length - 1];
        }
        
        return null;
    }

    /**
     * Extracts the resource type from a Shopify Global ID.
     *
     * @return the resource type, or null if not extractable
     */
    public String getResourceType() {
        if (value == null || !value.startsWith("gid://shopify/")) {
            return null;
        }
        
        String[] parts = value.split("/");
        if (parts.length >= 4) {
            return parts[3];
        }
        
        return null;
    }

    /**
     * Checks if this ID represents a specific resource type.
     *
     * @param resourceType the resource type to check
     * @return true if this ID is for the specified resource type
     */
    public boolean isResourceType(String resourceType) {
        return resourceType.equals(getResourceType());
    }

    @Override
    public String toString() {
        return value;
    }
}