package com.shopify.sdk.model.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSubscriptionLineItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("plan")
    private AppPlanV2 plan;
    
    @JsonProperty("usageRecords")
    private AppUsageRecordConnection usageRecords;
}