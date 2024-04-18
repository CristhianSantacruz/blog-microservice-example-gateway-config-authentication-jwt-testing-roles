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

    @Operation(summary = "Retrieves all posts without comments.",description = "For the /all endpoint, it retrieves all posts from the database without their associated comments. This endpoint returns a list of posts in the response, excluding any comments that may be associated with them.")

    @GET @Path("/all")
    public Response  getAllPost(){
        return Response.ok().entity(postRepository.listAll()).build();
    }

    @Operation(summary = "Retrieves a specific post by its ID.",description = "For the /id endpoint, it retrieves a specific post from the database based on the provided ID. If the post with the given ID is found, it returns the post details in the response. If the post is not found, it returns a 400 Bad Request status.")

    @GET @Path("/{id}")
    public Response getPostById(@PathParam("id") String id){
        Optional<PostEntity> postEntityOptional = service.getPostEntityById(id);
        return postEntityOptional.isPresent() ? Response.ok().entity(postEntityOptional.get()).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(summary = "Retrieves posts by title.",description = "For the /search endpoint, it retrieves posts from the database based on the provided title query parameter. It returns posts whose titles match the specified title.")

    @GET @Path("/search")
    public Response getAllPostByTitle(@QueryParam("title") String title){
        return Response.ok(service.getAllPostEntityByTitle(title)).build();
    }

    @Operation(summary = " Updates a post (admin role required).",
    description = "The /update endpoint updates a post with the provided details. This endpoint requires the user to have admin privileges. It returns the updated post if successful, otherwise, it returns a 400 Bad Request status.")

    @RolesAllowed({"admin"})
    @PUT @Path("/update")
    public Response updatePost(PostEntity post){
        Optional<PostEntity> postEntityOptional = service.update(post);
        return postEntityOptional.isPresent() ? Response.ok().entity(postEntityOptional.get()).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(summary = "Likes a post.",description = "The /update/{id}/like endpoint increments the like count of the post with the specified ID. It returns a success response if the operation is successful, otherwise, it returns a 400 Bad Request status.")

    @PUT @Path("/update/{id}/like")
    public Response likePost(@PathParam("id") String  id){

        boolean like = service.likePostEntity(id);
        return like ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(summary = "Deletes a post by ID (admin role required).",description = "The /delete/{id} endpoint deletes the post with the specified ID from the database. This endpoint also requires admin privileges. It returns a success response if the post is deleted successfully, otherwise, it returns a 400 Bad Request status.")

    @RolesAllowed("admin")
    @DELETE @Path("/delete/{id}")
    public Response deletePostById(@PathParam("id") String id){
        return service.deletePostEntityById(id) ? Response.ok("").build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
