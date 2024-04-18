package com.klashz;

import com.klashz.dto.AuthResponse;
import com.klashz.service.IUserService;
import com.klashz.utils.TokenService;
import com.klashz.dto.AuthRequest;
import com.klashz.model.UserEntity;
import com.klashz.utils.PBKDF2Encoder;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Optional;

@Path("/user")
@Transactional
public class AuthenticationRest {

    @Inject
    TokenService tokenService;

    @Inject
    IUserService iUserService;

    @Operation(summary = "User login endpoint.",
    description = "This endpoint allows users to log in to the system. It receives a JSON object containing the username and password in the request body. It checks if the user exists in the system based on the provided username. If the user exists, it verifies if the provided password matches the stored password. If the credentials are correct, it generates a JWT token for the user and returns it in the response. If the user does not exist or the password is incorrect, it returns an appropriate error response.")

    @PermitAll
    @POST
    @Path("/login") @Produces({MediaType.APPLICATION_JSON}) @Consumes(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {

        Optional<UserEntity> userEntity = iUserService.getUserByUsername(authRequest.username);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            return iUserService.passwordLoginEquals(authRequest.password,user.getPassword()) ?
                    Response.ok(new AuthResponse(tokenService.generateToken(user.getUsername(),user.getRol().toString()))).build() : Response.status(Response.Status.BAD_REQUEST).build();

        }
        return Response.status(Response.Status.NOT_FOUND).entity("Not exists user").build();

    }

    @Operation(summary = "User registration endpoint.",
            description = "This endpoint allows users to register in the system. It receives a JSON object containing user details such as username, password, and other necessary information in the request body. It validates the provided user data using Bean Validation (@Valid). If the user data is valid, it saves the user in the system and returns a 201 Created status along with the saved user details in the response.")

    @PermitAll
    @POST
    @Path("/register") @Produces({MediaType.APPLICATION_JSON}) @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserEntity userEntity){
        return Response.status(201).entity(iUserService.saveUser(userEntity)).build();
    }


}
