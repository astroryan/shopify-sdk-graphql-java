package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Shopify HTML scalar type wrapper.
 * Represents HTML content as a string.
 */
@Getter
@EqualsAndHashCode
public class HTML {
    
    private final String value;

    public HTML(String value) {
        this.value = value != null ? value : "";
    }

    @JsonCreator
    public static HTML of(String htmlString) {
        return new HTML(htmlString);
    }

    public static HTML empty() {
        return new HTML("");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public int length() {
        return value.length();
    }

    @Override
    public String toString() {
        return value;
    }
}