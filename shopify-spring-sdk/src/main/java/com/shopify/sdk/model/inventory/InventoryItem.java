package com.shopify.sdk.model.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {
    
    private String id;
    private String sku;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("requires_shipping")
    private Boolean requiresShipping;
    
    @JsonProperty("cost")
    private BigDecimal cost;
    
    @JsonProperty("country_code_of_origin")
    private String countryCodeOfOrigin;
    
    @JsonProperty("province_code_of_origin")
    private String provinceCodeOfOrigin;
    
    @JsonProperty("harmonized_system_code")
    private String harmonizedSystemCode;
    
    @JsonProperty("tracked")
    private Boolean tracked;
    
    @JsonProperty("country_harmonized_system_codes")
    private CountryHarmonizedSystemCode[] countryHarmonizedSystemCodes;
    
    @JsonProperty("duplicate_sku_count")
    private Integer duplicateSkuCount;
    
    public boolean isTracked() {
        return tracked != null && tracked;
    }
    
    public boolean requiresShipping() {
        return requiresShipping == null || requiresShipping;
    }
    
    public boolean hasCost() {
        return cost != null && cost.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean hasOriginInformation() {
        return countryCodeOfOrigin != null || provinceCodeOfOrigin != null;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CountryHarmonizedSystemCode {
        @JsonProperty("country_code")
        private String countryCode;
        
        @JsonProperty("harmonized_system_code")
        private String harmonizedSystemCode;
    }
}