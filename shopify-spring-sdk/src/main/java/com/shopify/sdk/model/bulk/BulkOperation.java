package com.shopify.sdk.model.bulk;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

/**
 * Represents a Shopify bulk operation.
 */
@Data
public class BulkOperation {
    
    private String id;
    private String status;
    private String errorCode;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant completedAt;
    
    private Integer objectCount;
    private String fileSize;
    private String url;
    private String partialDataUrl;
    private String rootObjectCount;
    private String type;
    private String query;
    
    // Status constants
    public static final String STATUS_CREATED = "CREATED";
    public static final String STATUS_RUNNING = "RUNNING"; 
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELING = "CANCELING";
    public static final String STATUS_CANCELED = "CANCELED";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_ACCESS_DENIED = "ACCESS_DENIED";
    
    // Type constants
    public static final String TYPE_QUERY = "QUERY";
    public static final String TYPE_MUTATION = "MUTATION";
    
    /**
     * Check if the bulk operation is completed.
     */
    public boolean isCompleted() {
        return STATUS_COMPLETED.equals(status);
    }
    
    /**
     * Check if the bulk operation failed.
     */
    public boolean isFailed() {
        return STATUS_FAILED.equals(status) || STATUS_ACCESS_DENIED.equals(status);
    }
    
    /**
     * Check if the bulk operation is running.
     */
    public boolean isRunning() {
        return STATUS_RUNNING.equals(status);
    }
    
    /**
     * Check if the bulk operation was canceled.
     */
    public boolean isCanceled() {
        return STATUS_CANCELED.equals(status) || STATUS_CANCELING.equals(status);
    }
    
    /**
     * Check if results are available for download.
     */
    public boolean hasResults() {
        return isCompleted() && url != null && !url.isEmpty();
    }
    
    /**
     * Get the appropriate download URL (full results or partial).
     */
    public String getDownloadUrl() {
        if (url != null && !url.isEmpty()) {
            return url;
        }
        return partialDataUrl;
    }
}