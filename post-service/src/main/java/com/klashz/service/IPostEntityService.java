package com.klashz.service;

import com.klashz.client.CommentDto;
import com.klashz.model.PostDto;
import com.klashz.model.PostEntity;

import java.util.List;
import java.util.Optional;


public interface IPostEntityService {

    Optional<PostEntity> getPostEntityById(String id);
    List<PostDto> getAllPostEntity();
    PostEntity savePostEntity(PostEntity pstEntity);
    List<CommentDto> getAllCommentByPostId(String id);
    Optional<PostEntity> update(PostEntity postEntity);
    boolean deletePostEntityById(String id);
    boolean likePostEntity(String id);
    List<PostEntity> getAllPostEntityByTitle(String title);
    
}
