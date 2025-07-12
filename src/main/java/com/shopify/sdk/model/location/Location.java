package com.shopify.sdk.model.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    
    private String id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String zip;
    private String province;
    private String country;
    private String phone;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("country_code")
    private String countryCode;
    
    @JsonProperty("country_name")
    private String countryName;
    
    @JsonProperty("province_code")
    private String provinceCode;
    
    @JsonProperty("legacy")
    private Boolean legacy;
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    @JsonProperty("localized_country_name")
    private String localizedCountryName;
    
    @JsonProperty("localized_province_name")
    private String localizedProvinceName;
    
    public boolean isActive() {
        return active == null || active;
    }
    
    public boolean isLegacy() {
        return legacy != null && legacy;
    }
    
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        
        if (address1 != null) {
            sb.append(address1);
        }
        
        if (address2 != null && !address2.trim().isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(address2);
        }
        
        if (city != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(city);
        }
        
        if (province != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(province);
        }
        
        if (zip != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(zip);
        }
        
        if (country != null) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(country);
        }
        
        return sb.toString();
    }
}