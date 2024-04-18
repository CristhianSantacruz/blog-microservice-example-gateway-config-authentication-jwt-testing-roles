package com.klashz;

import com.klashz.model.CommentEntity;
import com.klashz.service.ICommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Optional;

@Path("/comment")
@Transactional
public class CommentResource {


    @Inject
    ICommentService iCommentService;

    @Operation(summary = "Retrieves all comments by post ID.",description = "This endpoint retrieves all comments associated with a specific post ID from the database. It takes the post ID as a path parameter and returns all comments related to that post in the response. This allows clients to access all comments for a particular post.")

    @GET @Path("/post/{idPost}")
    public Response getAllCommentByPostId(@PathParam("idPost") String idPost){return Response   .ok(iCommentService.getAllPostById(idPost)).build();}

    @Operation(summary = "Retrieves a comment by its ID.",description = "This endpoint retrieves a comment from the database based on the provided ID. It takes the comment ID as a path parameter and returns the details of the comment in the response if it exists. If the comment with the given ID is not found, it returns a 400 Bad Request status.")

    @GET @Path("/{id}")
    public Response getCommentById(@PathParam("id") Long id){
        Optional<CommentEntity> optionalComment = this.iCommentService.getCommentById(id);
        return optionalComment.isPresent() ?
                Response.ok(optionalComment.get()).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(summary = "Saves a new comment.",description = "This endpoint allows users (both regular users and administrators) to save a new comment. It receives the details of the comment in the request body and validates them using Bean Validation (@Valid). If the comment is valid, it saves it to the database and returns a 201 Created status along with the saved comment in the response.")

    @RolesAllowed({"user","admin"})
    @POST() @Path("/save")
    public Response saveComment(@Valid CommentEntity comment){
        return Response.status(201).entity(iCommentService.saveComment(comment)).build();
    }

    @Operation(summary = " Updates a comment.",description = "This endpoint allows users (both regular users and administrators) to update a comment. It receives the updated details of the comment in the request body and attempts to update it in the database. If the update is successful, it returns the updated comment in the response with a status of 200 OK. If the comment to be updated is not found or the update operation fails, it returns a 400 Bad Request status.")

    @RolesAllowed({"user","admin"})
    @PUT() @Path("/update")
    public Response updateComment(CommentEntity comment){
        Optional<CommentEntity> optionalComment = this.iCommentService.updateComment(comment);
        return optionalComment.isPresent() ?
                Response.ok(optionalComment.get()).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(summary = "Deletes a comment by its ID.",description = "This endpoint allows users (both regular users and administrators) to delete a comment by its ID. It receives the ID of the comment to be deleted as a path parameter and attempts to delete it from the database. If the deletion is successful, it returns a 200 OK status in the response. If the comment with the given ID is not found or the deletion operation fails, it returns a 400 Bad Request status.")

    @RolesAllowed({"user","admin"})
    @DELETE() @Path("/delete/{id}")
    public Response deleteCommentById(@PathParam("id") Long id){
        return iCommentService.deleteCommentById(id) ?
                Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

}
