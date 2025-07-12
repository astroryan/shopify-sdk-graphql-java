package com.shopify.sdk.model.selling;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating or updating a selling plan group.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingPlanGroupInput {
    /**
     * The name of the selling plan group.
     */
    @JsonProperty("name")
    private String name;
    
    /**
     * The merchant code.
     */
    @JsonProperty("merchantCode")
    private String merchantCode;
    
    /**
     * The options for the selling plan group.
     */
    @JsonProperty("options")
    private List<String> options;
    
    /**
     * The selling plans to add.
     */
    @JsonProperty("sellingPlansToAdd")
    private List<SellingPlanInput> sellingPlansToAdd;
    
    /**
     * The selling plans to update.
     */
    @JsonProperty("sellingPlansToUpdate")
    private List<SellingPlanInput> sellingPlansToUpdate;
    
    /**
     * The selling plan IDs to delete.
     */
    @JsonProperty("sellingPlansToDelete")
    private List<String> sellingPlansToDelete;
}