package com.shopify.sdk.model.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyContact {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("company")
    private Company company;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("draftOrders")
    private DraftOrderConnection draftOrders;
    
    @JsonProperty("isMainContact")
    private Boolean isMainContact;
    
    @JsonProperty("lifetimeDuration")
    private String lifetimeDuration;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("orders")
    private OrderConnection orders;
    
    @JsonProperty("roleAssignments")
    private CompanyContactRoleAssignmentConnection roleAssignments;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}