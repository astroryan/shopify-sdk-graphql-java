package com.shopify.sdk.model.billing.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppSubscriptionInput {
    
    private String name;
    
    @JsonProperty("line_items")
    private List<AppSubscriptionLineItemInput> lineItems;
    
    @JsonProperty("trial_days")
    private Integer trialDays;
    
    @JsonProperty("return_url")
    private String returnUrl;
    
    @JsonProperty("test")
    private Boolean test;
    
    @JsonProperty("replacement_behavior")
    private String replacementBehavior;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AppSubscriptionLineItemInput {
        private String plan;
        
        @JsonProperty("pricing_details")
        private PricingDetailsInput pricingDetails;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PricingDetailsInput {
        private BigDecimal price;
        
        @JsonProperty("pricing_model")
        private String pricingModel;
        
        @JsonProperty("interval")
        private String interval;
        
        @JsonProperty("capped_amount")
        private BigDecimal cappedAmount;
        
        @JsonProperty("terms")
        private String terms;
    }
}