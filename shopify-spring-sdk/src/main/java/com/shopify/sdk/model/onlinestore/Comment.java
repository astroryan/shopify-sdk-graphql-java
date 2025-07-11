package com.shopify.sdk.model.onlinestore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Represents a comment in the online store
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private ID id;
    private String author;
    private String content;
    private DateTime createdAt;
    private DateTime updatedAt;
    private CommentPolicy policy;
}
