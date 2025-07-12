package com.shopify.sdk.model.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Address;
import com.shopify.sdk.model.market.Market;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a company location.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLocation implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the location.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The external ID of the location.
     */
    @JsonProperty("externalId")
    private String externalId;
    
    /**
     * The company this location belongs to.
     */
    @JsonProperty("company")
    private Company company;
    
    /**
     * The billing address of the location.
     */
    @JsonProperty("billingAddress")
    private Address billingAddress;
    
    /**
     * The shipping address of the location.
     */
    @JsonProperty("shippingAddress")
    private Address shippingAddress;
    
    /**
     * The note about the location.
     */
    @JsonProperty("note")
    private String note;
    
    /**
     * The locale of the location.
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * The market of the location.
     */
    @JsonProperty("market")
    private Market market;
    
    /**
     * The date and time when the location was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the location was last modified.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
}