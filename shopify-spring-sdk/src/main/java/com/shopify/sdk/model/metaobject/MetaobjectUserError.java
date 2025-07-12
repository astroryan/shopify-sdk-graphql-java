package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a user error from a metaobject operation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaobjectUserError {
    /**
     * The error code.
     */
    @JsonProperty("code")
    private MetaobjectUserErrorCode code;
    
    /**
     * The field that caused the error.
     */
    @JsonProperty("field")
    private List<String> field;
    
    /**
     * The error message.
     */
    @JsonProperty("message")
    private String message;
}