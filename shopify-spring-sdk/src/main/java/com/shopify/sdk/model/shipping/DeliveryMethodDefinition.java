package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a delivery method definition.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodDefinition implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
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
    private DeliveryRateDefinition rateDefinition;
    
    /**
     * The participant for the delivery method.
     */
    @JsonProperty("participant")
    private DeliveryParticipant participant;
}

/**
 * Rate definition for a delivery method.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryRateDefinition {
    /**
     * The ID of the rate definition.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The price of the delivery.
     */
    @JsonProperty("price")
    private MoneyV2 price;
}

/**
 * A participant in delivery.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryParticipant {
    /**
     * The ID of the participant.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the participant.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The type of participant.
     */
    @JsonProperty("participantType")
    private String participantType;
}