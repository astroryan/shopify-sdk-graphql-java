package com.shopify.sdk.model.privacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopify.sdk.model.common.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPrivacy {
    
    @JsonProperty("messageEncrypted")
    private String messageEncrypted;
    
    @JsonProperty("translatableContent")
    private String translatableContent;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("dataAccessType")
    private DataAccessType dataAccessType;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("failedAt")
    private DateTime failedAt;
    
    @JsonProperty("failureReason")
    private String failureReason;
    
    @JsonProperty("requestedAt")
    private DateTime requestedAt;
    
    @JsonProperty("shop")
    private Shop shop;
    
    @JsonProperty("state")
    private CustomerDataRequestState state;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

public enum DataAccessType {
    DATA_ACCESS,
    DATA_PORTABILITY,
    DATA_DELETION
}

public enum CustomerDataRequestState {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    CANCELLED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("dataRequest")
    private CustomerDataRequest dataRequest;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("requestDate")
    private DateTime requestDate;
    
    @JsonProperty("requestType")
    private CustomerRequestType requestType;
    
    @JsonProperty("status")
    private CustomerRequestStatus status;
}

public enum CustomerRequestType {
    ERASURE,
    ACCESS,
    PORTABILITY,
    OBJECTION,
    RECTIFICATION,
    RESTRICTION_PROCESSING
}

public enum CustomerRequestStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    DENIED,
    EXPIRED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecord {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("consentCollectedFrom")
    private ConsentCollectedFrom consentCollectedFrom;
    
    @JsonProperty("consentDate")
    private DateTime consentDate;
    
    @JsonProperty("consentUpdatedAt")
    private DateTime consentUpdatedAt;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("emailConsentState")
    private CustomerEmailMarketingState emailConsentState;
    
    @JsonProperty("emailMarketingConsent")
    private CustomerEmailMarketingConsent emailMarketingConsent;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("smsConsentState")
    private CustomerSmsMarketingState smsConsentState;
    
    @JsonProperty("smsMarketingConsent")
    private CustomerSmsMarketingConsent smsMarketingConsent;
}

public enum ConsentCollectedFrom {
    SHOPIFY,
    OTHER
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEmailMarketingConsent {
    
    @JsonProperty("consentUpdatedAt")
    private DateTime consentUpdatedAt;
    
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    @JsonProperty("marketingState")
    private CustomerEmailMarketingState marketingState;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSmsMarketingConsent {
    
    @JsonProperty("consentCollectedFrom")
    private CustomerConsentCollectedFrom consentCollectedFrom;
    
    @JsonProperty("consentUpdatedAt")
    private DateTime consentUpdatedAt;
    
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    @JsonProperty("marketingState")
    private CustomerSmsMarketingState marketingState;
}

public enum CustomerEmailMarketingState {
    NOT_SUBSCRIBED,
    PENDING,
    SUBSCRIBED,
    UNSUBSCRIBED,
    REDACTED,
    INVALID
}

public enum CustomerSmsMarketingState {
    NOT_SUBSCRIBED,
    PENDING,
    SUBSCRIBED,
    UNSUBSCRIBED,
    REDACTED
}

public enum CustomerMarketingOptInLevel {
    SINGLE_OPT_IN,
    CONFIRMED_OPT_IN,
    UNKNOWN
}

public enum CustomerConsentCollectedFrom {
    SHOPIFY,
    OTHER
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivacyPolicy {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("body")
    private String body;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("url")
    private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProtectionSetting {
    
    @JsonProperty("dataRetentionPeriod")
    private DataRetentionPeriod dataRetentionPeriod;
    
    @JsonProperty("dataRetentionPeriodDays")
    private Integer dataRetentionPeriodDays;
    
    @JsonProperty("requestProcessingDays")
    private Integer requestProcessingDays;
    
    @JsonProperty("rightToBeInformed")
    private Boolean rightToBeInformed;
    
    @JsonProperty("rightOfAccess")
    private Boolean rightOfAccess;
    
    @JsonProperty("rightToRectification")
    private Boolean rightToRectification;
    
    @JsonProperty("rightToErasure")
    private Boolean rightToErasure;
    
    @JsonProperty("rightToRestriction")
    private Boolean rightToRestriction;
    
    @JsonProperty("rightToPortability")
    private Boolean rightToPortability;
    
    @JsonProperty("rightToObject")
    private Boolean rightToObject;
    
    @JsonProperty("automatedDecisionMaking")
    private Boolean automatedDecisionMaking;
}

public enum DataRetentionPeriod {
    THREE_MONTHS,
    SIX_MONTHS,
    TWELVE_MONTHS,
    EIGHTEEN_MONTHS,
    TWENTY_FOUR_MONTHS,
    THIRTY_SIX_MONTHS,
    CUSTOM
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GDPRRequest {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("customer")
    private Customer customer;
    
    @JsonProperty("customerEmail")
    private String customerEmail;
    
    @JsonProperty("dataCategories")
    private List<DataCategory> dataCategories;
    
    @JsonProperty("processingState")
    private GDPRRequestState processingState;
    
    @JsonProperty("requestType")
    private GDPRRequestType requestType;
    
    @JsonProperty("requestedAt")
    private DateTime requestedAt;
    
    @JsonProperty("completedAt")
    private DateTime completedAt;
    
    @JsonProperty("deniedAt")
    private DateTime deniedAt;
    
    @JsonProperty("deniedReason")
    private String deniedReason;
}

public enum GDPRRequestState {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    DENIED,
    EXPIRED
}

public enum GDPRRequestType {
    ACCESS,
    PORTABILITY,
    ERASURE,
    RECTIFICATION,
    RESTRICTION,
    OBJECTION
}

public enum DataCategory {
    CUSTOMER_DATA,
    ORDER_DATA,
    DRAFT_ORDER_DATA,
    PRODUCT_DATA,
    COLLECTION_DATA,
    INVENTORY_DATA,
    LOCATION_DATA,
    MARKETING_DATA,
    ANALYTICS_DATA,
    FINANCIAL_DATA,
    SHIPPING_DATA,
    TAX_DATA
}

// Connection types
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequestConnection {
    
    @JsonProperty("edges")
    private List<CustomerDataRequestEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequestEdge {
    
    @JsonProperty("node")
    private CustomerDataRequest node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestConnection {
    
    @JsonProperty("edges")
    private List<CustomerRequestEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestEdge {
    
    @JsonProperty("node")
    private CustomerRequest node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecordConnection {
    
    @JsonProperty("edges")
    private List<ConsentRecordEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecordEdge {
    
    @JsonProperty("node")
    private ConsentRecord node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GDPRRequestConnection {
    
    @JsonProperty("edges")
    private List<GDPRRequestEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GDPRRequestEdge {
    
    @JsonProperty("node")
    private GDPRRequest node;
    
    @JsonProperty("cursor")
    private String cursor;
}

// Input classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestInput {
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("requestType")
    private CustomerRequestType requestType;
    
    @JsonProperty("dataCategories")
    private List<DataCategory> dataCategories;
    
    @JsonProperty("reason")
    private String reason;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRecordInput {
    
    @JsonProperty("customerId")
    private ID customerId;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("emailConsentState")
    private CustomerEmailMarketingState emailConsentState;
    
    @JsonProperty("smsConsentState")
    private CustomerSmsMarketingState smsConsentState;
    
    @JsonProperty("consentCollectedFrom")
    private ConsentCollectedFrom consentCollectedFrom;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataProtectionSettingInput {
    
    @JsonProperty("dataRetentionPeriod")
    private DataRetentionPeriod dataRetentionPeriod;
    
    @JsonProperty("dataRetentionPeriodDays")
    private Integer dataRetentionPeriodDays;
    
    @JsonProperty("requestProcessingDays")
    private Integer requestProcessingDays;
    
    @JsonProperty("rightToBeInformed")
    private Boolean rightToBeInformed;
    
    @JsonProperty("rightOfAccess")
    private Boolean rightOfAccess;
    
    @JsonProperty("rightToRectification")
    private Boolean rightToRectification;
    
    @JsonProperty("rightToErasure")
    private Boolean rightToErasure;
    
    @JsonProperty("rightToRestriction")
    private Boolean rightToRestriction;
    
    @JsonProperty("rightToPortability")
    private Boolean rightToPortability;
    
    @JsonProperty("rightToObject")
    private Boolean rightToObject;
    
    @JsonProperty("automatedDecisionMaking")
    private Boolean automatedDecisionMaking;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEmailMarketingConsentInput {
    
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    @JsonProperty("marketingState")
    private CustomerEmailMarketingState marketingState;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSmsMarketingConsentInput {
    
    @JsonProperty("marketingOptInLevel")
    private CustomerMarketingOptInLevel marketingOptInLevel;
    
    @JsonProperty("marketingState")
    private CustomerSmsMarketingState marketingState;
    
    @JsonProperty("consentCollectedFrom")
    private CustomerConsentCollectedFrom consentCollectedFrom;
}

// Common model references
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
public class Shop {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
}