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
public class CustomerDataRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("dataAccessType")
    private DataAccessType dataAccessType;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("failedAt")
    private DateTime failedAt;
    
    @JsonProperty("failureReason")
    private String failureReason;
    
    @JsonProperty("requestedAt")
    private DateTime requestedAt;
    
    @JsonProperty("shop")
    private Shop shop;
    
    @JsonProperty("state")
    private CustomerDataRequestState state;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}