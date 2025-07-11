package com.shopify.sdk.model.marketplace.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Input for updating a catalog.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogUpdateInput {
    /**
     * The title of the catalog
     */
    private String title;
    
    /**
     * The publication ID for the catalog
     */
    private String publicationId;
    
    /**
     * The product IDs to add to the catalog
     */
    private List<String> addProductIds;
    
    /**
     * The product IDs to remove from the catalog
     */
    private List<String> removeProductIds;
    
    /**
     * The price list IDs to add to the catalog
     */
    private List<String> addPriceListIds;
    
    /**
     * The price list IDs to remove from the catalog
     */
    private List<String> removePriceListIds;
    
    /**
     * The market IDs to add to the catalog
     */
    private List<String> addMarketIds;
    
    /**
     * The market IDs to remove from the catalog
     */
    private List<String> removeMarketIds;
    
    /**
     * The company location IDs to add access for this catalog
     */
    private List<String> addCompanyLocationIds;
    
    /**
     * The company location IDs to remove access for this catalog
     */
    private List<String> removeCompanyLocationIds;
}