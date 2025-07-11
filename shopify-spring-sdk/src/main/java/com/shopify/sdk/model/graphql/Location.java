package com.shopify.sdk.model.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
        public static class Location {
            private int line;
            private int column;
        }
