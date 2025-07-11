package com.shopify.sdk.model.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
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
public class Company {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("externalId")
    private String externalId;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("since")
    private DateTime since;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("customerSince")
    private DateTime customerSince;
    
    @JsonProperty("defaultCursor")
    private String defaultCursor;
    
    @JsonProperty("lifetimeDuration")
    private String lifetimeDuration;
    
    @JsonProperty("totalSpent")
    private CompanySpending totalSpent;
    
    @JsonProperty("mainContact")
    private CompanyContact mainContact;
    
    @JsonProperty("contactCount")
    private Integer contactCount;
    
    @JsonProperty("orderCount")
    private Integer orderCount;
    
    @JsonProperty("hasTimelineComment")
    private Boolean hasTimelineComment;
    
    @JsonProperty("locations")
    private CompanyLocationConnection locations;
    
    @JsonProperty("contacts")
    private CompanyContactConnection contacts;
    
    @JsonProperty("catalogs")
    private CatalogConnection catalogs;
}