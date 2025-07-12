package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.ID;
import com.shopify.sdk.graphql.scalar.MoneyScalar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a shipping line for an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingLine {
    
    /**
     * A globally-unique identifier.
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * Returns the carrier identifier for this shipping line.
     */
    @JsonProperty("carrierIdentifier")
    private String carrierIdentifier;
    
    /**
     * A reference to the shipping method.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * Whether the shipping line is custom or not.
     */
    @JsonProperty("custom")
    private Boolean custom;
    
    /**
     * The delivery category of the shipping line.
     */
    @JsonProperty("deliveryCategory")
    private String deliveryCategory;
    
    /**
     * The discounted price of the shipping line.
     */
    @JsonProperty("discountedPrice")
    private MoneyScalar discountedPrice;
    
    /**
     * The pre-tax shipping price.
     */
    @JsonProperty("originalPrice")
    private MoneyScalar originalPrice;
    
    /**
     * The phone number at the shipping address.
     */
    @JsonProperty("phone")
    private String phone;
    
    /**
     * Returns the requested fulfillment service for this shipping line.
     */
    @JsonProperty("requestedFulfillmentService")
    private FulfillmentService requestedFulfillmentService;
    
    /**
     * A unique identifier for the shipping rate.
     */
    @JsonProperty("shippingRateHandle")
    private String shippingRateHandle;
    
    /**
     * Returns the source of the shipping method.
     */
    @JsonProperty("source")
    private String source;
    
    /**
     * The title of the shipping line.
     */
    @JsonProperty("title")
    private String title;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentService {
        
        @JsonProperty("id")
        private ID id;
        
        @JsonProperty("serviceName")
        private String serviceName;
    }
}