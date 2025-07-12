package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating or updating a delivery method definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodDefinitionInput {
    /**
     * The name of the delivery method.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The description of the delivery method.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * Whether the delivery method is active.
     */
    @JsonProperty("active")
    private Boolean active;
    
    /**
     * The rate definition for the delivery method.
     */
    @JsonProperty("rateDefinition")
    private DeliveryRateDefinitionInput rateDefinition;
    
    /**
     * The participant for the delivery method.
     */
    @JsonProperty("participant")
    private DeliveryParticipantInput participant;
    
    /**
     * The weight conditions for the delivery method.
     */
    @JsonProperty("weightConditionsToCreate")
    private List<DeliveryWeightConditionInput> weightConditionsToCreate;
    
    /**
     * The price conditions for the delivery method.
     */
    @JsonProperty("priceConditionsToCreate")
    private List<DeliveryPriceConditionInput> priceConditionsToCreate;
}

/**
 * Input for a delivery rate definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryRateDefinitionInput {
    /**
     * The price of the delivery.
     */
    @JsonProperty("price")
    private MoneyInput price;
}

/**
 * Input for money values.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MoneyInput {
    /**
     * The amount.
     */
    @JsonProperty("amount")
    private String amount;
    
    /**
     * The currency code.
     */
    @JsonProperty("currencyCode")
    private String currencyCode;
}

/**
 * Input for a delivery participant.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryParticipantInput {
    /**
     * The ID of the participant.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The carrier service ID.
     */
    @JsonProperty("carrierServiceId")
    private String carrierServiceId;
    
    /**
     * Fixed fee details.
     */
    @JsonProperty("fixedFee")
    private DeliveryFixedFeeInput fixedFee;
}

/**
 * Input for a fixed fee.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryFixedFeeInput {
    /**
     * The amount of the fixed fee.
     */
    @JsonProperty("amount")
    private MoneyInput amount;
}

/**
 * Input for a weight condition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryWeightConditionInput {
    /**
     * The minimum weight.
     */
    @JsonProperty("weightGreaterThanOrEqualTo")
    private Double weightGreaterThanOrEqualTo;
    
    /**
     * The maximum weight.
     */
    @JsonProperty("weightLessThanOrEqualTo")
    private Double weightLessThanOrEqualTo;
}

/**
 * Input for a price condition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryPriceConditionInput {
    /**
     * The minimum price.
     */
    @JsonProperty("priceGreaterThanOrEqualTo")
    private MoneyInput priceGreaterThanOrEqualTo;
    
    /**
     * The maximum price.
     */
    @JsonProperty("priceLessThanOrEqualTo")
    private MoneyInput priceLessThanOrEqualTo;
}