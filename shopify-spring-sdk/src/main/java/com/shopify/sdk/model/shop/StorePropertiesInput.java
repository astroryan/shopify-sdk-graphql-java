package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for updating store properties.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorePropertiesInput {
    /**
     * The name of the shop.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The primary email address of the shop.
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The description of the shop.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The shop's billing address.
     */
    @JsonProperty("billingAddress")
    private MailingAddressInput billingAddress;
}

/**
 * Input for a mailing address.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MailingAddressInput {
    /**
     * The first line of the address.
     */
    @JsonProperty("address1")
    private String address1;
    
    /**
     * The second line of the address.
     */
    @JsonProperty("address2")
    private String address2;
    
    /**
     * The city.
     */
    @JsonProperty("city")
    private String city;
    
    /**
     * The company.
     */
    @JsonProperty("company")
    private String company;
    
    /**
     * The country code.
     */
    @JsonProperty("countryCode")
    private String countryCode;
    
    /**
     * The first name.
     */
    @JsonProperty("firstName")
    private String firstName;
    
    /**
     * The last name.
     */
    @JsonProperty("lastName")
    private String lastName;
    
    /**
     * The phone number.
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The province or state code.
     */
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    /**
     * The zip or postal code.
     */
    @JsonProperty("zip")
    private String zip;
}