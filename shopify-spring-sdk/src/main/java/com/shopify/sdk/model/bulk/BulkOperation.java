package com.shopify.sdk.model.bulk;

import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a bulk operation.
 * Bulk operations are long-running tasks that are processed asynchronously.
 * They can be used to import/export large amounts of data or perform other
 * time-consuming operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkOperation implements Node {
    /**
     * A globally-unique identifier for the bulk operation
     */
    private String id;
    
    /**
     * The status of the bulk operation
     */
    private BulkOperationStatus status;
    
    /**
     * The error code if the operation failed
     */
    private BulkOperationErrorCode errorCode;
    
    /**
     * When the bulk operation was created
     */
    private LocalDateTime createdAt;
    
    /**
     * When the bulk operation was completed
     */
    private LocalDateTime completedAt;
    
    /**
     * The number of objects processed
     */
    private Long objectCount;
    
    /**
     * The size of the result file in bytes
     */
    private Long fileSize;
    
    /**
     * The URL where the result file can be downloaded
     */
    private String url;
    
    /**
     * The URL where partial results can be downloaded (if available)
     */
    private String partialDataUrl;
    
    /**
     * The GraphQL query that was executed
     */
    private String query;
    
    /**
     * The number of root objects in the result
     */
    private Long rootObjectCount;
    
    /**
     * The type of bulk operation
     */
    private BulkOperationType type;
}