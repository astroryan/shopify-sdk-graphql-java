package com.shopify.sdk.model.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a company contact.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyContact implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The customer associated with this contact.
     */
    @JsonProperty("customer")
    private Customer customer;
    
    /**
     * The company this contact belongs to.
     */
    @JsonProperty("company")
    private Company company;
    
    /**
     * The title of the contact.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The locale of the contact.
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * Whether this is the main contact.
     */
    @JsonProperty("isMainContact")
    private Boolean isMainContact;
    
    /**
     * The date and time when the contact was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the contact was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}