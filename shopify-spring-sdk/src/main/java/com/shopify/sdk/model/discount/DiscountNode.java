package com.shopify.sdk.model.discount;

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
public class DiscountNode {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("discount")
    private Discount discount;
    
    @JsonProperty("discountClass")
    private DiscountClass discountClass;
    
    @JsonProperty("events")
    private DiscountEventConnection events;
    
    @JsonProperty("metafield")
    private Metafield metafield;
    
    @JsonProperty("metafields")
    private MetafieldConnection metafields;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Discount {
    
    @JsonProperty("__typename")
    private String typename;
}

public enum DiscountClass {
    PRODUCT,
    ORDER,
    SHIPPING
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountEventConnection {
    
    @JsonProperty("edges")
    private List<DiscountEventEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountEventEdge {
    
    @JsonProperty("node")
    private DiscountEvent node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountEvent {
    
    @JsonProperty("appTitle")
    private String appTitle;
    
    @JsonProperty("attributeToApp")
    private Boolean attributeToApp;
    
    @JsonProperty("attributeToUser")
    private Boolean attributeToUser;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("criticalAlert")
    private Boolean criticalAlert;
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("message")
    private String message;
}