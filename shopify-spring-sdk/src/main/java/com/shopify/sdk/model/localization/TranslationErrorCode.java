package com.shopify.sdk.model.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum TranslationErrorCode {
    BLANK,
    INVALID,
    RESOURCE_NOT_FOUND,
    TOO_MANY_KEYS_FOR_RESOURCE,
    TRANSLATION_ALREADY_EXISTS,
    TRANSLATION_KEY_INVALID,
    TRANSLATION_MUST_BE_DIFFERENT_FROM_DEFAULT,
    TRANSLATION_VALUE_INVALID,
    FAILS_GATEWAYS_VALIDATION,
    INVALID_KEY_FOR_MODEL,
    INVALID_LOCALE_FOR_SHOP,
    INVALID_TRANSLATABLE_CONTENT,
    RESOURCE_TYPE_NOT_TRANSLATABLE
}
