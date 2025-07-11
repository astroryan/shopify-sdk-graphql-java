package com.shopify.sdk.model.retail;

import com.shopify.sdk.model.common.Image;
import com.shopify.sdk.model.common.Node;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;

/**
 * Represents a staff member in retail context.
 * This is a simplified version for retail operations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffMember implements Node {
    
    @GraphQLQuery(name = "id", description = "A globally-unique ID")
    private String id;
    
    @GraphQLQuery(name = "active", description = "Whether the staff member is active")
    private Boolean active;
    
    @GraphQLQuery(name = "avatar", description = "The staff member's avatar")
    private Image avatar;
    
    @GraphQLQuery(name = "email", description = "The staff member's email")
    private String email;
    
    @GraphQLQuery(name = "exists", description = "Whether the staff member's account exists")
    private Boolean exists;
    
    @GraphQLQuery(name = "firstName", description = "The staff member's first name")
    private String firstName;
    
    @GraphQLQuery(name = "initials", description = "The staff member's initials")
    private String initials;
    
    @GraphQLQuery(name = "isShopOwner", description = "Whether the staff member is the shop owner")
    private Boolean isShopOwner;
    
    @GraphQLQuery(name = "lastName", description = "The staff member's last name")
    private String lastName;
    
    @GraphQLQuery(name = "locale", description = "The staff member's locale")
    private String locale;
    
    @GraphQLQuery(name = "name", description = "The staff member's full name")
    private String name;
    
    @GraphQLQuery(name = "phone", description = "The staff member's phone number")
    private String phone;
}