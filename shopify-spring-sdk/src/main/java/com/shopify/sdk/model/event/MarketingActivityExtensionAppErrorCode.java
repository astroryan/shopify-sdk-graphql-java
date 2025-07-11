package com.shopify.sdk.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum MarketingActivityExtensionAppErrorCode {
    NOT_ONBOARDED_ERROR,
    VALIDATION_ERROR,
    PLATFORM_ERROR,
    INSTALL_REQUIRED_ERROR
}
