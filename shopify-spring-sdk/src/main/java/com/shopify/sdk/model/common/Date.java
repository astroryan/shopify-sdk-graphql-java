package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Shopify Date scalar type wrapper.
 * Represents a date in ISO 8601 format (YYYY-MM-DD).
 */
@Getter
@EqualsAndHashCode
public class Date {
    
    private final LocalDate value;

    public Date(LocalDate value) {
        this.value = value;
    }

    @JsonCreator
    public static Date of(String dateString) {
        return new Date(LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public static Date of(LocalDate date) {
        return new Date(date);
    }

    public static Date today() {
        return new Date(LocalDate.now());
    }

    @JsonValue
    public String toIsoString() {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String toString() {
        return toIsoString();
    }
}