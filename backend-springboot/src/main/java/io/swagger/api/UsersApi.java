/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-23T13:04:25.984Z[GMT]")
@Validated
public interface UsersApi {

    @Operation(summary = "Register User Data", description = "", tags={ "Customer" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "User created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid user object") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<UserDTO> addUser(@Parameter(in = ParameterIn.DEFAULT, description = "New user object", required=true, schema=@Schema()) @Valid @RequestBody UserDTO body);


    @Operation(summary = "Search a user list on email address", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employee" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/users/getByEmail/{email}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserDTO> getByEmail(@Parameter(in = ParameterIn.PATH, description = "Email input", required=true, schema=@Schema()) @PathVariable("email") String email);


    @Operation(summary = "Search a user list on username", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employee" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/users/getByUsername/{username}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserDTO> getByUsername(@Parameter(in = ParameterIn.PATH, description = "Username input", required=true, schema=@Schema()) @PathVariable("username") String username);


    @Operation(summary = "Updates a user", description = "By sending this request, an employee or customer can update the information of one user ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employee", "Customer" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User found and updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/users/getByUsername/{username}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<UserDTO> updateUser(@Parameter(in = ParameterIn.PATH, description = "Username input", required=true, schema=@Schema()) @PathVariable("username") String username, @Parameter(in = ParameterIn.DEFAULT, description = "Updated user object", required=true, schema=@Schema()) @Valid @RequestBody UserDTO body);

}

