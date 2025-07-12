package com.shopify.sdk.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Decimal;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a shipping line for an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingLine {
    
    /**
     * The unique identifier of the shipping line.
     */
    @JsonProperty("id")
    private ID id;
    
    /**
     * The price of the shipping line.
     */
    @JsonProperty("price")
    private Decimal price;
    
    /**
     * The price of the shipping line in the shop's default currency.
     */
    @JsonProperty("price_set")
    private Map<String, Object> priceSet;
    
    /**
     * The title of the shipping method.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The code of the shipping method.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The carrier identifier for the shipping method.
     */
    @JsonProperty("carrier_identifier")
    private String carrierIdentifier;
    
    /**
     * The source of the shipping method.
     */
    @JsonProperty("source")
    private String source;
    
    /**
     * Whether the shipping line was discounted.
     */
    @JsonProperty("discounted_price")
    private Decimal discountedPrice;
    
    /**
     * The discounted price set of the shipping line.
     */
    @JsonProperty("discounted_price_set")
    private Map<String, Object> discountedPriceSet;
    
    /**
     * Tax lines for the shipping line.
     */
    @JsonProperty("tax_lines")
    private List<TaxLine> taxLines;
    
    /**
     * The requested fulfillment service for the shipping line.
     */
    @JsonProperty("requested_fulfillment_service_id")
    private String requestedFulfillmentServiceId;
    
    /**
     * The delivery category for the shipping line.
     */
    @JsonProperty("delivery_category")
    private String deliveryCategory;
    
    /**
     * Custom attributes associated with the shipping line.
     */
    @JsonProperty("custom_attributes")
    private List<Map<String, String>> customAttributes;
}