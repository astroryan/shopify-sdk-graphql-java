package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a mailing address on Shopify.
 * Used for customer addresses, company locations, and shipping addresses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    /**
     * The first line of the address. Typically the street address or PO Box number.
     */
    @JsonProperty("address1")
    private String address1;
    
    /**
     * The second line of the address. Typically the apartment, suite, or unit number.
     */
    @JsonProperty("address2")
    private String address2;
    
    /**
     * The name of the city, district, village, or town.
     */
    @JsonProperty("city")
    private String city;
    
    /**
     * The name of the customer's company or organization.
     */
    @JsonProperty("company")
    private String company;
    
    /**
     * The name of the country.
     */
    @JsonProperty("country")
    private String country;
    
    /**
     * The two-letter code for the country of the address.
     * For example, US.
     */
    @JsonProperty("countryCode")
    private String countryCode;
    
    /**
     * The first name of the customer.
     */
    @JsonProperty("firstName")
    private String firstName;
    
    /**
     * The last name of the customer.
     */
    @JsonProperty("lastName")
    private String lastName;
    
    /**
     * The full name of the customer, based on firstName and lastName.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * A unique phone number for the address.
     * Formatted using E.164 standard. For example, +16135551111.
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The region of the address, such as the province, state, or district.
     */
    @JsonProperty("province")
    private String province;
    
    /**
     * The two-letter code for the region.
     * For example, ON for Ontario.
     */
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    /**
     * The zip or postal code of the address.
     */
    @JsonProperty("zip")
    private String zip;
    
    /**
     * The latitude coordinate of the address.
     */
    @JsonProperty("latitude")
    private Double latitude;
    
    /**
     * The longitude coordinate of the address.
     */
    @JsonProperty("longitude")
    private Double longitude;
}