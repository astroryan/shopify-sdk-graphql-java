package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of device credentials.
 * This connection type is used for paginated queries of device credentials in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialConnection {
    /**
     * A list of edges containing device credential nodes and cursors
     */
    private List<DeviceCredentialEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}