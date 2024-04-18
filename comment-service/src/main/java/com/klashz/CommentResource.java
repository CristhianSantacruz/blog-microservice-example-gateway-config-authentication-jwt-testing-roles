package com.klashz;

import com.klashz.model.CommentEntity;
import com.klashz.service.ICommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.Optional;

@Path("/comment")
@Transactional
public class CommentResource {


    @Inject
    ICommentService iCommentService;

    @GET @Path("/post/{idPost}")
    public Response getAllCommentByPostId(@PathParam("idPost") String idPost){return Response   .ok(iCommentService.getAllPostById(idPost)).build();}

    @GET @Path("/{id}")
    public Response getCommentById(@PathParam("id") Long id){
        Optional<CommentEntity> optionalComment = this.iCommentService.getCommentById(id);
        return optionalComment.isPresent() ?
                Response.ok(optionalComment.get()).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
    @RolesAllowed({"user","admin"})
    @POST() @Path("/save")
    public Response saveComment(@Valid CommentEntity comment){
        return Response.status(201).entity(iCommentService.saveComment(comment)).build();
    }
    @RolesAllowed({"user","admin"})
    @PUT() @Path("/update")
    public Response updateComment(CommentEntity comment){
        Optional<CommentEntity> optionalComment = this.iCommentService.updateComment(comment);
        return optionalComment.isPresent() ?
                Response.ok(optionalComment.get()).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
    @RolesAllowed({"user","admin"})
    @DELETE() @Path("/delete/{id}")
    public Response deleteCommentById(@PathParam("id") Long id){
        return iCommentService.deleteCommentById(id) ?
                Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

}
