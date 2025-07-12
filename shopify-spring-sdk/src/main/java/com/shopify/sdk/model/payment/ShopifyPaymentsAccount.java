package com.shopify.sdk.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.MoneyV2;
import com.shopify.sdk.model.common.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a Shopify Payments account and its details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyPaymentsAccount implements Node {
    /**
     * A globally unique identifier.
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * Whether the Shopify Payments setup is completed.
     */
    @JsonProperty("activated")
    private Boolean activated;
    
    /**
     * The account's balance information.
     */
    @JsonProperty("balance")
    private ShopifyPaymentsBalance balance;
    
    /**
     * The bank accounts associated with the Shopify Payments account.
     */
    @JsonProperty("bankAccounts")
    private ShopifyPaymentsBankAccountConnection bankAccounts;
    
    /**
     * The charge statement descriptor.
     */
    @JsonProperty("chargeStatementDescriptor")
    private String chargeStatementDescriptor;
    
    /**
     * The charge statement descriptor details.
     */
    @JsonProperty("chargeStatementDescriptors")
    private ShopifyPaymentsChargeStatementDescriptor chargeStatementDescriptors;
    
    /**
     * The country of the Shopify Payments account.
     */
    @JsonProperty("country")
    private String country;
    
    /**
     * The default currency for payouts.
     */
    @JsonProperty("defaultCurrency")
    private String defaultCurrency;
    
    /**
     * The fraud settings.
     */
    @JsonProperty("fraudSettings")
    private ShopifyPaymentsFraudSettings fraudSettings;
    
    /**
     * The notification settings.
     */
    @JsonProperty("notificationSettings")
    private ShopifyPaymentsNotificationSettings notificationSettings;
    
    /**
     * Whether the account can be onboarded.
     */
    @JsonProperty("onboardable")
    private Boolean onboardable;
    
    /**
     * The payout schedule.
     */
    @JsonProperty("payoutSchedule")
    private ShopifyPaymentsPayoutSchedule payoutSchedule;
    
    /**
     * The payout statement descriptor.
     */
    @JsonProperty("payoutStatementDescriptor")
    private String payoutStatementDescriptor;
    
    /**
     * The payouts from the account.
     */
    @JsonProperty("payouts")
    private ShopifyPaymentsPayoutConnection payouts;
}

/**
 * The balance information for a Shopify Payments account.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsBalance {
    /**
     * The available balance.
     */
    @JsonProperty("available")
    private List<MoneyV2> available;
    
    /**
     * The balance on hold.
     */
    @JsonProperty("onHold")
    private List<MoneyV2> onHold;
    
    /**
     * The pending balance.
     */
    @JsonProperty("pending")
    private List<MoneyV2> pending;
}

/**
 * Charge statement descriptor settings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsChargeStatementDescriptor {
    /**
     * The default charge statement descriptor.
     */
    @JsonProperty("default")
    private String defaultDescriptor;
    
    /**
     * The charge statement descriptor prefix.
     */
    @JsonProperty("prefix")
    private String prefix;
}

/**
 * Fraud settings for Shopify Payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsFraudSettings {
    /**
     * Whether to decline charges when AVS verification fails.
     */
    @JsonProperty("declineChargeOnAvsFailure")
    private Boolean declineChargeOnAvsFailure;
    
    /**
     * Whether to decline charges when CVC verification fails.
     */
    @JsonProperty("declineChargeOnCvcFailure")
    private Boolean declineChargeOnCvcFailure;
}

/**
 * Notification settings for Shopify Payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsNotificationSettings {
    /**
     * Whether to receive payout notifications.
     */
    @JsonProperty("payouts")
    private Boolean payouts;
}

/**
 * The payout schedule for Shopify Payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutSchedule {
    /**
     * The interval between payouts.
     */
    @JsonProperty("interval")
    private ShopifyPaymentsPayoutInterval interval;
    
    /**
     * The monthly anchor date for payouts.
     */
    @JsonProperty("monthlyAnchor")
    private Integer monthlyAnchor;
    
    /**
     * The weekly anchor day for payouts.
     */
    @JsonProperty("weeklyAnchor")
    private ShopifyPaymentsPayoutWeeklyAnchor weeklyAnchor;
}


/**
 * A connection to bank accounts.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsBankAccountConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<ShopifyPaymentsBankAccountEdge> edges;
    
    /**
     * The nodes in the connection.
     */
    @JsonProperty("nodes")
    private List<ShopifyPaymentsBankAccount> nodes;
}

/**
 * An edge in a bank account connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsBankAccountEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The bank account node.
     */
    @JsonProperty("node")
    private ShopifyPaymentsBankAccount node;
}


/**
 * A connection to payouts.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutConnection {
    /**
     * The edges in the connection.
     */
    @JsonProperty("edges")
    private List<ShopifyPaymentsPayoutEdge> edges;
}

/**
 * An edge in a payout connection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ShopifyPaymentsPayoutEdge {
    /**
     * The cursor for pagination.
     */
    @JsonProperty("cursor")
    private String cursor;
    
    /**
     * The payout node.
     */
    @JsonProperty("node")
    private ShopifyPaymentsPayout node;
}