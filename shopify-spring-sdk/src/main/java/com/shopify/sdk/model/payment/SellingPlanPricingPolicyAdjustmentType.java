package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


public enum SellingPlanPricingPolicyAdjustmentType {
    PERCENTAGE,
    FIXED_AMOUNT,
    PRICE
}