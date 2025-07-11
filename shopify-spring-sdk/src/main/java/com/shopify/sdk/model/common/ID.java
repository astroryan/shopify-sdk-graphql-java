package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ID {
    private String value;
    
    @JsonCreator
    public static ID from(String value) {
        return new ID(value);
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    public static ID gid(String resourceType, String id) {
        return new ID(String.format("gid://shopify/%s/%s", resourceType, id));
    }
    
    public boolean isGlobalId() {
        return value != null && value.startsWith("gid://shopify/");
    }
    
    public String extractNumericId() {
        if (!isGlobalId()) {
            return value;
        }
        String[] parts = value.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;
    }
    
    public String extractResourceType() {
        if (!isGlobalId()) {
            return null;
        }
        String[] parts = value.split("/");
        return parts.length >= 4 ? parts[3] : null;
    }
}