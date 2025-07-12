package com.shopify.sdk.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Represents an event in the shop.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Node {
    /**
     * The unique identifier for the event.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * The action performed by the event.
     */
    @JsonProperty("action")
    private String action;
    
    /**
     * The app title.
     */
    @JsonProperty("appTitle")
    private String appTitle;
    
    /**
     * The type of attributes for the event.
     */
    @JsonProperty("attributeToApp")
    private Boolean attributeToApp;
    
    /**
     * The type of attributes for the event.
     */
    @JsonProperty("attributeToUser")
    private Boolean attributeToUser;
    
    /**
     * When the event was created.
     */
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    
    /**
     * Whether the event is critical.
     */
    @JsonProperty("criticalAlert")
    private Boolean criticalAlert;
    
    /**
     * The message of the event.
     */
    @JsonProperty("message")
    private String message;
    
    /**
     * The subject ID of the event.
     */
    @JsonProperty("subjectId")
    private String subjectId;
    
    /**
     * The subject type of the event.
     */
    @JsonProperty("subjectType")
    private EventSubjectType subjectType;
}