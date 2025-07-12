package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input for updating data protection settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProtectionSettingInput {
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
}