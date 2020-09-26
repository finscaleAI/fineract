package org.apache.fineract.infrastructure.security.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.data.AuthenticatedUserData;
import org.apache.fineract.infrastructure.security.service.SpringSecurityPlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Scope("singleton")
@Profile("bitrupeeAuth")
@Path("/passwordless")
@Tag(name = "Authentication HTTP Passwordless", description = "An API capability that allows client applications to verify authentication details using Bitrupee Authentication.")
public class BitRupeeAuthApiResource {

    private final DaoAuthenticationProvider customAuthprovider;
    private final ToApiJsonSerializer<AuthenticatedUserData> toApiJsonSerializer;
    private final SpringSecurityPlatformSecurityContext springSecurityPlatformSecurityContext;
    // Here we put the bitrupee utils in place @Todo here
    // You need to add a seperate class where we would use the bitrupee utils like
    // verifySignature()
    // getSinFromPublicKey()
    // Create a class in the service package of security where you can name that class as bitrupeeUtils
    // There create above methods

   @Autowired
    public BitRupeeAuthApiResource(
           @Qualifier("customAuthenticationProvider") final DaoAuthenticationProvider customAuthprovider,
           final ToApiJsonSerializer<AuthenticatedUserData> toApiJsonSerializer,
           final SpringSecurityPlatformSecurityContext springSecurityPlatformSecurityContext) {
       this.customAuthprovider = customAuthprovider;
       this.springSecurityPlatformSecurityContext = springSecurityPlatformSecurityContext;
       this.toApiJsonSerializer = toApiJsonSerializer;
   }

   @POST
   @Consumes({MediaType.APPLICATION_JSON})
   @Produces({ MediaType.APPLICATION_JSON })
   @Operation(summary = "Verify authentication", description = "Authenticates the credentials provided and returns the set roles and permissions allowed.")
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AuthenticationApiResourceSwagger.PostAuthenticationResponse.class))),
           @ApiResponse(responseCode = "400", description = "Unauthenticated. Please login") })
    public String auth(final String requestBodyAsJson) {
       // This is where all of the magic happens
       // Client will send keys in the header as using JS based libaray and signs them
       // On the server side here we would match those keys and would return the user object that has
       // already been created 
       return "";
   }
}
