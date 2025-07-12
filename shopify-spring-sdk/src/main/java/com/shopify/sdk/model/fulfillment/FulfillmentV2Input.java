package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a fulfillment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentV2Input {
    /**
     * The fulfillment's tracking information.
     */
    @JsonProperty("trackingInfo")
    private FulfillmentTrackingInput trackingInfo;
    
    /**
     * Whether the customer is notified.
     */
    @JsonProperty("notifyCustomer")
    private Boolean notifyCustomer;
    
    /**
     * The line items to be fulfilled.
     */
    @JsonProperty("lineItemsByFulfillmentOrder")
    private List<FulfillmentOrderLineItemsInput> lineItemsByFulfillmentOrder;
    
    /**
     * The ID of the location from which the order will be fulfilled.
     */
    @JsonProperty("originAddress")
    private FulfillmentOriginAddressInput originAddress;
}

/**
 * Tracking information for a fulfillment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentTrackingInput {
    /**
     * The name of the tracking company.
     */
    @JsonProperty("company")
    private String company;
    
    /**
     * The tracking numbers for the fulfillment.
     */
    @JsonProperty("numbers")
    private List<String> numbers;
    
    /**
     * The URLs for tracking the fulfillment.
     */
    @JsonProperty("urls")
    private List<String> urls;
}

/**
 * Input for fulfillment order line items.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOrderLineItemsInput {
    /**
     * The ID of the fulfillment order.
     */
    @JsonProperty("fulfillmentOrderId")
    private String fulfillmentOrderId;
    
    /**
     * The fulfillment order line items to be fulfilled.
     */
    @JsonProperty("fulfillmentOrderLineItems")
    private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
}

/**
 * Input for the origin address of a fulfillment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOriginAddressInput {
    /**
     * The first line of the address.
     */
    @JsonProperty("address1")
    private String address1;
    
    /**
     * The second line of the address.
     */
    @JsonProperty("address2")
    private String address2;
    
    /**
     * The city of the address.
     */
    @JsonProperty("city")
    private String city;
    
    /**
     * The country code of the address.
     */
    @JsonProperty("countryCode")
    private String countryCode;
    
    /**
     * The province code of the address.
     */
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    /**
     * The zip or postal code of the address.
     */
    @JsonProperty("zip")
    private String zip;
}