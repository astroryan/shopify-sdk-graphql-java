package com.shopify.sdk.model.fulfillment;

public enum FulfillmentStatus {
    PENDING("pending"),
    OPEN("open"),
    SUCCESS("success"),
    CANCELLED("cancelled"),
    ERROR("error"),
    FAILURE("failure");
    
    private final String value;
    
    FulfillmentStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static FulfillmentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        
        for (FulfillmentStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        
        throw new IllegalArgumentException("Unknown fulfillment status: " + value);
    }
    
    public boolean isCompleted() {
        return this == SUCCESS;
    }
    
    public boolean isFailed() {
        return this == CANCELLED || this == ERROR || this == FAILURE;
    }
    
    public boolean isPending() {
        return this == PENDING || this == OPEN;
    }
}