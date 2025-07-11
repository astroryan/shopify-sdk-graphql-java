package com.shopify.sdk.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserError {
    
    @JsonProperty("field")
    private List<String> field;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("code")
    private String code;
}