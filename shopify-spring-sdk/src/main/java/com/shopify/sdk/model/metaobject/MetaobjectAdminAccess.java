package com.shopify.sdk.model.metaobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public enum MetaobjectAdminAccess {
    NONE,
    READ,
    READ_WRITE
}
