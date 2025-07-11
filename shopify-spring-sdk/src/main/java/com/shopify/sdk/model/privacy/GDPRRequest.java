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
public class GDPRRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("customerEmail")
    private String customerEmail;
    
    @JsonProperty("dataCategories")
    private List<DataCategory> dataCategories;
    
    @JsonProperty("processingState")
    private GDPRRequestState processingState;
    
    @JsonProperty("requestType")
    private GDPRRequestType requestType;
    
    @JsonProperty("requestedAt")
    private DateTime requestedAt;
    
    @JsonProperty("completedAt")
    private DateTime completedAt;
    
    @JsonProperty("deniedAt")
    private DateTime deniedAt;
    
    @JsonProperty("deniedReason")
    private String deniedReason;
}