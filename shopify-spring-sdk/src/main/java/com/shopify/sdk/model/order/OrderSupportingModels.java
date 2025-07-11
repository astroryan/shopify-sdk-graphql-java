package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountAllocation {
    
    @JsonProperty("allocatedAmountSet")
    private MoneyBag allocatedAmountSet;
    
    @JsonProperty("discountApplication")
    private DiscountApplication discountApplication;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountApplication {
    
    @JsonProperty("index")
    private Integer index;
    
    @JsonProperty("targetSelection")
    private String targetSelection;
    
    @JsonProperty("targetType")
    private String targetType;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("value")
    private PricingValue value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TaxLine {
    
    @JsonProperty("channelLiable")
    private Boolean channelLiable;
    
    @JsonProperty("priceSet")
    private MoneyBag priceSet;
    
    @JsonProperty("rate")
    private Double rate;
    
    @JsonProperty("ratePercentage")
    private Double ratePercentage;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentService {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    
    @JsonProperty("fulfillmentOrdersOptIn")
    private Boolean fulfillmentOrdersOptIn;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("inventoryManagement")
    private Boolean inventoryManagement;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("permitsSkuSharing")
    private Boolean permitsSkuSharing;
    
    @JsonProperty("productBased")
    private Boolean productBased;
    
    @JsonProperty("serviceName")
    private String serviceName;
    
    @JsonProperty("type")
    private String type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrder {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("assignedLocation")
    private FulfillmentOrderAssignedLocation assignedLocation;
    
    @JsonProperty("deliveryMethod")
    private DeliveryMethod deliveryMethod;
    
    @JsonProperty("fulfillAt")
    private DateTime fulfillAt;
    
    @JsonProperty("fulfillmentHolds")
    private List<FulfillmentHold> fulfillmentHolds;
    
    @JsonProperty("internationalDuties")
    private InternationalDuties internationalDuties;
    
    @JsonProperty("lineItems")
    private FulfillmentOrderLineItemConnection lineItems;
    
    @JsonProperty("locationsForMove")
    private List<FulfillmentOrderLocationForMove> locationsForMove;
    
    @JsonProperty("merchantRequests")
    private FulfillmentOrderMerchantRequestConnection merchantRequests;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("requestStatus")
    private String requestStatus;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("supportedActions")
    private List<String> supportedActions;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderAssignedLocation {
    
    @JsonProperty("address1")
    private String address1;
    
    @JsonProperty("address2")
    private String address2;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("countryCode")
    private String countryCode;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("province")
    private String province;
    
    @JsonProperty("zip")
    private String zip;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryMethod {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("maxDeliveryDateTime")
    private DateTime maxDeliveryDateTime;
    
    @JsonProperty("methodType")
    private String methodType;
    
    @JsonProperty("minDeliveryDateTime")
    private DateTime minDeliveryDateTime;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentHold {
    
    @JsonProperty("reason")
    private String reason;
    
    @JsonProperty("reasonNotes")
    private String reasonNotes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class InternationalDuties {
    
    @JsonProperty("chargeType")
    private String chargeType;
    
    @JsonProperty("incoterm")
    private String incoterm;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderLineItemConnection {
    
    @JsonProperty("edges")
    private List<FulfillmentOrderLineItemEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderLineItemEdge {
    
    @JsonProperty("node")
    private FulfillmentOrderLineItem node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderLineItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("inventoryItemId")
    private ID inventoryItemId;
    
    @JsonProperty("lineItem")
    private LineItem lineItem;
    
    @JsonProperty("quantity")
    private Integer quantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderLocationForMove {
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("movable")
    private Boolean movable;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderMerchantRequestConnection {
    
    @JsonProperty("edges")
    private List<FulfillmentOrderMerchantRequestEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderMerchantRequestEdge {
    
    @JsonProperty("node")
    private FulfillmentOrderMerchantRequest node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderMerchantRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("kind")
    private String kind;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("requestOptions")
    private String requestOptions;
    
    @JsonProperty("responseData")
    private String responseData;
    
    @JsonProperty("sentAt")
    private DateTime sentAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentLineItemConnection {
    
    @JsonProperty("edges")
    private List<FulfillmentLineItemEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentLineItemEdge {
    
    @JsonProperty("node")
    private FulfillmentLineItem node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentLineItem {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("discountedTotalSet")
    private MoneyBag discountedTotalSet;
    
    @JsonProperty("lineItem")
    private LineItem lineItem;
    
    @JsonProperty("originalTotalSet")
    private MoneyBag originalTotalSet;
    
    @JsonProperty("quantity")
    private Integer quantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Product {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
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