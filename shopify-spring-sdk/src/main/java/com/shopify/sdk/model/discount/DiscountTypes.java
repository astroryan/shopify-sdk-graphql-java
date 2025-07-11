package com.shopify.sdk.model.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import com.shopify.sdk.model.product.Product;
import com.shopify.sdk.model.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAmount {
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("appliesOnEachItem")
    private Boolean appliesOnEachItem;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPercentage {
    
    @JsonProperty("percentage")
    private Double percentage;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountProducts {
    
    @JsonProperty("productVariants")
    private ProductVariantConnection productVariants;
    
    @JsonProperty("products")
    private ProductConnection products;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCollections {
    
    @JsonProperty("collections")
    private CollectionConnection collections;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllDiscountItems {
    
    @JsonProperty("allItems")
    private Boolean allItems;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountMinimumQuantity {
    
    @JsonProperty("greaterThanOrEqualToQuantity")
    private String greaterThanOrEqualToQuantity;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountMinimumSubtotal {
    
    @JsonProperty("greaterThanOrEqualToSubtotal")
    private MoneyV2 greaterThanOrEqualToSubtotal;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCustomers {
    
    @JsonProperty("customers")
    private List<Customer> customers;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCustomerSegments {
    
    @JsonProperty("segments")
    private List<Segment> segments;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCountrySelection {
    
    @JsonProperty("countries")
    private List<CountryCode> countries;
    
    @JsonProperty("includeRestOfWorld")
    private Boolean includeRestOfWorld;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Segment {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("query")
    private String query;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("handle")
    private String handle;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductConnection {
    
    @JsonProperty("edges")
    private List<ProductEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductEdge {
    
    @JsonProperty("node")
    private Product node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductVariantConnection {
    
    @JsonProperty("edges")
    private List<ProductVariantEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ProductVariantEdge {
    
    @JsonProperty("node")
    private ProductVariant node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CollectionConnection {
    
    @JsonProperty("edges")
    private List<CollectionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CollectionEdge {
    
    @JsonProperty("node")
    private Collection node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Metafield {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("namespace")
    private String namespace;
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
    
    @JsonProperty("type")
    private String type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldConnection {
    
    @JsonProperty("edges")
    private List<MetafieldEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MetafieldEdge {
    
    @JsonProperty("node")
    private Metafield node;
    
    @JsonProperty("cursor")
    private String cursor;
}