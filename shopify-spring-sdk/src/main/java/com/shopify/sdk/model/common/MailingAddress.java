package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a mailing address
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailingAddress {
    /**
     * The first line of the address
     */
    @JsonProperty("address1")
    private String address1;
    
    /**
     * The second line of the address
     */
    @JsonProperty("address2")
    private String address2;
    
    /**
     * The city
     */
    @JsonProperty("city")
    private String city;
    
    /**
     * The company name
     */
    @JsonProperty("company")
    private String company;
    
    /**
     * The country
     */
    @JsonProperty("country")
    private String country;
    
    /**
     * The country code
     */
    @JsonProperty("countryCodeV2")
    private CountryCode countryCodeV2;
    
    /**
     * The first name
     */
    @JsonProperty("firstName")
    private String firstName;
    
    /**
     * The formatted address
     */
    @JsonProperty("formatted")
    private String formatted;
    
    /**
     * The ID of the address
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The last name
     */
    @JsonProperty("lastName")
    private String lastName;
    
    /**
     * The latitude
     */
    @JsonProperty("latitude")
    private Double latitude;
    
    /**
     * The longitude
     */
    @JsonProperty("longitude")
    private Double longitude;
    
    /**
     * The full name
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The phone number
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * The province/state
     */
    @JsonProperty("province")
    private String province;
    
    /**
     * The province/state code
     */
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    /**
     * The postal/zip code
     */
    @JsonProperty("zip")
    private String zip;
}