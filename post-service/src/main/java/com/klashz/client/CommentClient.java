package com.klashz.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@Path("/comment")
@RegisterRestClient(configKey = "comment-key")
public interface CommentClient {

    @GET @Path("/post/{idPost}")
    List<CommentDto> getAllCommentByPost(@PathParam("idPost") String  idPost);
}
