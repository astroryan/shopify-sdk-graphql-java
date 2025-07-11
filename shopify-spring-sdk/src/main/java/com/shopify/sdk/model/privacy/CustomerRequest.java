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
public class CustomerRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("dataRequest")
    private CustomerDataRequest dataRequest;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("requestDate")
    private DateTime requestDate;
    
    @JsonProperty("requestType")
    private CustomerRequestType requestType;
    
    @JsonProperty("status")
    private CustomerRequestStatus status;
}