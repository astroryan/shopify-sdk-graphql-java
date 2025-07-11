package com.shopify.sdk.model.marketplace;

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
public class Publishable {
    
    @JsonProperty("publicationCount")
    private Integer publicationCount;
    
    @JsonProperty("publishedOnCurrentPublication")
    private Boolean publishedOnCurrentPublication;
    
    @JsonProperty("unpublishedPublications")
    private PublicationConnection unpublishedPublications;
    
    @JsonProperty("publications")
    private PublicationConnection publications;
}
