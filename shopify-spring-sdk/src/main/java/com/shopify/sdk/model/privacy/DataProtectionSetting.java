package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents data protection settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProtectionSetting {
    /**
     * Whether data protection is enabled.
     */
    @JsonProperty("enabled")
    private Boolean enabled;
    
    /**
     * The data retention period.
     */
    @JsonProperty("dataRetentionPeriod")
    private DataRetentionPeriod dataRetentionPeriod;
    
    /**
     * The data processing opt-in level.
     */
    @JsonProperty("dataProcessingOptInLevel")
    private DataProcessingOptInLevel dataProcessingOptInLevel;
    
    /**
     * The data access types allowed.
     */
    @JsonProperty("dataAccessTypes")
    private String dataAccessTypes;
    
    /**
     * The data categories protected.
     */
    @JsonProperty("dataCategories")
    private String dataCategories;
}