package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date and time in ISO 8601 format
 * Shopify uses ISO 8601 format for all date/time values
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DateTime {
    private OffsetDateTime value;
    
    /**
     * Create a DateTime from an OffsetDateTime
     * @param value The OffsetDateTime value
     * @return A new DateTime instance
     */
    public static DateTime of(OffsetDateTime value) {
        return new DateTime(value);
    }
    
    /**
     * Create a DateTime from an Instant
     * @param instant The Instant value
     * @return A new DateTime instance
     */
    public static DateTime of(Instant instant) {
        return new DateTime(instant.atOffset(ZoneOffset.UTC));
    }
    
    /**
     * Create a DateTime from an ISO 8601 string
     * @param value The ISO 8601 formatted string
     * @return A new DateTime instance
     */
    public static DateTime parse(String value) {
        return new DateTime(OffsetDateTime.parse(value));
    }
    
    /**
     * Get the current date and time
     * @return A new DateTime instance with the current time
     */
    public static DateTime now() {
        return new DateTime(OffsetDateTime.now(ZoneOffset.UTC));
    }
    
    @JsonValue
    public String getValue() {
        return value != null ? value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }
    
    public OffsetDateTime getOffsetDateTime() {
        return value;
    }
    
    public void setValue(OffsetDateTime value) {
        this.value = value;
    }
    
    public void setValue(String value) {
        this.value = OffsetDateTime.parse(value);
    }
    
    @Override
    public String toString() {
        return getValue();
    }
}