package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.order.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrder {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("assignedLocation")
    private FulfillmentOrderAssignedLocation assignedLocation;
    
    @JsonProperty("channelId")
    private String channelId;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("deliveryMethod")
    private DeliveryMethod deliveryMethod;
    
    @JsonProperty("destination")
    private FulfillmentOrderDestination destination;
    
    @JsonProperty("fulfillAt")
    private DateTime fulfillAt;
    
    @JsonProperty("fulfillBy")
    private DateTime fulfillBy;
    
    @JsonProperty("fulfillmentHolds")
    private List<FulfillmentHold> fulfillmentHolds;
    
    @JsonProperty("fulfillmentOrdersForMerge")
    private List<FulfillmentOrder> fulfillmentOrdersForMerge;
    
    @JsonProperty("fulfillments")
    private FulfillmentConnection fulfillments;
    
    @JsonProperty("internationalDuties")
    private FulfillmentOrderInternationalDuties internationalDuties;
    
    @JsonProperty("lineItems")
    private FulfillmentOrderLineItemConnection lineItems;
    
    @JsonProperty("locationsForMove")
    private List<FulfillmentOrderLocationForMove> locationsForMove;
    
    @JsonProperty("locationsForSplit")
    private List<FulfillmentOrderLocationForSplit> locationsForSplit;
    
    @JsonProperty("merchantRequests")
    private FulfillmentOrderMerchantRequestConnection merchantRequests;
    
    @JsonProperty("order")
    private Order order;
    
    @JsonProperty("requestStatus")
    private FulfillmentOrderRequestStatus requestStatus;
    
    @JsonProperty("status")
    private FulfillmentOrderStatus status;
    
    @JsonProperty("supportedActions")
    private List<FulfillmentOrderSupportedAction> supportedActions;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
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
    private CountryCode countryCode;
    
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
class FulfillmentOrderDestination {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("address1")
    private String address1;
    
    @JsonProperty("address2")
    private String address2;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("company")
    private String company;
    
    @JsonProperty("countryCode")
    private CountryCode countryCode;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
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
class FulfillmentOrderInternationalDuties {
    
    @JsonProperty("chargeType")
    private String chargeType;
    
    @JsonProperty("incoterm")
    private String incoterm;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderLocationForSplit {
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("movable")
    private Boolean movable;
}

public enum FulfillmentOrderRequestStatus {
    ACCEPTED,
    CANCELLATION_ACCEPTED,
    CANCELLATION_REJECTED,
    CANCELLATION_SUBMITTED,
    CLOSED,
    NOT_SUBMITTED,
    REJECTED,
    SUBMITTED
}

public enum FulfillmentOrderStatus {
    CANCELLED,
    CLOSED,
    ERROR,
    FULFILLED,
    IN_PROGRESS,
    ON_HOLD,
    OPEN,
    PARTIALLY_FULFILLED,
    PENDING_FULFILLMENT,
    SCHEDULED
}

public enum FulfillmentOrderSupportedAction {
    CANCEL_FULFILLMENT_ORDER,
    CREATE_FULFILLMENT,
    EXTERNAL_REQUEST,
    FULFILLMENT_ORDER_CANCEL,
    FULFILLMENT_ORDER_CLOSE,
    FULFILLMENT_ORDER_HOLD,
    FULFILLMENT_ORDER_MERGE,
    FULFILLMENT_ORDER_MOVE,
    FULFILLMENT_ORDER_OPEN,
    FULFILLMENT_ORDER_RELEASE_HOLD,
    FULFILLMENT_ORDER_RESCHEDULE,
    FULFILLMENT_ORDER_SPLIT,
    FULFILLMENT_ORDER_SUBMIT_CANCELLATION_REQUEST,
    FULFILLMENT_ORDER_SUBMIT_FULFILLMENT_REQUEST,
    MARK_AS_OPEN,
    REQUEST_CANCELLATION,
    REQUEST_FULFILLMENT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentConnection {
    
    @JsonProperty("edges")
    private List<FulfillmentEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentEdge {
    
    @JsonProperty("node")
    private Fulfillment node;
    
    @JsonProperty("cursor")
    private String cursor;
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
class DeliveryMethod {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("methodType")
    private DeliveryMethodType methodType;
}

public enum DeliveryMethodType {
    LOCAL,
    NONE,
    PICK_UP,
    RETAIL,
    SHIPPING
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderConnection {
    
    @JsonProperty("edges")
    private List<FulfillmentOrderEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderEdge {
    
    @JsonProperty("node")
    private FulfillmentOrder node;
    
    @JsonProperty("cursor")
    private String cursor;
}