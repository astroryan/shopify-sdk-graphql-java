package com.shopify.sdk.model.draftorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DraftOrder {
    
    private String id;
    private String note;
    private String email;
    
    @JsonProperty("taxes_included")
    private Boolean taxesIncluded;
    
    private String currency;
    
    @JsonProperty("invoice_sent_at")
    private Instant invoiceSentAt;
    
    @JsonProperty("created_at")
    private Instant createdAt;
    
    @JsonProperty("updated_at")
    private Instant updatedAt;
    
    @JsonProperty("tax_exempt")
    private Boolean taxExempt;
    
    @JsonProperty("completed_at")
    private Instant completedAt;
    
    private String name;
    private String status;
    
    @JsonProperty("line_items")
    private List<DraftOrderLineItem> lineItems;
    
    @JsonProperty("shipping_address")
    private Address shippingAddress;
    
    @JsonProperty("billing_address")
    private Address billingAddress;
    
    @JsonProperty("invoice_url")
    private String invoiceUrl;
    
    @JsonProperty("applied_discount")
    private AppliedDiscount appliedDiscount;
    
    @JsonProperty("order_id")
    private String orderId;
    
    @JsonProperty("shipping_line")
    private ShippingLine shippingLine;
    
    @JsonProperty("tax_lines")
    private List<TaxLine> taxLines;
    
    private String tags;
    
    @JsonProperty("note_attributes")
    private List<NoteAttribute> noteAttributes;
    
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    
    @JsonProperty("subtotal_price")
    private BigDecimal subtotalPrice;
    
    @JsonProperty("total_tax")
    private BigDecimal totalTax;
    
    @JsonProperty("payment_terms")
    private PaymentTerms paymentTerms;
    
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("use_customer_default_address")
    private Boolean useCustomerDefaultAddress;
    
    public boolean isCompleted() {
        return completedAt != null;
    }
    
    public boolean isOpen() {
        return "open".equals(status);
    }
    
    public boolean isInvoiceSent() {
        return invoiceSentAt != null;
    }
    
    public boolean isTaxExempt() {
        return taxExempt != null && taxExempt;
    }
    
    public boolean taxesIncluded() {
        return taxesIncluded != null && taxesIncluded;
    }
    
    public int getLineItemCount() {
        return lineItems != null ? lineItems.size() : 0;
    }
    
    public boolean hasCustomer() {
        return customer != null || email != null;
    }
    
    public boolean hasShippingAddress() {
        return shippingAddress != null;
    }
    
    public boolean hasBillingAddress() {
        return billingAddress != null;
    }
    
    public boolean hasDiscount() {
        return appliedDiscount != null;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DraftOrderLineItem {
        private String id;
        
        @JsonProperty("variant_id")
        private String variantId;
        
        @JsonProperty("product_id")
        private String productId;
        
        private String title;
        
        @JsonProperty("variant_title")
        private String variantTitle;
        
        private String sku;
        private String vendor;
        private Integer quantity;
        
        @JsonProperty("requires_shipping")
        private Boolean requiresShipping;
        
        private Boolean taxable;
        
        @JsonProperty("gift_card")
        private Boolean giftCard;
        
        @JsonProperty("fulfillment_service")
        private String fulfillmentService;
        
        private Integer grams;
        
        @JsonProperty("tax_lines")
        private List<TaxLine> taxLines;
        
        @JsonProperty("applied_discount")
        private AppliedDiscount appliedDiscount;
        
        private String name;
        
        @JsonProperty("custom")
        private Boolean custom;
        
        private BigDecimal price;
        
        @JsonProperty("admin_graphql_api_id")
        private String adminGraphqlApiId;
        
        @JsonProperty("properties")
        private List<LineItemProperty> properties;
        
        public boolean isGiftCard() {
            return giftCard != null && giftCard;
        }
        
        public boolean requiresShipping() {
            return requiresShipping == null || requiresShipping;
        }
        
        public boolean isTaxable() {
            return taxable == null || taxable;
        }
        
        public boolean isCustom() {
            return custom != null && custom;
        }
        
        public BigDecimal getTotalPrice() {
            if (price == null || quantity == null) {
                return BigDecimal.ZERO;
            }
            return price.multiply(BigDecimal.valueOf(quantity));
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        @JsonProperty("first_name")
        private String firstName;
        
        @JsonProperty("last_name")
        private String lastName;
        
        private String company;
        private String address1;
        private String address2;
        private String city;
        private String province;
        private String country;
        private String zip;
        private String phone;
        
        @JsonProperty("province_code")
        private String provinceCode;
        
        @JsonProperty("country_code")
        private String countryCode;
        
        public String getFullName() {
            StringBuilder name = new StringBuilder();
            if (firstName != null) {
                name.append(firstName);
            }
            if (lastName != null) {
                if (name.length() > 0) name.append(" ");
                name.append(lastName);
            }
            return name.toString();
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AppliedDiscount {
        private String title;
        private String description;
        private BigDecimal value;
        
        @JsonProperty("value_type")
        private String valueType;
        
        private BigDecimal amount;
        
        public boolean isPercentage() {
            return "percentage".equals(valueType);
        }
        
        public boolean isFixedAmount() {
            return "fixed_amount".equals(valueType);
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShippingLine {
        private String title;
        private BigDecimal price;
        private String code;
        private String source;
        private String phone;
        
        @JsonProperty("delivery_category")
        private String deliveryCategory;
        
        @JsonProperty("carrier_identifier")
        private String carrierIdentifier;
        
        @JsonProperty("requested_fulfillment_service_id")
        private String requestedFulfillmentServiceId;
        
        @JsonProperty("tax_lines")
        private List<TaxLine> taxLines;
        
        @JsonProperty("custom")
        private Boolean custom;
        
        public boolean isCustom() {
            return custom != null && custom;
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TaxLine {
        private BigDecimal price;
        private Double rate;
        private String title;
        
        @JsonProperty("channel_liable")
        private Boolean channelLiable;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NoteAttribute {
        private String name;
        private String value;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentTerms {
        private Integer amount;
        private String currency;
        
        @JsonProperty("payment_terms_name")
        private String paymentTermsName;
        
        @JsonProperty("payment_terms_type")
        private String paymentTermsType;
        
        @JsonProperty("due_in_days")
        private Integer dueInDays;
        
        @JsonProperty("payment_schedules")
        private List<PaymentSchedule> paymentSchedules;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentSchedule {
        private Integer amount;
        private String currency;
        
        @JsonProperty("issued_at")
        private Instant issuedAt;
        
        @JsonProperty("due_at")
        private Instant dueAt;
        
        @JsonProperty("completed_at")
        private Instant completedAt;
        
        @JsonProperty("expected_payment_method")
        private String expectedPaymentMethod;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Customer {
        private String id;
        private String email;
        
        @JsonProperty("accepts_marketing")
        private Boolean acceptsMarketing;
        
        @JsonProperty("created_at")
        private Instant createdAt;
        
        @JsonProperty("updated_at")
        private Instant updatedAt;
        
        @JsonProperty("first_name")
        private String firstName;
        
        @JsonProperty("last_name")
        private String lastName;
        
        private String phone;
        private String state;
        private String note;
        
        @JsonProperty("verified_email")
        private Boolean verifiedEmail;
        
        @JsonProperty("multipass_identifier")
        private String multipassIdentifier;
        
        @JsonProperty("tax_exempt")
        private Boolean taxExempt;
        
        private String tags;
        
        @JsonProperty("currency")
        private String currency;
        
        @JsonProperty("tax_exemptions")
        private List<String> taxExemptions;
        
        @JsonProperty("admin_graphql_api_id")
        private String adminGraphqlApiId;
        
        @JsonProperty("default_address")
        private Address defaultAddress;
        
        public String getFullName() {
            StringBuilder name = new StringBuilder();
            if (firstName != null) {
                name.append(firstName);
            }
            if (lastName != null) {
                if (name.length() > 0) name.append(" ");
                name.append(lastName);
            }
            return name.toString();
        }
        
        public boolean acceptsMarketing() {
            return acceptsMarketing != null && acceptsMarketing;
        }
        
        public boolean isTaxExempt() {
            return taxExempt != null && taxExempt;
        }
        
        public boolean hasVerifiedEmail() {
            return verifiedEmail != null && verifiedEmail;
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LineItemProperty {
        private String name;
        private String value;
    }
}