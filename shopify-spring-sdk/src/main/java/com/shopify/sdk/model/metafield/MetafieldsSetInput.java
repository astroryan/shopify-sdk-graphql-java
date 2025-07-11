package com.shopify.sdk.model.metafield;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetafieldsSetInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("ownerId")
    private String ownerId;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("value")
    private String value;
}