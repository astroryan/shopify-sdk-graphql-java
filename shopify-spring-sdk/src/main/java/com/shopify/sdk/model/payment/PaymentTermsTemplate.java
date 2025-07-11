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
public class PaymentTermsTemplate {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("paymentTermsType")
    private PaymentTermsType paymentTermsType;
    
    @JsonProperty("dueInDays")
    private Integer dueInDays;
    
    @JsonProperty("description")
    private String description;
}
