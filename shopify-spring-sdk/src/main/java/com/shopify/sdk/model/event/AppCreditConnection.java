package com.shopify.sdk.model.event;

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
public class AppCreditConnection {
    
    @JsonProperty("edges")
    private List<AppCreditEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}
