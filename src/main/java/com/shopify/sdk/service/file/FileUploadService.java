package com.shopify.sdk.service.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.sdk.client.ShopifyGraphQLClient;
import com.shopify.sdk.model.file.StagedUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    
    private final ShopifyGraphQLClient graphQLClient;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    
    // GraphQL mutations
    private static final String STAGED_UPLOADS_CREATE = """
        mutation stagedUploadsCreate($input: [StagedUploadInput!]!) {
            stagedUploadsCreate(input: $input) {
                stagedTargets {
                    url
                    resourceUrl
                    parameters {
                        name
                        value
                    }
                }
                userErrors {
                    field
                    message
                }
            }
        }
        """;
    
    /**
     * Creates staged upload targets for files.
     */
    public Mono<List<StagedUpload>> createStagedUploads(String shop, String accessToken, List<StagedUploadInput> inputs) {
        Map<String, Object> variables = Map.of("input", inputs);
        
        return graphQLClient.query(shop, accessToken, STAGED_UPLOADS_CREATE, variables)
            .map(response -> {
                try {
                    var data = response.getData().get("stagedUploadsCreate");
                    var stagedTargets = data.get("stagedTargets");
                    
                    // Check for user errors
                    var userErrors = data.get("userErrors");
                    if (userErrors.isArray() && userErrors.size() > 0) {
                        String errorMessage = userErrors.get(0).get("message").asText();
                        throw new RuntimeException("Staged upload creation failed: " + errorMessage);
                    }
                    
                    return objectMapper.convertValue(stagedTargets,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, StagedUpload.class));
                } catch (Exception e) {
                    log.error("Failed to parse staged uploads create response", e);
                    throw new RuntimeException("Failed to parse staged uploads create response", e);
                }
            });
    }
    
    /**
     * Creates a single staged upload target.
     */
    public Mono<StagedUpload> createStagedUpload(String shop, String accessToken, StagedUploadInput input) {
        return createStagedUploads(shop, accessToken, List.of(input))
            .map(uploads -> uploads.isEmpty() ? null : uploads.get(0));
    }
    
    /**
     * Uploads a file to a staged upload target.
     */
    public Mono<String> uploadFile(StagedUpload stagedUpload, File file) {
        if (!stagedUpload.hasRequiredParameters()) {
            return Mono.error(new IllegalArgumentException("Staged upload missing required parameters"));
        }
        
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            
            // Add all staged upload parameters
            for (StagedUpload.Parameter param : stagedUpload.getParameters()) {
                builder.part(param.getName(), param.getValue());
            }
            
            // Add the file
            builder.part("file", Files.readAllBytes(file.toPath()))
                .filename(file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
            
            return webClient.post()
                .uri(stagedUpload.getUrl())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return stagedUpload.getResourceUrl();
                    } else {
                        throw new RuntimeException("File upload failed with status: " + response.getStatusCode());
                    }
                })
                .doOnSuccess(resourceUrl -> log.info("Successfully uploaded file {} to {}", file.getName(), resourceUrl))
                .doOnError(error -> log.error("Failed to upload file {}", file.getName(), error));
                
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Failed to read file: " + file.getName(), e));
        }
    }
    
    /**
     * Uploads a file from a Resource.
     */
    public Mono<String> uploadFile(StagedUpload stagedUpload, Resource resource, String filename) {
        if (!stagedUpload.hasRequiredParameters()) {
            return Mono.error(new IllegalArgumentException("Staged upload missing required parameters"));
        }
        
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            
            // Add all staged upload parameters
            for (StagedUpload.Parameter param : stagedUpload.getParameters()) {
                builder.part(param.getName(), param.getValue());
            }
            
            // Add the file
            builder.part("file", resource)
                .filename(filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
            
            return webClient.post()
                .uri(stagedUpload.getUrl())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return stagedUpload.getResourceUrl();
                    } else {
                        throw new RuntimeException("File upload failed with status: " + response.getStatusCode());
                    }
                })
                .doOnSuccess(resourceUrl -> log.info("Successfully uploaded resource {} to {}", filename, resourceUrl))
                .doOnError(error -> log.error("Failed to upload resource {}", filename, error));
                
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Failed to upload resource: " + filename, e));
        }
    }
    
    /**
     * Uploads a file with automatic staged upload creation.
     */
    public Mono<String> uploadFileWithStaging(String shop, String accessToken, File file, String purpose) {
        StagedUploadInput input = StagedUploadInput.builder()
            .filename(file.getName())
            .mimeType(getMimeType(file))
            .httpMethod("POST")
            .resource(purpose)
            .build();
        
        return createStagedUpload(shop, accessToken, input)
            .flatMap(stagedUpload -> uploadFile(stagedUpload, file));
    }
    
    /**
     * Uploads a Resource with automatic staged upload creation.
     */
    public Mono<String> uploadResourceWithStaging(String shop, String accessToken, Resource resource, 
                                                 String filename, String purpose) {
        StagedUploadInput input = StagedUploadInput.builder()
            .filename(filename)
            .mimeType(getMimeType(filename))
            .httpMethod("POST")
            .resource(purpose)
            .build();
        
        return createStagedUpload(shop, accessToken, input)
            .flatMap(stagedUpload -> uploadFile(stagedUpload, resource, filename));
    }
    
    /**
     * Creates a staged upload for bulk operation files.
     */
    public Mono<StagedUpload> createBulkUploadTarget(String shop, String accessToken, String filename) {
        StagedUploadInput input = StagedUploadInput.builder()
            .filename(filename)
            .mimeType("application/jsonl")  // JSONL format for bulk operations
            .httpMethod("POST")
            .resource("BULK_MUTATION_VARIABLES")
            .build();
        
        return createStagedUpload(shop, accessToken, input);
    }
    
    /**
     * Creates a staged upload for general file uploads.
     */
    public Mono<StagedUpload> createFileUploadTarget(String shop, String accessToken, String filename, String mimeType) {
        StagedUploadInput input = StagedUploadInput.builder()
            .filename(filename)
            .mimeType(mimeType)
            .httpMethod("POST")
            .resource("FILE")
            .build();
        
        return createStagedUpload(shop, accessToken, input);
    }
    
    /**
     * Determines MIME type from file.
     */
    private String getMimeType(File file) {
        try {
            String mimeType = Files.probeContentType(file.toPath());
            return mimeType != null ? mimeType : "application/octet-stream";
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }
    
    /**
     * Determines MIME type from filename.
     */
    private String getMimeType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return switch (extension) {
            case "json" -> "application/json";
            case "jsonl" -> "application/jsonl";
            case "csv" -> "text/csv";
            case "txt" -> "text/plain";
            case "pdf" -> "application/pdf";
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "svg" -> "image/svg+xml";
            default -> "application/octet-stream";
        };
    }
    
    /**
     * Input for creating staged uploads.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class StagedUploadInput {
        private String filename;
        private String mimeType;
        private String httpMethod;
        private String resource;
    }
}