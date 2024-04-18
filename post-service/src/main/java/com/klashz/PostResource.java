package com.klashz;

import com.klashz.model.PostEntity;
import com.klashz.repository.PostRepository;
import com.klashz.service.IPostEntityService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.Optional;
import java.util.UUID;

@Path("/post")
@Transactional
public class PostResource {

    @Inject
    IPostEntityService service;
    @Inject
    PostRepository postRepository;

    @Operation(summary = "Saves a new post to the database.",description = "This endpoint receives data for a new post and saves it to the database. It validates the incoming data and stores it persistently, allowing users to create and store posts in the system for later retrieval and viewing.")

    @RolesAllowed("admin")
    @POST @Path("/save")
    public Response savePost(@Valid PostEntity postEntity){
        return Response.status(201).entity(service.savePostEntity(postEntity)).build();
    }
    @Operation(summary = "Retrieves all posts with associated comments.",description = "This endpoint retrieves all posts from the database along with their associated comments. It fetches the data from the database and returns it in the response, allowing clients to access a list of posts along with their comments.")

    @GET @Path("/all-with-comment")
    public Response  getAllPostWithComments(){
        return Response.ok().entity(service.getAllPostEntity()).build();
    }

    @GET @Path("/all")
    public Response  getAllPost(){
        return Response.ok().entity(postRepository.listAll()).build();
    }
    @GET @Path("/{id}")
    public Response getPostById(@PathParam("id") String id){
        Optional<PostEntity> postEntityOptional = service.getPostEntityById(id);
        return postEntityOptional.isPresent() ? Response.ok().entity(postEntityOptional.get()).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }
    @GET @Path("/search")
    public Response getAllPostByTitle(@QueryParam("title") String title){
        return Response.ok(service.getAllPostEntityByTitle(title)).build();
    }
    @RolesAllowed({"admin"})
    @PUT @Path("/update")
    public Response updatePost(PostEntity post){
        Optional<PostEntity> postEntityOptional = service.update(post);
        return postEntityOptional.isPresent() ? Response.ok().entity(postEntityOptional.get()).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT @Path("/update/{id}/like")
    public Response likePost(@PathParam("id") String  id){

        boolean like = service.likePostEntity(id);
        return like ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
    @RolesAllowed("admin")
    @DELETE @Path("/delete/{id}")
    public Response deletePostById(@PathParam("id") String id){
        return service.deletePostEntityById(id) ? Response.ok("").build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
