package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProtectionSettingInput {
    
    @JsonProperty("dataRetentionPeriod")
    private DataRetentionPeriod dataRetentionPeriod;
    
    @JsonProperty("dataRetentionPeriodDays")
    private Integer dataRetentionPeriodDays;
    
    @JsonProperty("requestProcessingDays")
    private Integer requestProcessingDays;
    
    @JsonProperty("rightToBeInformed")
    private Boolean rightToBeInformed;
    
    @JsonProperty("rightOfAccess")
    private Boolean rightOfAccess;
    
    @JsonProperty("rightToRectification")
    private Boolean rightToRectification;
    
    @JsonProperty("rightToErasure")
    private Boolean rightToErasure;
    
    @JsonProperty("rightToRestriction")
    private Boolean rightToRestriction;
    
    @JsonProperty("rightToPortability")
    private Boolean rightToPortability;
    
    @JsonProperty("rightToObject")
    private Boolean rightToObject;
    
    @JsonProperty("automatedDecisionMaking")
    private Boolean automatedDecisionMaking;
}