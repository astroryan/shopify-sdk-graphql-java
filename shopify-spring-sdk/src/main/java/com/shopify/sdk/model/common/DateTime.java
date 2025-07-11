package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTime {
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    
    private OffsetDateTime value;
    
    @JsonCreator
    public static DateTime from(String dateTimeString) {
        if (dateTimeString == null) {
            return null;
        }
        return new DateTime(OffsetDateTime.parse(dateTimeString, ISO_FORMATTER));
    }
    
    public static DateTime from(OffsetDateTime offsetDateTime) {
        return new DateTime(offsetDateTime);
    }
    
    public static DateTime from(Instant instant) {
        return new DateTime(OffsetDateTime.ofInstant(instant, ZoneOffset.UTC));
    }
    
    public static DateTime now() {
        return new DateTime(OffsetDateTime.now());
    }
    
    @JsonValue
    public String toString() {
        if (value == null) {
            return null;
        }
        return value.format(ISO_FORMATTER);
    }
    
    public Instant toInstant() {
        return value != null ? value.toInstant() : null;
    }
    
    public long toEpochMilli() {
        return value != null ? value.toInstant().toEpochMilli() : 0;
    }
}