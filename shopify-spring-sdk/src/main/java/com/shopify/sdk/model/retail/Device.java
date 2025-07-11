package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.time.Instant;

/**
 * Represents a device used in retail operations.
 * Devices are used for point of sale operations and retail management.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "name", description = "The name of the device")
    private String name;
    
    @GraphQLQuery(name = "enabled", description = "Whether the device is enabled")
    private Boolean enabled;
    
    @GraphQLQuery(name = "type", description = "The type of the device")
    private DeviceType type;
    
    @GraphQLQuery(name = "location", description = "The location where the device is used")
    private Location location;
    
    @GraphQLQuery(name = "staffMember", description = "The staff member assigned to the device")
    private StaffMember staffMember;
    
    @GraphQLQuery(name = "createdAt", description = "The date and time when the device was created")
    private Instant createdAt;
    
    @GraphQLQuery(name = "updatedAt", description = "The date and time when the device was last updated")
    private Instant updatedAt;
    
    /**
     * The type of device
     */
    public enum DeviceType {
        /**
         * A point of sale device
         */
        POS,
        
        /**
         * A mobile device
         */
        MOBILE,
        
        /**
         * A web browser
         */
        WEB,
        
        /**
         * Other device type
         */
        OTHER
    }
    
    /**
     * Simplified location for retail
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        @GraphQLQuery(name = "id", description = "The ID of the location")
        private String id;
        
        @GraphQLQuery(name = "name", description = "The name of the location")
        private String name;
    }
    
    /**
     * Simplified staff member for retail
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StaffMember {
        @GraphQLQuery(name = "id", description = "The ID of the staff member")
        private String id;
        
        @GraphQLQuery(name = "displayName", description = "The display name of the staff member")
        private String displayName;
        
        @GraphQLQuery(name = "email", description = "The email of the staff member")
        private String email;
    }
}