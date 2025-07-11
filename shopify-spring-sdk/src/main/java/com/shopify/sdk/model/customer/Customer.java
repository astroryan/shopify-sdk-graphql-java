package com.shopify.sdk.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a Shopify customer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    /**
     * The globally unique identifier for the customer
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The customer's email address
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The customer's phone number
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The customer's first name
     */
    @JsonProperty("firstName")
    private String firstName;
    
    /**
     * The customer's last name
     */
    @JsonProperty("lastName")
    private String lastName;
    
    /**
     * The customer's display name
     */
    @JsonProperty("displayName")
    private String displayName;
    
    /**
     * Whether the customer has accepted marketing
     */
    @JsonProperty("acceptsMarketing")
    private Boolean acceptsMarketing;
    
    /**
     * When the customer accepted marketing
     */
    @JsonProperty("acceptsMarketingUpdatedAt")
    private DateTime acceptsMarketingUpdatedAt;
    
    /**
     * The date and time when the customer was created
     */
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    /**
     * The date and time when the customer was last updated
     */
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    /**
     * The state of the customer's account
     */
    @JsonProperty("state")
    private CustomerState state;
    
    /**
     * The customer's note
     */
    @JsonProperty("note")
    private String note;
    
    /**
     * Whether the customer is tax exempt
     */
    @JsonProperty("taxExempt")
    private Boolean taxExempt;
    
    /**
     * Tax exemptions for the customer
     */
    @JsonProperty("taxExemptions")
    private List<String> taxExemptions;
    
    /**
     * Tags associated with the customer
     */
    @JsonProperty("tags")
    private List<String> tags;
    
    /**
     * The default address for the customer
     */
    @JsonProperty("defaultAddress")
    private MailingAddress defaultAddress;
}