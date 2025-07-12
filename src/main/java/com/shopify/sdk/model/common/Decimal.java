package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Shopify Decimal scalar type wrapper.
 * Represents a decimal number with arbitrary precision.
 */
@Getter
@EqualsAndHashCode
public class Decimal {
    
    private final BigDecimal value;

    public Decimal(BigDecimal value) {
        this.value = value;
    }

    @JsonCreator
    public static Decimal of(String decimalString) {
        return new Decimal(new BigDecimal(decimalString));
    }

    public static Decimal of(BigDecimal decimal) {
        return new Decimal(decimal);
    }

    public static Decimal of(double value) {
        return new Decimal(BigDecimal.valueOf(value));
    }

    public static Decimal of(long value) {
        return new Decimal(BigDecimal.valueOf(value));
    }

    public static Decimal zero() {
        return new Decimal(BigDecimal.ZERO);
    }

    @JsonValue
    public String toString() {
        return value.toPlainString();
    }

    public BigDecimal toBigDecimal() {
        return value;
    }

    public double toDouble() {
        return value.doubleValue();
    }

    public Decimal add(Decimal other) {
        return new Decimal(this.value.add(other.value));
    }

    public Decimal subtract(Decimal other) {
        return new Decimal(this.value.subtract(other.value));
    }

    public Decimal multiply(Decimal other) {
        return new Decimal(this.value.multiply(other.value));
    }

    public Decimal divide(Decimal other) {
        return new Decimal(this.value.divide(other.value));
    }
}