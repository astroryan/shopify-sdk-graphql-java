package com.shopify.sdk.model.shipping;

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
public class DeliveryZone {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("countries")
    private List<DeliveryCountry> countries;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodDefinition {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("methodConditions")
    private List<DeliveryCondition> methodConditions;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("rateProvider")
    private DeliveryRateProvider rateProvider;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCondition {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("conditionCriteria")
    private DeliveryConditionCriteria conditionCriteria;
    
    @JsonProperty("field")
    private DeliveryConditionField field;
    
    @JsonProperty("operator")
    private DeliveryConditionOperator operator;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryConditionCriteria {
    
    @JsonProperty("__typename")
    private String typename;
}

public enum DeliveryConditionField {
    TOTAL_PRICE,
    TOTAL_WEIGHT
}

public enum DeliveryConditionOperator {
    GREATER_THAN_OR_EQUAL_TO,
    LESS_THAN_OR_EQUAL_TO
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryRateProvider {
    
    @JsonProperty("__typename")
    private String typename;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryParticipant {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("adaptToNewServicesFlag")
    private Boolean adaptToNewServicesFlag;
    
    @JsonProperty("carrierService")
    private DeliveryCarrierService carrierService;
    
    @JsonProperty("fixedFee")
    private MoneyV2 fixedFee;
    
    @JsonProperty("participantServices")
    private List<DeliveryParticipantService> participantServices;
    
    @JsonProperty("percentageOfRateFee")
    private Double percentageOfRateFee;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryParticipantService {
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRateDefinition {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("price")
    private MoneyV2 price;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodDefinitionConnection {
    
    @JsonProperty("edges")
    private List<DeliveryMethodDefinitionEdge> edges;
    
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryMethodDefinitionEdge {
    
    @JsonProperty("node")
    private DeliveryMethodDefinition node;
    
    @JsonProperty("cursor")
    private String cursor;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRate {
    
    @JsonProperty("id")
    private ID id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("price")
    private MoneyV2 price;
}