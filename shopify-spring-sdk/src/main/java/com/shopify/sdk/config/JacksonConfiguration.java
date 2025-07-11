package com.shopify.sdk.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopify.sdk.model.common.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson configuration for Shopify SDK.
 * Configures custom serializers and deserializers for Shopify-specific types.
 */
@Configuration
public class JacksonConfiguration {
    
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Configure for better compatibility
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // Register Java Time module
        mapper.registerModule(new JavaTimeModule());
        
        // Register custom module for Shopify types
        mapper.registerModule(shopifyModule());
        
        return mapper;
    }
    
    @Bean
    public SimpleModule shopifyModule() {
        SimpleModule module = new SimpleModule("ShopifyModule");
        
        // ID serialization
        module.addSerializer(ID.class, new JsonSerializer<ID>() {
            @Override
            public void serialize(ID value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(value.getValue());
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(ID.class, new JsonDeserializer<ID>() {
            @Override
            public ID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getText();
                return value != null ? new ID(value) : null;
            }
        });
        
        // DateTime serialization
        module.addSerializer(DateTime.class, new JsonSerializer<DateTime>() {
            @Override
            public void serialize(DateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(value.getValue());
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(DateTime.class, new JsonDeserializer<DateTime>() {
            @Override
            public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getText();
                return value != null ? new DateTime(value) : null;
            }
        });
        
        // Money serialization
        module.addSerializer(Money.class, new JsonSerializer<Money>() {
            @Override
            public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeStartObject();
                    gen.writeStringField("amount", value.getAmount());
                    if (value.getCurrencyCode() != null) {
                        gen.writeStringField("currencyCode", value.getCurrencyCode().toString());
                    }
                    gen.writeEndObject();
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(Money.class, new JsonDeserializer<Money>() {
            @Override
            public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                JsonNode node = p.getCodec().readTree(p);
                if (node == null || node.isNull()) {
                    return null;
                }
                
                Money.MoneyBuilder builder = Money.builder();
                
                if (node.has("amount")) {
                    builder.amount(node.get("amount").asText());
                }
                
                if (node.has("currencyCode")) {
                    builder.currencyCode(CurrencyCode.valueOf(node.get("currencyCode").asText()));
                }
                
                return builder.build();
            }
        });
        
        // Decimal serialization
        module.addSerializer(Decimal.class, new JsonSerializer<Decimal>() {
            @Override
            public void serialize(Decimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(value.getValue());
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(Decimal.class, new JsonDeserializer<Decimal>() {
            @Override
            public Decimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getText();
                return value != null ? new Decimal(value) : null;
            }
        });
        
        // URL serialization
        module.addSerializer(URL.class, new JsonSerializer<URL>() {
            @Override
            public void serialize(URL value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(value.getValue());
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(URL.class, new JsonDeserializer<URL>() {
            @Override
            public URL deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getText();
                return value != null ? new URL(value) : null;
            }
        });
        
        // HTML serialization
        module.addSerializer(HTML.class, new JsonSerializer<HTML>() {
            @Override
            public void serialize(HTML value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(value.getValue());
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(HTML.class, new JsonDeserializer<HTML>() {
            @Override
            public HTML deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getText();
                return value != null ? new HTML(value) : null;
            }
        });
        
        // ZonedDateTime serialization for better date handling
        module.addSerializer(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            
            @Override
            public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (value != null) {
                    gen.writeString(formatter.format(value));
                } else {
                    gen.writeNull();
                }
            }
        });
        
        module.addDeserializer(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            
            @Override
            public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getText();
                return value != null ? ZonedDateTime.parse(value, formatter) : null;
            }
        });
        
        return module;
    }
}