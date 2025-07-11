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
public class SellingPlanRecurringBillingPolicyInput {
    
    @JsonProperty("interval")
    private SellingPlanInterval interval;
    
    @JsonProperty("intervalCount")
    private Integer intervalCount;
    
    @JsonProperty("anchors")
    private List<SellingPlanAnchorInput> anchors;
    
    @JsonProperty("minCycles")
    private Integer minCycles;
    
    @JsonProperty("maxCycles")
    private Integer maxCycles;
}