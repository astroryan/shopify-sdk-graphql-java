package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Shopify JSON scalar type wrapper.
 * Represents arbitrary JSON content.
 */
@Getter
@EqualsAndHashCode
public class JSON {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    private final String value;

    public JSON(String value) {
        this.value = value != null ? value : "{}";
    }

    @JsonCreator
    public static JSON of(String jsonString) {
        return new JSON(jsonString);
    }

    public static JSON of(Object object) {
        try {
            return new JSON(OBJECT_MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize object to JSON", e);
        }
    }

    public static JSON empty() {
        return new JSON("{}");
    }

    @JsonValue
    @JsonRawValue
    public String getValue() {
        return value;
    }

    public JsonNode asJsonNode() {
        try {
            return OBJECT_MAPPER.readTree(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON content", e);
        }
    }

    public <T> T as(Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(value, type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot deserialize JSON to " + type.getSimpleName(), e);
        }
    }

    public boolean isEmpty() {
        return "{}".equals(value) || "[]".equals(value) || value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }
}