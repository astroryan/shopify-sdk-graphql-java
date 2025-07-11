package com.shopify.sdk.model.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryProfileInput {
    
    @JsonProperty("default")
    private Boolean isDefault;
    
    @JsonProperty("locationsToActivate")
    private List<String> locationsToActivate;
    
    @JsonProperty("locationsToDeactivate")
    private List<String> locationsToDeactivate;
    
    @JsonProperty("locationsToAssociate")
    private List<String> locationsToAssociate;
    
    @JsonProperty("locationsToDisassociate")
    private List<String> locationsToDisassociate;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("originLocationGroupZonesToCreate")
    private List<DeliveryLocationGroupZoneInput> originLocationGroupZonesToCreate;
    
    @JsonProperty("originLocationGroupZonesToUpdate")
    private List<DeliveryLocationGroupZoneUpdateInput> originLocationGroupZonesToUpdate;
    
    @JsonProperty("originLocationGroupZonesToDelete")
    private List<String> originLocationGroupZonesToDelete;
    
    @JsonProperty("variantsToAssociate")
    private List<String> variantsToAssociate;
    
    @JsonProperty("variantsToDisassociate")
    private List<String> variantsToDisassociate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryLocationGroupZoneInput {
    
    @JsonProperty("countries")
    private List<DeliveryCountryInput> countries;
    
    @JsonProperty("methodDefinitionsToCreate")
    private List<DeliveryMethodDefinitionInput> methodDefinitionsToCreate;
    
    @JsonProperty("methodDefinitionsToUpdate")
    private List<DeliveryMethodDefinitionInput> methodDefinitionsToUpdate;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryLocationGroupZoneUpdateInput {
    
    @JsonProperty("countries")
    private List<DeliveryCountryInput> countries;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("methodDefinitionsToCreate")
    private List<DeliveryMethodDefinitionInput> methodDefinitionsToCreate;
    
    @JsonProperty("methodDefinitionsToUpdate")
    private List<DeliveryMethodDefinitionInput> methodDefinitionsToUpdate;
    
    @JsonProperty("name")
    private String name;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCountryInput {
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("includeAllProvinces")
    private Boolean includeAllProvinces;
    
    @JsonProperty("provinces")
    private List<String> provinces;
    
    @JsonProperty("restOfWorld")
    private Boolean restOfWorld;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodDefinitionInput {
    
    @JsonProperty("active")
    private Boolean active;
    
    @JsonProperty("combine")
    private Boolean combine;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("methodConditionsToCreate")
    private List<DeliveryConditionInput> methodConditionsToCreate;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("participantSubtitle")
    private String participantSubtitle;
    
    @JsonProperty("participantTitle")
    private String participantTitle;
    
    @JsonProperty("rateDefinition")
    private DeliveryRateDefinitionInput rateDefinition;
    
    @JsonProperty("rateProvider")
    private DeliveryRateProviderInput rateProvider;
    
    @JsonProperty("weightConditionsToCreate")
    private List<DeliveryWeightConditionInput> weightConditionsToCreate;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryConditionInput {
    
    @JsonProperty("conditionCriteria")
    private DeliveryConditionCriteriaInput conditionCriteria;
    
    @JsonProperty("field")
    private DeliveryConditionField field;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("operator")
    private DeliveryConditionOperator operator;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryConditionCriteriaInput {
    
    @JsonProperty("unit")
    private String unit;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryWeightConditionInput {
    
    @JsonProperty("criteria")
    private WeightInput criteria;
    
    @JsonProperty("operator")
    private DeliveryConditionOperator operator;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class WeightInput {
    
    @JsonProperty("unit")
    private String unit;
    
    @JsonProperty("value")
    private String value;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryRateDefinitionInput {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("price")
    private MoneyInput price;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryRateProviderInput {
    
    @JsonProperty("carrierService")
    private DeliveryCarrierServiceInput carrierService;
    
    @JsonProperty("customFlat")
    private DeliveryCustomFlatInput customFlat;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCarrierServiceInput {
    
    @JsonProperty("id")
    private String id;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DeliveryCustomFlatInput {
    
    @JsonProperty("dummy")
    private Boolean dummy;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MoneyInput {
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("currencyCode")
    private String currencyCode;
}