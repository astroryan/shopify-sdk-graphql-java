package com.shopify.sdk.model.marketplace;

import com.shopify.sdk.model.common.Node;
import com.shopify.sdk.model.common.CompanyLocation;
import com.shopify.sdk.model.product.ProductConnection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a catalog.
 * A catalog is a collection of products that's available to a specific set of customers.
 * For example, you can create a catalog for B2B customers in a specific region.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Catalog implements Node {
    /**
     * A globally-unique identifier for the catalog
     */
    private String id;
    
    /**
     * The title of the catalog
     */
    private String title;
    
    /**
     * The status of the catalog
     */
    private CatalogStatus status;
    
    /**
     * The products in the catalog
     */
    private ProductConnection products;
    
    /**
     * The price lists associated with the catalog
     */
    private PriceListConnection priceLists;
    
    /**
     * The publication associated with the catalog
     */
    private Publication publication;
    
    /**
     * The markets associated with the catalog
     */
    private MarketConnection markets;
    
    /**
     * The company locations that have access to this catalog
     */
    private List<CompanyLocation> companyLocations;
}