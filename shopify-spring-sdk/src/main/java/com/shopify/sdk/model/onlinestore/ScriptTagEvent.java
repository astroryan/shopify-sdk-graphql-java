package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The event that triggers a script tag.
 */
public enum ScriptTagEvent {
    @JsonProperty("onload")
    ONLOAD
}