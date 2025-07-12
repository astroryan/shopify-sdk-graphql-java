package com.shopify.sdk.model.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a company customer in B2B.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the company.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The external ID of the company.
     */
    @JsonProperty("externalId")
    private String externalId;
    
    /**
     * The note about the company.
     */
    @JsonProperty("note")
    private String note;
    
    /**
     * The date and time when the company was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the company was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * Whether the company is active.
     */
    @JsonProperty("active")
    private Boolean active;
    
    /**
     * The customer lifetime value of the company.
     */
    @JsonProperty("lifetimeDuration")
    private String lifetimeDuration;
    
    /**
     * The company locations.
     */
    @JsonProperty("locations")
    private CompanyLocationConnection locations;
    
    /**
     * The company contacts.
     */
    @JsonProperty("contacts")
    private CompanyContactConnection contacts;
    
    /**
     * The main contact for the company.
     */
    @JsonProperty("mainContact")
    private CompanyContact mainContact;
}