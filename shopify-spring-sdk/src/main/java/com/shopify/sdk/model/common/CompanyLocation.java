package com.shopify.sdk.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a company location.
 * Company locations are physical or virtual locations associated with a B2B company.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLocation implements Node {
    /**
     * A globally-unique identifier for the company location
     */
    private String id;
    
    /**
     * The name of the company location
     */
    private String name;
    
    /**
     * The address of the company location
     */
    private Address address;
    
    /**
     * The phone number of the company location
     */
    private String phone;
    
    /**
     * The email of the company location
     */
    private String email;
    
    /**
     * The external ID of the company location
     */
    private String externalId;
}