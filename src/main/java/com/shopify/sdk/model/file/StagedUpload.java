package com.shopify.sdk.model.file;

import lombok.Data;

import java.util.List;

/**
 * Represents a staged upload target for file uploads to Shopify.
 */
@Data
public class StagedUpload {
    
    private String url;
    private List<Parameter> parameters;
    private String resourceUrl;
    
    /**
     * Parameter for staged upload.
     */
    @Data
    public static class Parameter {
        private String name;
        private String value;
    }
    
    /**
     * Gets the parameter value by name.
     */
    public String getParameterValue(String name) {
        if (parameters == null) {
            return null;
        }
        
        return parameters.stream()
            .filter(param -> name.equals(param.getName()))
            .map(Parameter::getValue)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Checks if the staged upload has all required parameters.
     */
    public boolean hasRequiredParameters() {
        return url != null && !url.isEmpty() && 
               parameters != null && !parameters.isEmpty();
    }
}