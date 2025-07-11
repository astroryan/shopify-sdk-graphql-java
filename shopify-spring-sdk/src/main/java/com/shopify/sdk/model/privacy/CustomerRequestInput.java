package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestInput {
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("requestType")
    private CustomerRequestType requestType;
    
    @JsonProperty("dataCategories")
    private List<DataCategory> dataCategories;
    
    @JsonProperty("reason")
    private String reason;
}