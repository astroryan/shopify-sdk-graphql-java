package com.shopify.sdk.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object for OAuth access token requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {
    
    /**
     * The access token for making authenticated requests.
     */
    @JsonProperty("access_token")
    private String accessToken;
    
    /**
     * The granted scopes for this token.
     */
    @JsonProperty("scope")
    private String scope;
    
    /**
     * The associated user information (for online access mode).
     */
    @JsonProperty("associated_user")
    private AssociatedUser associatedUser;
    
    /**
     * The associated user scope (for online access mode).
     */
    @JsonProperty("associated_user_scope")
    private String associatedUserScope;
    
    /**
     * The account owner information.
     */
    @JsonProperty("account_owner")
    private Boolean accountOwner;
    
    /**
     * Collaborator account information.
     */
    @JsonProperty("collaborator")
    private Boolean collaborator;
    
    /**
     * Email verified status.
     */
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    
    /**
     * Represents the associated user information for online access mode.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssociatedUser {
        
        /**
         * The user's ID.
         */
        @JsonProperty("id")
        private Long id;
        
        /**
         * The user's first name.
         */
        @JsonProperty("first_name")
        private String firstName;
        
        /**
         * The user's last name.
         */
        @JsonProperty("last_name")
        private String lastName;
        
        /**
         * The user's email address.
         */
        @JsonProperty("email")
        private String email;
        
        /**
         * Whether the user's email is verified.
         */
        @JsonProperty("email_verified")
        private Boolean emailVerified;
        
        /**
         * Whether the user is the account owner.
         */
        @JsonProperty("account_owner")
        private Boolean accountOwner;
        
        /**
         * The user's locale.
         */
        @JsonProperty("locale")
        private String locale;
        
        /**
         * Whether the user is a collaborator.
         */
        @JsonProperty("collaborator")
        private Boolean collaborator;
    }
}