package com.shopify.sdk.model.retail;

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
public class Device {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("icon")
    private String icon;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("enabledCardReaderTypes")
    private List<CardReaderType> enabledCardReaderTypes;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("type")
    private DeviceType type;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

public enum DeviceType {
    ANDROID,
    IOS,
    WEB
}

public enum CardReaderType {
    VERIFONE_P400,
    CHIPPER_2X,
    WISEPAD_3,
    STRIPE_M2,
    TAP_TO_PAY_IOS
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredential {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("activatedAt")
    private DateTime activatedAt;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("device")
    private Device device;
    
    @JsonProperty("expiresAt")
    private DateTime expiresAt;
    
    @JsonProperty("revokedAt")
    private DateTime revokedAt;
    
    @JsonProperty("staffMember")
    private StaffMember staffMember;
    
    @JsonProperty("token")
    private String token;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMember {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("avatar")
    private StaffMemberAvatar avatar;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("exists")
    private Boolean exists;
    
    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("initials")
    private List<String> initials;
    
    @JsonProperty("isShopOwner")
    private Boolean isShopOwner;
    
    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("locale")
    private String locale;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("privateMeta")
    private PrivateMeta privateMeta;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMemberAvatar {
    
    @JsonProperty("url")
    private String url;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMeta {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDevice {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("apiClient")
    private ApiClient apiClient;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("externalId")
    private String externalId;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("status")
    private PosExternalDeviceStatus status;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

public enum PosExternalDeviceStatus {
    CONNECTED,
    DISCONNECTED,
    UNKNOWN
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiClient {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSession {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("cashTrackingEnabled")
    private Boolean cashTrackingEnabled;
    
    @JsonProperty("closedAt")
    private DateTime closedAt;
    
    @JsonProperty("closingBalance")
    private MoneyV2 closingBalance;
    
    @JsonProperty("closingNote")
    private String closingNote;
    
    @JsonProperty("device")
    private Device device;
    
    @JsonProperty("openedAt")
    private DateTime openedAt;
    
    @JsonProperty("openingBalance")
    private MoneyV2 openingBalance;
    
    @JsonProperty("openingNote")
    private String openingNote;
    
    @JsonProperty("staffMember")
    private StaffMember staffMember;
    
    @JsonProperty("state")
    private RetailSessionState state;
    
    @JsonProperty("totalCashMovement")
    private MoneyV2 totalCashMovement;
    
    @JsonProperty("totalCashPayments")
    private MoneyV2 totalCashPayments;
    
    @JsonProperty("totalCashRefunds")
    private MoneyV2 totalCashRefunds;
}

public enum RetailSessionState {
    OPEN,
    CLOSED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSession {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("cashAdjustments")
    private CashAdjustmentConnection cashAdjustments;
    
    @JsonProperty("closedAt")
    private DateTime closedAt;
    
    @JsonProperty("closingBalance")
    private MoneyV2 closingBalance;
    
    @JsonProperty("device")
    private Device device;
    
    @JsonProperty("expectedBalance")
    private MoneyV2 expectedBalance;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("openedAt")
    private DateTime openedAt;
    
    @JsonProperty("openingBalance")
    private MoneyV2 openingBalance;
    
    @JsonProperty("staffMember")
    private StaffMember staffMember;
    
    @JsonProperty("state")
    private CashTrackingSessionState state;
}

public enum CashTrackingSessionState {
    OPEN,
    CLOSED
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAdjustment {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("amount")
    private MoneyV2 amount;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("note")
    private String note;
    
    @JsonProperty("staffMember")
    private StaffMember staffMember;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("address")
    private LocationAddress address;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("deactivatedAt")
    private DateTime deactivatedAt;
    
    @JsonProperty("deletable")
    private Boolean deletable;
    
    @JsonProperty("fulfillmentService")
    private FulfillmentService fulfillmentService;
    
    @JsonProperty("fulfillsOnlineOrders")
    private Boolean fulfillsOnlineOrders;
    
    @JsonProperty("hasActiveInventory")
    private Boolean hasActiveInventory;
    
    @JsonProperty("hasUnfulfilledOrders")
    private Boolean hasUnfulfilledOrders;
    
    @JsonProperty("isActive")
    private Boolean isActive;
    
    @JsonProperty("legacyResourceId")
    private String legacyResourceId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationAddress {
    
    @JsonProperty("address1")
    private String address1;
    
    @JsonProperty("address2")
    private String address2;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("countryCode")
    private String countryCode;
    
    @JsonProperty("formatted")
    private List<String> formatted;
    
    @JsonProperty("latitude")
    private Double latitude;
    
    @JsonProperty("longitude")
    private Double longitude;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("province")
    private String province;
    
    @JsonProperty("provinceCode")
    private String provinceCode;
    
    @JsonProperty("zip")
    private String zip;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentService {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    
    @JsonProperty("fulfillmentOrdersOptIn")
    private Boolean fulfillmentOrdersOptIn;
    
    @JsonProperty("handle")
    private String handle;
    
    @JsonProperty("inventoryManagement")
    private Boolean inventoryManagement;
    
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("permitsSkuSharing")
    private Boolean permitsSkuSharing;
    
    @JsonProperty("productBased")
    private Boolean productBased;
    
    @JsonProperty("type")
    private FulfillmentServiceType type;
}

public enum FulfillmentServiceType {
    GIFT_CARD,
    MANUAL,
    THIRD_PARTY
}

// Connection types
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceConnection {
    
    @JsonProperty("edges")
    private List<DeviceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEdge {
    
    @JsonProperty("node")
    private Device node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialConnection {
    
    @JsonProperty("edges")
    private List<DeviceCredentialEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialEdge {
    
    @JsonProperty("node")
    private DeviceCredential node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMemberConnection {
    
    @JsonProperty("edges")
    private List<StaffMemberEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMemberEdge {
    
    @JsonProperty("node")
    private StaffMember node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDeviceConnection {
    
    @JsonProperty("edges")
    private List<PosExternalDeviceEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDeviceEdge {
    
    @JsonProperty("node")
    private PosExternalDevice node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionConnection {
    
    @JsonProperty("edges")
    private List<RetailSessionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionEdge {
    
    @JsonProperty("node")
    private RetailSession node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionConnection {
    
    @JsonProperty("edges")
    private List<CashTrackingSessionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionEdge {
    
    @JsonProperty("node")
    private CashTrackingSession node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAdjustmentConnection {
    
    @JsonProperty("edges")
    private List<CashAdjustmentEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAdjustmentEdge {
    
    @JsonProperty("node")
    private CashAdjustment node;
    
    @JsonProperty("cursor")
    private String cursor;
}

// Input classes
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreateInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("type")
    private DeviceType type;
    
    @JsonProperty("locationId")
    private ID locationId;
    
    @JsonProperty("enabledCardReaderTypes")
    private List<CardReaderType> enabledCardReaderTypes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpdateInput {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("enabledCardReaderTypes")
    private List<CardReaderType> enabledCardReaderTypes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCredentialCreateInput {
    
    @JsonProperty("deviceId")
    private ID deviceId;
    
    @JsonProperty("staffMemberId")
    private ID staffMemberId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosExternalDeviceCreateInput {
    
    @JsonProperty("externalId")
    private String externalId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("locationId")
    private ID locationId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionOpenInput {
    
    @JsonProperty("deviceId")
    private ID deviceId;
    
    @JsonProperty("openingBalance")
    private MoneyInput openingBalance;
    
    @JsonProperty("openingNote")
    private String openingNote;
    
    @JsonProperty("cashTrackingEnabled")
    private Boolean cashTrackingEnabled;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailSessionCloseInput {
    
    @JsonProperty("sessionId")
    private ID sessionId;
    
    @JsonProperty("closingBalance")
    private MoneyInput closingBalance;
    
    @JsonProperty("closingNote")
    private String closingNote;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionOpenInput {
    
    @JsonProperty("deviceId")
    private ID deviceId;
    
    @JsonProperty("locationId")
    private ID locationId;
    
    @JsonProperty("openingBalance")
    private MoneyInput openingBalance;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTrackingSessionCloseInput {
    
    @JsonProperty("sessionId")
    private ID sessionId;
    
    @JsonProperty("closingBalance")
    private MoneyInput closingBalance;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAdjustmentCreateInput {
    
    @JsonProperty("sessionId")
    private ID sessionId;
    
    @JsonProperty("amount")
    private MoneyInput amount;
    
    @JsonProperty("note")
    private String note;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currencyCode")
    private CurrencyCode currencyCode;
}