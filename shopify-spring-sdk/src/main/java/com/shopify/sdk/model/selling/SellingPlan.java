package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a selling plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlan implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the selling plan.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The description of the selling plan.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The options for the selling plan.
     */
    @JsonProperty("options")
    private List<String> options;
    
    /**
     * The position of the selling plan.
     */
    @JsonProperty("position")
    private Integer position;
    
    /**
     * The billing policy of the selling plan.
     */
    @JsonProperty("billingPolicy")
    private SellingPlanBillingPolicy billingPolicy;
    
    /**
     * The delivery policy of the selling plan.
     */
    @JsonProperty("deliveryPolicy")
    private SellingPlanDeliveryPolicy deliveryPolicy;
    
    /**
     * The pricing policies of the selling plan.
     */
    @JsonProperty("pricingPolicies")
    private List<SellingPlanPricingPolicy> pricingPolicies;
    
    /**
     * The date and time when the selling plan was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
}