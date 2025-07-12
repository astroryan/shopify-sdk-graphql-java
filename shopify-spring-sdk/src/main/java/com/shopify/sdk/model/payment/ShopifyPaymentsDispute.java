package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a dispute on a Shopify Payments transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsDispute implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The amount disputed.
     */
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    /**
     * The deadline for submitting evidence.
     */
    @JsonProperty("evidenceDueBy")
    private OffsetDateTime evidenceDueBy;
    
    /**
     * The date when evidence was sent.
     */
    @JsonProperty("evidenceSentOn")
    private OffsetDateTime evidenceSentOn;
    
    /**
     * The date when the dispute was finalized.
     */
    @JsonProperty("finalizedOn")
    private OffsetDateTime finalizedOn;
    
    /**
     * The date when the dispute was initiated.
     */
    @JsonProperty("initiatedAt")
    private OffsetDateTime initiatedAt;
    
    /**
     * The legacy resource ID.
     */
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    /**
     * The network reason code.
     */
    @JsonProperty("networkReasonCode")
    private String networkReasonCode;
    
    /**
     * The order associated with the dispute.
     */
    @JsonProperty("order")
    private Order order;
    
    /**
     * The reason details for the dispute.
     */
    @JsonProperty("reasonDetails")
    private ShopifyPaymentsDisputeReasonDetails reasonDetails;
    
    /**
     * The status of the dispute.
     */
    @JsonProperty("status")
    private ShopifyPaymentsDisputeStatus status;
    
    /**
     * The type of dispute.
     */
    @JsonProperty("type")
    private ShopifyPaymentsDisputeType type;
}

/**
 * The reason details for a dispute.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsDisputeReasonDetails {
    /**
     * The network reason code.
     */
    @JsonProperty("networkReasonCode")
    private String networkReasonCode;
    
    /**
     * The human-readable reason.
     */
    @JsonProperty("reason")
    private String reason;
}

/**
 * The status of a Shopify Payments dispute.
 */
enum ShopifyPaymentsDisputeStatus {
    @JsonProperty("NEEDS_RESPONSE")
    NEEDS_RESPONSE,
    
    @JsonProperty("UNDER_REVIEW")
    UNDER_REVIEW,
    
    @JsonProperty("CHARGE_REFUNDED")
    CHARGE_REFUNDED,
    
    @JsonProperty("ACCEPTED")
    ACCEPTED,
    
    @JsonProperty("WON")
    WON,
    
    @JsonProperty("LOST")
    LOST
}

/**
 * The type of Shopify Payments dispute.
 */
enum ShopifyPaymentsDisputeType {
    @JsonProperty("CHARGEBACK")
    CHARGEBACK,
    
    @JsonProperty("INQUIRY")
    INQUIRY
}