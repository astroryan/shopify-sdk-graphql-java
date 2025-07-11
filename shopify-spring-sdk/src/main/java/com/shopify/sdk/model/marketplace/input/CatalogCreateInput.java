package com.shopify.sdk.model.marketplace.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for creating a catalog.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogCreateInput {
    /**
     * The title of the catalog
     */
    private String title;
    
    /**
     * The publication ID for the catalog
     */
    private String publicationId;
    
    /**
     * The product IDs to include in the catalog
     */
    private List<String> productIds;
    
    /**
     * The price list IDs to associate with the catalog
     */
    private List<String> priceListIds;
    
    /**
     * The market IDs to associate with the catalog
     */
    private List<String> marketIds;
    
    /**
     * The company location IDs that can access this catalog
     */
    private List<String> companyLocationIds;
}