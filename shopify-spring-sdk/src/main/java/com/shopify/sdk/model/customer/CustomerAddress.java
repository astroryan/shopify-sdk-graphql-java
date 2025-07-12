package com.shopify.sdk.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a customer address.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {
    
    /**
     * A globally-unique identifier.
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The first line of the address. Typically the street address or PO Box number.
     */
    @JsonProperty("address1")
    private String address1;
    
    /**
     * The second line of the address. Typically the number of the apartment, suite, or unit.
     */
    @JsonProperty("address2")
    private String address2;
    
    /**
     * The name of the city, district, village, or town.
     */
    @JsonProperty("city")
    private String city;
    
    /**
     * The name of the company or organization to ship to.
     */
    @JsonProperty("company")
    private String company;
    
    /**
     * The two-letter code for the country of the address.
     */
    @JsonProperty("country")
    private String country;
    
    /**
     * The two-letter code for the country of the address.
     */
    @JsonProperty("countryCode")
    private String countryCode;
    
    /**
     * The first name of the person to ship to.
     */
    @JsonProperty("firstName")
    private String firstName;
    
    /**
     * A formatted version of the address, customized by the provided arguments.
     */
    @JsonProperty("formatted")
    private String formatted;
    
    /**
     * A comma-separated list of the values for city, province, and country.
     */
    @JsonProperty("formattedArea")
    private String formattedArea;
    
    /**
     * The last name of the person to ship to.
     */
    @JsonProperty("lastName")
    private String lastName;
    
    /**
     * The latitude coordinate of the customer address.
     */
    @JsonProperty("latitude")
    private Double latitude;
    
    /**
     * The longitude coordinate of the customer address.
     */
    @JsonProperty("longitude")
    private Double longitude;
    
    /**
     * The full name of the person to ship to.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * A unique phone number for the customer.
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
     */
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    /**
     * The zip or postal code of the address.
     */
    @JsonProperty("zip")
    private String zip;
}