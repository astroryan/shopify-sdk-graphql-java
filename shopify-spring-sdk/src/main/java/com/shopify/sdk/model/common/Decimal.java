package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a decimal number value
 * Used for precise decimal calculations in Shopify
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Decimal {
    private BigDecimal value;
    
    /**
     * Create a Decimal from a BigDecimal
     * @param value The BigDecimal value
     * @return A new Decimal instance
     */
    public static Decimal of(BigDecimal value) {
        return new Decimal(value);
    }
    
    /**
     * Create a Decimal from a string
     * @param value The string value
     * @return A new Decimal instance
     */
    public static Decimal of(String value) {
        return new Decimal(new BigDecimal(value));
    }
    
    /**
     * Create a Decimal from a double
     * @param value The double value
     * @return A new Decimal instance
     */
    public static Decimal of(double value) {
        return new Decimal(BigDecimal.valueOf(value));
    }
    
    /**
     * Create a Decimal from a long
     * @param value The long value
     * @return A new Decimal instance
     */
    public static Decimal of(long value) {
        return new Decimal(BigDecimal.valueOf(value));
    }
    
    @JsonValue
    public String getValue() {
        return value != null ? value.toPlainString() : null;
    }
    
    public BigDecimal getBigDecimalValue() {
        return value;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    
    public void setValue(String value) {
        this.value = new BigDecimal(value);
    }
    
    /**
     * Add another decimal to this one
     * @param other The other decimal
     * @return A new Decimal with the sum
     */
    public Decimal add(Decimal other) {
        return new Decimal(this.value.add(other.value));
    }
    
    /**
     * Subtract another decimal from this one
     * @param other The other decimal
     * @return A new Decimal with the difference
     */
    public Decimal subtract(Decimal other) {
        return new Decimal(this.value.subtract(other.value));
    }
    
    /**
     * Multiply this decimal by another
     * @param other The other decimal
     * @return A new Decimal with the product
     */
    public Decimal multiply(Decimal other) {
        return new Decimal(this.value.multiply(other.value));
    }
    
    /**
     * Divide this decimal by another
     * @param other The other decimal
     * @param scale The scale for the result
     * @param roundingMode The rounding mode
     * @return A new Decimal with the quotient
     */
    public Decimal divide(Decimal other, int scale, RoundingMode roundingMode) {
        return new Decimal(this.value.divide(other.value, scale, roundingMode));
    }
    
    @Override
    public String toString() {
        return getValue();
    }
}