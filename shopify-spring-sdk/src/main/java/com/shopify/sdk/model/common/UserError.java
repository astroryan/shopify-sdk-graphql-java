package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents an error that occurred during a user operation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserError {
    /**
     * The path to the input field that caused the error
     */
    @JsonProperty("field")
    private List<String> field;
    
    /**
     * The error message
     */
    @JsonProperty("message")
    private String message;
    
    /**
     * The error code
     */
    @JsonProperty("code")
    private String code;
}