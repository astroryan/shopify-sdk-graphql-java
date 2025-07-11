package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * Base interface for pricing values (percentage or fixed amount)
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "__typename"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PricingPercentageValue.class, name = "PricingPercentageValue"),
    @JsonSubTypes.Type(value = PricingFixedAmountValue.class, name = "PricingFixedAmountValue")
})
public interface PricingValue {
    // Marker interface for different pricing value types
}

/**
 * A percentage-based pricing value
 */
@Data
class PricingPercentageValue implements PricingValue {
    private Double percentage;
}

/**
 * A fixed amount pricing value
 */
@Data
class PricingFixedAmountValue implements PricingValue {
    private MoneyBag amount;
}