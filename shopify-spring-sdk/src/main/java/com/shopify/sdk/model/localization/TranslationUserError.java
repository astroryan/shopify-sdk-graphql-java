package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an error that occurred during a translation operation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationUserError {
    /**
     * The error code.
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * The path to the input field that caused the error.
     */
    @JsonProperty("field")
    private String field;
    
    /**
     * The error message.
     */
    @JsonProperty("message")
    private String message;
}