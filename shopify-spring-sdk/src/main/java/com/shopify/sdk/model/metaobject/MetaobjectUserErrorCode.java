package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum MetaobjectUserErrorCode {
    INVALID,
    TAKEN,
    TOO_MANY_ARGUMENTS,
    INVALID_TYPE,
    APP_NOT_AUTHORIZED,
    INVALID_CAPABILITY_SETUP,
    INVALID_FIELD_CONFIGURATION,
    RESERVED_KEYWORDS_USED,
    MAX_DEFINITIONS_EXCEEDED,
    INVALID_INPUT,
    INCLUSION,
    REQUIRED,
    TOO_LONG,
    BLANK,
    OPERATION_IN_PROGRESS,
    FORBIDDEN,
    CANNOT_CHANGE_FIELD_TYPE
}
