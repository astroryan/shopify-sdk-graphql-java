package com.shopify.sdk.model.b2b;

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
public class CompanyLocation {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("externalId")
    private String externalId;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("billingAddress")
    private CompanyAddress billingAddress;
    
    @JsonProperty("shippingAddress")
    private CompanyAddress shippingAddress;
    
    @JsonProperty("buyerExperienceConfiguration")
    private BuyerExperienceConfiguration buyerExperienceConfiguration;
    
    @JsonProperty("catalogs")
    private CatalogConnection catalogs;
    
    @JsonProperty("company")
    private Company company;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("currency")
    private CurrencyCode currency;
    
    @JsonProperty("defaultCursor")
    private String defaultCursor;
    
    @JsonProperty("draftOrders")
    private DraftOrderConnection draftOrders;
    
    @JsonProperty("hasTimelineComment")
    private Boolean hasTimelineComment;
    
    @JsonProperty("market")
    private Market market;
    
    @JsonProperty("orderCount")
    private Integer orderCount;
    
    @JsonProperty("orders")
    private OrderConnection orders;
    
    @JsonProperty("roleAssignments")
    private CompanyContactRoleAssignmentConnection roleAssignments;
    
    @JsonProperty("taxExemptions")
    private List<TaxExemption> taxExemptions;
    
    @JsonProperty("taxRegistrationId")
    private String taxRegistrationId;
    
    @JsonProperty("totalSpent")
    private MoneyV2 totalSpent;
}