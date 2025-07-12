package com.shopify.sdk.graphql.scalar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Shopify Money scalar type.
 * Represents a monetary amount as a decimal string.
 */
@Getter
@EqualsAndHashCode
public class MoneyScalar {
    
    private final BigDecimal amount;

    public MoneyScalar(BigDecimal amount) {
        this.amount = amount != null ? amount : BigDecimal.ZERO;
    }

    @JsonCreator
    public static MoneyScalar of(String moneyString) {
        if (moneyString == null || moneyString.trim().isEmpty()) {
            return new MoneyScalar(BigDecimal.ZERO);
        }
        return new MoneyScalar(new BigDecimal(moneyString));
    }

    public static MoneyScalar of(BigDecimal amount) {
        return new MoneyScalar(amount);
    }

    public static MoneyScalar of(double amount) {
        return new MoneyScalar(BigDecimal.valueOf(amount));
    }

    public static MoneyScalar zero() {
        return new MoneyScalar(BigDecimal.ZERO);
    }

    @JsonValue
    public String toString() {
        return amount.toPlainString();
    }

    public BigDecimal toBigDecimal() {
        return amount;
    }

    public double toDouble() {
        return amount.doubleValue();
    }

    public MoneyScalar add(MoneyScalar other) {
        return new MoneyScalar(this.amount.add(other.amount));
    }

    public MoneyScalar subtract(MoneyScalar other) {
        return new MoneyScalar(this.amount.subtract(other.amount));
    }

    public MoneyScalar multiply(MoneyScalar other) {
        return new MoneyScalar(this.amount.multiply(other.amount));
    }

    public MoneyScalar divide(MoneyScalar other) {
        return new MoneyScalar(this.amount.divide(other.amount));
    }
}