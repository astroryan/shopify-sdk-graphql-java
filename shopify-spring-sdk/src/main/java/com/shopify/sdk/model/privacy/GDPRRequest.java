package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents a GDPR request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GDPRRequest implements Node {
    /**
     * The unique identifier for the GDPR request.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The customer ID associated with the request.
     */
    @JsonProperty("customerId")
    private String customerId;
    
    /**
     * The email associated with the request.
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * The type of GDPR request.
     */
    @JsonProperty("requestType")
    private GDPRRequestType requestType;
    
    /**
     * The state of the GDPR request.
     */
    @JsonProperty("state")
    private GDPRRequestState state;
    
    /**
     * The data categories requested.
     */
    @JsonProperty("dataCategories")
    private String dataCategories;
    
    /**
     * When the request was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * When the request was updated.
     */
    @JsonProperty("updatedAt")
    private OffsetDateTime updatedAt;
    
    /**
     * When the request was processed.
     */
    @JsonProperty("processedAt")
    private OffsetDateTime processedAt;
}