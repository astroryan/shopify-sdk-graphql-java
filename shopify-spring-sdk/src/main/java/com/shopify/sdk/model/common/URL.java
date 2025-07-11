package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;

/**
 * Represents a URL value
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class URL {
    private String value;
    
    /**
     * Create a URL from a string
     * @param value The URL string
     * @return A new URL instance
     */
    public static URL of(String value) {
        return new URL(value);
    }
    
    /**
     * Create a URL from a java.net.URL
     * @param url The java.net.URL
     * @return A new URL instance
     */
    public static URL of(java.net.URL url) {
        return new URL(url.toString());
    }
    
    /**
     * Validate and create a URL from a string
     * @param value The URL string
     * @return A new URL instance
     * @throws IllegalArgumentException if the URL is malformed
     */
    public static URL validate(String value) {
        try {
            new java.net.URL(value);
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + value, e);
        }
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Convert to java.net.URL
     * @return A java.net.URL instance
     * @throws MalformedURLException if the URL is malformed
     */
    public java.net.URL toJavaURL() throws MalformedURLException {
        return new java.net.URL(value);
    }
    
    @Override
    public String toString() {
        return value;
    }
}