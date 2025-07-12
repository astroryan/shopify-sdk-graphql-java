package com.shopify.sdk.service.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyRestClient;
import com.shopify.sdk.model.location.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    
    private final ShopifyRestClient restClient;
    private final ObjectMapper objectMapper;
    
    public Mono<List<Location>> getLocations(String shop, String accessToken) {
        String endpoint = "/admin/api/2023-10/locations.json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("locations");
                    return objectMapper.convertValue(data,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Location.class));
                } catch (Exception e) {
                    log.error("Failed to parse locations response", e);
                    throw new RuntimeException("Failed to parse locations response", e);
                }
            });
    }
    
    public Mono<Location> getLocation(String shop, String accessToken, String locationId) {
        String endpoint = "/admin/api/2023-10/locations/" + locationId + ".json";
        
        return restClient.get(shop, accessToken, endpoint)
            .map(response -> {
                try {
                    var data = response.get("location");
                    return objectMapper.convertValue(data, Location.class);
                } catch (Exception e) {
                    log.error("Failed to parse location response", e);
                    throw new RuntimeException("Failed to parse location response", e);
                }
            });
    }
    
    public Mono<Location> updateLocation(String shop, String accessToken, String locationId, LocationUpdateRequest request) {
        String endpoint = "/admin/api/2023-10/locations/" + locationId + ".json";
        
        return restClient.post(shop, accessToken, endpoint, Map.of("location", request))
            .map(response -> {
                try {
                    var data = response.get("location");
                    return objectMapper.convertValue(data, Location.class);
                } catch (Exception e) {
                    log.error("Failed to parse update location response", e);
                    throw new RuntimeException("Failed to parse update location response", e);
                }
            });
    }
    
    public Mono<List<Location>> getActiveLocations(String shop, String accessToken) {
        return getLocations(shop, accessToken)
            .map(locations -> locations.stream()
                .filter(Location::isActive)
                .toList());
    }
    
    public Mono<List<Location>> getLocationsByCountry(String shop, String accessToken, String countryCode) {
        return getLocations(shop, accessToken)
            .map(locations -> locations.stream()
                .filter(location -> countryCode.equals(location.getCountryCode()))
                .toList());
    }
    
    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    @lombok.Builder
    public static class LocationUpdateRequest {
        private String name;
        private String address1;
        private String address2;
        private String city;
        private String zip;
        private String province;
        private String country;
        private String phone;
        private String provinceCode;
        private String countryCode;
        private Boolean active;
    }
}