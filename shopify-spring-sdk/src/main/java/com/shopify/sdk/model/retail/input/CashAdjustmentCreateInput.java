package com.shopify.sdk.model.retail.input;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

import java.math.BigDecimal;

/**
 * Input for creating a cash adjustment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAdjustmentCreateInput {
    
    @GraphQLQuery(name = "cashTrackingSessionId", description = "The ID of the cash tracking session")
    private String cashTrackingSessionId;
    
    @GraphQLQuery(name = "amount", description = "The amount of the adjustment")
    private BigDecimal amount;
    
    @GraphQLQuery(name = "currency", description = "The currency for the adjustment")
    private String currency;
    
    @GraphQLQuery(name = "type", description = "The type of adjustment")
    private String type;
    
    @GraphQLQuery(name = "reason", description = "The reason for the adjustment")
    private String reason;
    
    @GraphQLQuery(name = "note", description = "Additional notes about the adjustment")
    private String note;
}