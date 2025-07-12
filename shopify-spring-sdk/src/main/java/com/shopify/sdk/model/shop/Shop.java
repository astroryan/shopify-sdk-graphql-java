package com.shopify.sdk.model.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Represents a shop on Shopify.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The name of the shop.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The description of the shop.
     */
    @JsonProperty("description")
    private String description;
    
    /**
     * The shop's primary email address.
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The shop's contact email.
     */
    @JsonProperty("contactEmail")
    private String contactEmail;
    
    /**
     * The shop's customer email.
     */
    @JsonProperty("customerEmail")
    private String customerEmail;
    
    /**
     * The primary domain of the shop.
     */
    @JsonProperty("primaryDomain")
    private Domain primaryDomain;
    
    /**
     * The shop's Shopify domain.
     */
    @JsonProperty("myshopifyDomain")
    private String myshopifyDomain;
    
    /**
     * The shop's billing address.
     */
    @JsonProperty("billingAddress")
    private Address billingAddress;
    
    /**
     * The shop's currency code.
     */
    @JsonProperty("currencyCode")
    private String currencyCode;
    
    /**
     * The shop's enabled presentment currencies.
     */
    @JsonProperty("enabledPresentmentCurrencies")
    private List<String> enabledPresentmentCurrencies;
    
    /**
     * The shop's weight unit.
     */
    @JsonProperty("weightUnit")
    private String weightUnit;
    
    /**
     * The shop's unit system.
     */
    @JsonProperty("unitSystem")
    private String unitSystem;
    
    /**
     * The shop's timezone.
     */
    @JsonProperty("ianaTimezone")
    private String ianaTimezone;
    
    /**
     * The shop's plan name.
     */
    @JsonProperty("plan")
    private ShopPlan plan;
    
    /**
     * The shop's features.
     */
    @JsonProperty("features")
    private ShopFeatures features;
    
    /**
     * The shop's resource limits.
     */
    @JsonProperty("resourceLimits")
    private ShopResourceLimits resourceLimits;
    
    /**
     * The date and time when the shop was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * The date and time when the shop was last updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * Whether the shop has storefront.
     */
    @JsonProperty("hasStorefront")
    private Boolean hasStorefront;
    
    /**
     * Whether checkout is complete.
     */
    @JsonProperty("checkoutApiSupported")
    private Boolean checkoutApiSupported;
    
    /**
     * Whether taxes are included in product prices.
     */
    @JsonProperty("taxesIncluded")
    private Boolean taxesIncluded;
    
    /**
     * Whether the shop charges taxes.
     */
    @JsonProperty("taxShipping")
    private Boolean taxShipping;
    
    /**
     * The shop owner's name.
     */
    @JsonProperty("shopOwnerName")
    private String shopOwnerName;
}