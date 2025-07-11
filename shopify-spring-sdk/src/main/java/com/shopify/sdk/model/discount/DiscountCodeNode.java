package com.shopify.sdk.model.discount;

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
public class DiscountCodeNode extends DiscountNode {
    
    @JsonProperty("codeDiscount")
    private CodeDiscount codeDiscount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CodeDiscount {
    
    @JsonProperty("__typename")
    private String typename;
    
    @JsonProperty("codes")
    private DiscountRedeemCodeConnection codes;
    
    @JsonProperty("combinesWith")
    private DiscountCombinesWith combinesWith;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("customerSelection")
    private DiscountCustomerSelection customerSelection;
    
    @JsonProperty("hasTimelineComment")
    private Boolean hasTimelineComment;
    
    @JsonProperty("shareableUrls")
    private List<DiscountShareableUrl> shareableUrls;
    
    @JsonProperty("startsAt")
    private DateTime startsAt;
    
    @JsonProperty("status")
    private DiscountStatus status;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("totalSales")
    private MoneyV2 totalSales;
    
    @JsonProperty("updatedAt")
    private DateTime updatedAt;
    
    @JsonProperty("usageLimit")
    private Integer usageLimit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeBasic extends CodeDiscount {
    
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    @JsonProperty("asyncUsageCount")
    private Integer asyncUsageCount;
    
    @JsonProperty("codeCount")
    private Integer codeCount;
    
    @JsonProperty("customerGets")
    private DiscountCustomerGets customerGets;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirement minimumRequirement;
    
    @JsonProperty("recurringCycleLimit")
    private Integer recurringCycleLimit;
    
    @JsonProperty("shortSummary")
    private String shortSummary;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeBxgy extends CodeDiscount {
    
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    @JsonProperty("asyncUsageCount")
    private Integer asyncUsageCount;
    
    @JsonProperty("codeCount")
    private Integer codeCount;
    
    @JsonProperty("customerBuys")
    private DiscountCustomerBuys customerBuys;
    
    @JsonProperty("customerGets")
    private DiscountCustomerGets customerGets;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("usesPerOrderLimit")
    private Integer usesPerOrderLimit;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeFreeShipping extends CodeDiscount {
    
    @JsonProperty("appliesOncePerCustomer")
    private Boolean appliesOncePerCustomer;
    
    @JsonProperty("asyncUsageCount")
    private Integer asyncUsageCount;
    
    @JsonProperty("codeCount")
    private Integer codeCount;
    
    @JsonProperty("destinationSelection")
    private DiscountShippingDestinationSelection destinationSelection;
    
    @JsonProperty("endsAt")
    private DateTime endsAt;
    
    @JsonProperty("maximumShippingPrice")
    private MoneyV2 maximumShippingPrice;
    
    @JsonProperty("minimumRequirement")
    private DiscountMinimumRequirement minimumRequirement;
    
    @JsonProperty("recurringCycleLimit")
    private Integer recurringCycleLimit;
    
    @JsonProperty("shortSummary")
    private String shortSummary;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountRedeemCodeConnection {
    
    @JsonProperty("edges")
    private List<DiscountRedeemCodeEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountRedeemCodeEdge {
    
    @JsonProperty("node")
    private DiscountRedeemCode node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRedeemCode {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("asyncUsageCount")
    private Integer asyncUsageCount;
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("createdBy")
    private DiscountRedeemCodeBulkCreation createdBy;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountRedeemCodeBulkCreation {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("codesCreatedAt")
    private DateTime codesCreatedAt;
    
    @JsonProperty("createdAt")
    private DateTime createdAt;
    
    @JsonProperty("discountCode")
    private DiscountCodeNode discountCode;
    
    @JsonProperty("done")
    private Boolean done;
    
    @JsonProperty("failedCount")
    private Integer failedCount;
    
    @JsonProperty("importedCount")
    private Integer importedCount;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountCustomerSelection {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiscountShareableUrl {
    
    @JsonProperty("targetItemImage")
    private Image targetItemImage;
    
    @JsonProperty("targetType")
    private String targetType;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("url")
    private String url;
}