package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a privacy policy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivacyPolicy implements Node {
    /**
     * The unique identifier for the privacy policy.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The title of the privacy policy.
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * The body of the privacy policy.
     */
    @JsonProperty("body")
    private String body;
    
    /**
     * The URL of the privacy policy.
     */
    @JsonProperty("url")
    private String url;
    
    /**
     * When the privacy policy was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the privacy policy was updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * When the privacy policy was published.
     */
    @JsonProperty("publishedAt")
    private OffsetDateTime publishedAt;
}