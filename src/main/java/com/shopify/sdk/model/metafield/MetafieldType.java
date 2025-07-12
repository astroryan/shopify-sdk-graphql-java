package com.shopify.sdk.model.metafield;

public enum MetafieldType {
    // String types
    SINGLE_LINE_TEXT_FIELD("single_line_text_field"),
    MULTI_LINE_TEXT_FIELD("multi_line_text_field"),
    RICH_TEXT_FIELD("rich_text_field"),
    
    // Number types
    NUMBER_INTEGER("number_integer"),
    NUMBER_DECIMAL("number_decimal"),
    
    // Date types
    DATE("date"),
    DATE_TIME("date_time"),
    
    // Boolean type
    BOOLEAN("boolean"),
    
    // Color type
    COLOR("color"),
    
    // Weight type
    WEIGHT("weight"),
    
    // Volume type
    VOLUME("volume"),
    
    // Dimension type
    DIMENSION("dimension"),
    
    // Money type
    MONEY("money"),
    
    // Rating type
    RATING("rating"),
    
    // URL type
    URL("url"),
    
    // JSON type
    JSON("json"),
    
    // File reference types
    FILE_REFERENCE("file_reference"),
    PAGE_REFERENCE("page_reference"),
    PRODUCT_REFERENCE("product_reference"),
    VARIANT_REFERENCE("variant_reference"),
    COLLECTION_REFERENCE("collection_reference"),
    
    // List types
    LIST_SINGLE_LINE_TEXT_FIELD("list.single_line_text_field"),
    LIST_NUMBER_INTEGER("list.number_integer"),
    LIST_NUMBER_DECIMAL("list.number_decimal"),
    LIST_DATE("list.date"),
    LIST_DATE_TIME("list.date_time"),
    LIST_COLOR("list.color"),
    LIST_WEIGHT("list.weight"),
    LIST_VOLUME("list.volume"),
    LIST_DIMENSION("list.dimension"),
    LIST_MONEY("list.money"),
    LIST_RATING("list.rating"),
    LIST_URL("list.url"),
    LIST_FILE_REFERENCE("list.file_reference"),
    LIST_PAGE_REFERENCE("list.page_reference"),
    LIST_PRODUCT_REFERENCE("list.product_reference"),
    LIST_VARIANT_REFERENCE("list.variant_reference"),
    LIST_COLLECTION_REFERENCE("list.collection_reference"),
    
    // Legacy types (deprecated but still supported)
    STRING("string"),
    INTEGER("integer");
    
    private final String value;
    
    MetafieldType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static MetafieldType fromValue(String value) {
        if (value == null) {
            return null;
        }
        
        for (MetafieldType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        
        throw new IllegalArgumentException("Unknown metafield type: " + value);
    }
    
    public boolean isStringType() {
        return this == SINGLE_LINE_TEXT_FIELD || this == MULTI_LINE_TEXT_FIELD || 
               this == RICH_TEXT_FIELD || this == STRING;
    }
    
    public boolean isNumberType() {
        return this == NUMBER_INTEGER || this == NUMBER_DECIMAL || this == INTEGER;
    }
    
    public boolean isDateType() {
        return this == DATE || this == DATE_TIME;
    }
    
    public boolean isBooleanType() {
        return this == BOOLEAN;
    }
    
    public boolean isListType() {
        return value.startsWith("list.");
    }
    
    public boolean isReferenceType() {
        return value.contains("_reference");
    }
    
    public boolean isLegacyType() {
        return this == STRING || this == INTEGER;
    }
}