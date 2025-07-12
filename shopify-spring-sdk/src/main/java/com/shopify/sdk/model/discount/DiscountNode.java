package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a discount in Shopify.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountNode implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the discount.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The status of the discount.
     */
    @JsonProperty("status")
    private DiscountStatus status;
    
    /**
     * The date and time when the discount starts.
     */
    @JsonProperty("startsAt")
    private OffsetDateTime startsAt;
    
    /**
     * The date and time when the discount ends.
     */
    @JsonProperty("endsAt")
    private OffsetDateTime endsAt;
    
    /**
     * The number of times the discount has been used.
     */
    @JsonProperty("usageCount")
    private Integer usageCount;
    
    /**
     * The discount details based on the type.
     */
    @JsonProperty("discount")
    private Discount discount;
}


/**
 * The base discount interface.
 */
interface Discount {
    // Marker interface for different discount types
}