package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Shopify DateTime scalar type wrapper.
 * Represents an ISO 8601 formatted date and time string.
 */
@Getter
@EqualsAndHashCode
public class DateTime {
    
    private final OffsetDateTime value;

    public DateTime(OffsetDateTime value) {
        this.value = value;
    }

    @JsonCreator
    public static DateTime of(String isoString) {
        return new DateTime(OffsetDateTime.parse(isoString, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    public static DateTime of(OffsetDateTime dateTime) {
        return new DateTime(dateTime);
    }

    public static DateTime now() {
        return new DateTime(OffsetDateTime.now());
    }

    @JsonValue
    public String toIsoString() {
        return value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public String toString() {
        return toIsoString();
    }
}