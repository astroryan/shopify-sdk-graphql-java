package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryProfile {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("activeMethodDefinitionsCount")
    private Integer activeMethodDefinitionsCount;
    
    @JsonProperty("default")
    private Boolean isDefault;
    
    @JsonProperty("legacyMode")
    private Boolean legacyMode;
    
    @JsonProperty("locationsWithoutRatesCount")
    private Integer locationsWithoutRatesCount;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("originLocationCount")
    private Integer originLocationCount;
    
    @JsonProperty("productVariantsCountV2")
    private DeliveryProductVariantsCount productVariantsCountV2;
    
    @JsonProperty("profileItems")
    private DeliveryProfileItemConnection profileItems;
    
    @JsonProperty("profileLocationGroups")
    private List<DeliveryProfileLocationGroup> profileLocationGroups;
    
    @JsonProperty("sellingPlanGroups")
    private SellingPlanGroupConnection sellingPlanGroups;
    
    @JsonProperty("unassignedLocations")
    private List<Location> unassignedLocations;
    
    @JsonProperty("usedAsShippingProfiles")
    private Boolean usedAsShippingProfiles;
    
    @JsonProperty("zoneCountryCount")
    private Integer zoneCountryCount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProductVariantsCount {
    
    @JsonProperty("capped")
    private Boolean capped;
    
    @JsonProperty("count")
    private Integer count;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProfileItemConnection {
    
    @JsonProperty("edges")
    private List<DeliveryProfileItemEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProfileItemEdge {
    
    @JsonProperty("node")
    private DeliveryProfileItem node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProfileItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("product")
    private Product product;
    
    @JsonProperty("variants")
    private ProductVariantConnection variants;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProfileLocationGroup {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("countriesInAnyZone")
    private List<DeliveryCountryAndZone> countriesInAnyZone;
    
    @JsonProperty("locationGroup")
    private DeliveryLocationGroup locationGroup;
    
    @JsonProperty("locationGroupZones")
    private DeliveryLocationGroupZoneConnection locationGroupZones;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountryAndZone {
    
    @JsonProperty("country")
    private DeliveryCountry country;
    
    @JsonProperty("zone")
    private String zone;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountry {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("code")
    private DeliveryCountryCodeOrRestOfWorld code;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("provinces")
    private List<DeliveryProvince> provinces;
    
    @JsonProperty("translatedName")
    private String translatedName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountryCodeOrRestOfWorld {
    
    @JsonProperty("countryCode")
    private CountryCode countryCode;
    
    @JsonProperty("restOfWorld")
    private Boolean restOfWorld;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryProvince {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("translatedName")
    private String translatedName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroup {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("locations")
    private LocationConnection locations;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroupZoneConnection {
    
    @JsonProperty("edges")
    private List<DeliveryLocationGroupZoneEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroupZoneEdge {
    
    @JsonProperty("node")
    private DeliveryLocationGroupZone node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroupZone {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("methodDefinitionCounts")
    private DeliveryMethodDefinitionCounts methodDefinitionCounts;
    
    @JsonProperty("methodDefinitions")
    private DeliveryMethodDefinitionConnection methodDefinitions;
    
    @JsonProperty("zone")
    private DeliveryZone zone;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryMethodDefinitionCounts {
    
    @JsonProperty("participantDefinitionsCount")
    private Integer participantDefinitionsCount;
    
    @JsonProperty("rateDefinitionsCount")
    private Integer rateDefinitionsCount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SellingPlanGroupConnection {
    
    @JsonProperty("edges")
    private List<SellingPlanGroupEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SellingPlanGroupEdge {
    
    @JsonProperty("node")
    private SellingPlanGroup node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SellingPlanGroup {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Location {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class LocationConnection {
    
    @JsonProperty("edges")
    private List<LocationEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class LocationEdge {
    
    @JsonProperty("node")
    private Location node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductVariantConnection {
    
    @JsonProperty("edges")
    private List<ProductVariantEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductVariantEdge {
    
    @JsonProperty("node")
    private ProductVariant node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductVariant {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}