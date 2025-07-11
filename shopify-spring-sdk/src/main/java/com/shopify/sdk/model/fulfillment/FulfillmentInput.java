package com.shopify.sdk.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentV2Input {
    
    @JsonProperty("lineItemsByFulfillmentOrder")
    private List<FulfillmentOrderLineItemsInput> lineItemsByFulfillmentOrder;
    
    @JsonProperty("notifyCustomer")
    private Boolean notifyCustomer;
    
    @JsonProperty("originAddress")
    private FulfillmentOriginAddressInput originAddress;
    
    @JsonProperty("trackingInfo")
    private FulfillmentTrackingInput trackingInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderLineItemsInput {
    
    @JsonProperty("fulfillmentOrderId")
    private String fulfillmentOrderId;
    
    @JsonProperty("fulfillmentOrderLineItems")
    private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderLineItemInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("quantity")
    private Integer quantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FulfillmentOriginAddressInput {
    
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
class FulfillmentTrackingInput {
    
    @JsonProperty("company")
    private String company;
    
    @JsonProperty("numbers")
    private List<String> numbers;
    
    @JsonProperty("urls")
    private List<String> urls;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderHoldInput {
    
    @JsonProperty("fulfillmentOrderId")
    private String fulfillmentOrderId;
    
    @JsonProperty("fulfillmentOrderLineItems")
    private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
    
    @JsonProperty("reason")
    private FulfillmentHoldReason reason;
    
    @JsonProperty("reasonNotes")
    private String reasonNotes;
}

public enum FulfillmentHoldReason {
    AWAITING_PAYMENT,
    HIGH_RISK_OF_FRAUD,
    INCORRECT_ADDRESS,
    INVENTORY_OUT_OF_STOCK,
    OTHER,
    REGULATORY_CLEARANCE,
    UNFULFILLABLE_PRODUCT
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderSplitInput {
    
    @JsonProperty("fulfillmentOrderId")
    private String fulfillmentOrderId;
    
    @JsonProperty("fulfillmentOrderLineItems")
    private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderSubmitCancellationRequestInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("message")
    private String message;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderAcceptCancellationRequestInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("message")
    private String message;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderRejectCancellationRequestInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("message")
    private String message;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderSubmitFulfillmentRequestInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("notifyCustomer")
    private Boolean notifyCustomer;
    
    @JsonProperty("fulfillmentOrderLineItems")
    private List<FulfillmentOrderLineItemInput> fulfillmentOrderLineItems;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderAcceptFulfillmentRequestInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("message")
    private String message;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentOrderRejectFulfillmentRequestInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("reason")
    private FulfillmentOrderMerchantRequestKind reason;
}

public enum FulfillmentOrderMerchantRequestKind {
    FULFILLMENT_REQUEST,
    CANCELLATION_REQUEST
}