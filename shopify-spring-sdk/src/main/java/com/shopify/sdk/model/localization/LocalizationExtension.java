package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Represents a localization extension
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizationExtension {
    private ID id;
    private String key;
    private LocalizationExtensionPurpose purpose;
    private List<LocalizationExtensionValue> values;
}

/**
 * Represents a localization extension value
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class LocalizationExtensionValue {
    private String locale;
    private String value;
}
