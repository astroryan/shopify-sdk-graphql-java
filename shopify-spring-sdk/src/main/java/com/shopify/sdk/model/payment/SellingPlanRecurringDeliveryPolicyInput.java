package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanRecurringDeliveryPolicyInput {
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("cutoff")
    private String cutoff;
    
    @JsonProperty("intent")
    private SellingPlanRecurringDeliveryPolicyIntent intent;
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
    
    @JsonProperty("preAnchorBehavior")
    private SellingPlanRecurringDeliveryPolicyPreAnchorBehavior preAnchorBehavior;
}