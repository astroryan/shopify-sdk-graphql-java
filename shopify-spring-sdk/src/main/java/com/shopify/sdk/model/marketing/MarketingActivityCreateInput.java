package com.shopify.sdk.model.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Input for creating a marketing activity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingActivityCreateInput {
    /**
     * The title of the marketing activity.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The form data for the marketing activity.
     */
    @JsonProperty("formData")
    private String formData;
    
    /**
     * The marketing activity extension.
     */
    @JsonProperty("marketingActivityExtensionId")
    private String marketingActivityExtensionId;
    
    /**
     * The context of the marketing activity.
     */
    @JsonProperty("context")
    private String context;
    
    /**
     * The scheduled start time.
     */
    @JsonProperty("scheduledStartAt")
    private OffsetDateTime scheduledStartAt;
    
    /**
     * The scheduled end time.
     */
    @JsonProperty("scheduledEndAt")
    private OffsetDateTime scheduledEndAt;
}