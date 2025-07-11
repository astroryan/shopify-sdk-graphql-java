package com.shopify.sdk.model.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.DateTime;
import com.shopify.sdk.model.common.ID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkOperation {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("status")
    private BulkOperationStatus status;
    
    @JsonProperty("errorCode")
    private BulkOperationErrorCode errorCode;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("completedAt")
    private DateTime completedAt;
    
    @JsonProperty("objectCount")
    private String objectCount;
    
    @JsonProperty("fileSize")
    private String fileSize;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("partialDataUrl")
    private String partialDataUrl;
    
    @JsonProperty("query")
    private String query;
    
    @JsonProperty("rootObjectCount")
    private String rootObjectCount;
    
    @JsonProperty("type")
    private BulkOperationType type;
}