package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents an external device used with point of sale systems.
 * External devices include card readers, receipt printers, etc.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDevice implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "name", description = "The name of the external device")
    private String name;
    
    @GraphQLQuery(name = "externalId", description = "The external ID of the device")
    private String externalId;
    
    @GraphQLQuery(name = "type", description = "The type of external device")
    private PosExternalDeviceType type;
    
    @GraphQLQuery(name = "vendor", description = "The vendor of the device")
    private String vendor;
    
    @GraphQLQuery(name = "model", description = "The model of the device")
    private String model;
    
    @GraphQLQuery(name = "serialNumber", description = "The serial number of the device")
    private String serialNumber;
    
    @GraphQLQuery(name = "active", description = "Whether the device is active")
    private Boolean active;
    
    @GraphQLQuery(name = "location", description = "The location where the device is used")
    private Device.Location location;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the device was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the device was last updated")
    private Instant updatedAt;
    
    /**
     * The type of POS external device
     */
    public enum PosExternalDeviceType {
        /**
         * Card reader device
         */
        CARD_READER,
        
        /**
         * Receipt printer
         */
        RECEIPT_PRINTER,
        
        /**
         * Barcode scanner
         */
        BARCODE_SCANNER,
        
        /**
         * Cash drawer
         */
        CASH_DRAWER,
        
        /**
         * Other device type
         */
        OTHER
    }
}