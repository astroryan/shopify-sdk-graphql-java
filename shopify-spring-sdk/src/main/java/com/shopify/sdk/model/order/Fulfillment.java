package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fulfillment {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("deliveredAt")
    private DateTime deliveredAt;
    
    @JsonProperty("displayStatus")
    private FulfillmentDisplayStatus displayStatus;
    
    @JsonProperty("estimatedDeliveryAt")
    private DateTime estimatedDeliveryAt;
    
    @JsonProperty("fulfillmentLineItems")
    private FulfillmentLineItemConnection fulfillmentLineItems;
    
    @JsonProperty("fulfillmentOrders")
    private List<FulfillmentOrder> fulfillmentOrders;
    
    @JsonProperty("inTransitAt")
    private DateTime inTransitAt;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("originAddress")
    private FulfillmentOriginAddress originAddress;
    
    @JsonProperty("requiresShipping")
    private Boolean requiresShipping;
    
    @JsonProperty("service")
    private FulfillmentService service;
    
    @JsonProperty("status")
    private FulfillmentStatus status;
    
    @JsonProperty("totalQuantity")
    private Integer totalQuantity;
    
    @JsonProperty("trackingInfo")
    private List<FulfillmentTrackingInfo> trackingInfo;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOriginAddress {
    
    @JsonProperty("address1")
    private String address1;
    
    @JsonProperty("address2")
    private String address2;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("countryCode")
    private String countryCode;
    
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    @JsonProperty("zip")
    private String zip;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentTrackingInfo {
    
    @JsonProperty("company")
    private String company;
    
    @JsonProperty("number")
    private String number;
    
    @JsonProperty("url")
    private String url;
}