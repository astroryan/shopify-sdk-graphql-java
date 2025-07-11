package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCarrierService {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("availableServicesForCountries")
    private List<DeliveryAvailableService> availableServicesForCountries;
    
    @JsonProperty("formattedName")
    private String formattedName;
    
    @JsonProperty("icon")
    private Image icon;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryAvailableService {
    
    @JsonProperty("countries")
    private DeliveryCountryCodesOrRestOfWorld countries;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountryCodesOrRestOfWorld {
    
    @JsonProperty("countryCodes")
    private List<CountryCode> countryCodes;
    
    @JsonProperty("restOfWorld")
    private Boolean restOfWorld;
}