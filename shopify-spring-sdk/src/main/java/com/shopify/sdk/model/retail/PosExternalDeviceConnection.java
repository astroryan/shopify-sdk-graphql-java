package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a connection to a list of POS external devices.
 * This connection type is used for paginated queries of POS external devices in the Shopify Admin API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDeviceConnection {
    /**
     * A list of edges containing POS external device nodes and cursors
     */
    private List<PosExternalDeviceEdge> edges;
    
    /**
     * Information about pagination in the connection
     */
    private PageInfo pageInfo;
}