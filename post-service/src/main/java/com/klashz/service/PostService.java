package com.klashz.service;

import com.klashz.client.CommentClient;
import com.klashz.client.CommentDto;
import com.klashz.model.PostDto;
import com.klashz.model.PostEntity;
import com.klashz.repository.PostRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class PostService implements IPostEntityService {

    @Inject
    @RestClient
    CommentClient clientComment;

    @Inject
    PostRepository postRepository;


    @Override
    public Optional<PostEntity> getPostEntityById(String id) {
       Optional<PostEntity> postOptional = postRepository.findByIdOptional(id);
       if(postOptional.isPresent()){
           PostEntity post2  = postOptional.get();
           post2.viewCount+=1;
           post2.persist();
           return Optional.of(post2);
       }
        return Optional.empty();
    }

    @Override
    public List<PostDto> getAllPostEntity() {
        return postRepository.listAll(Sort.by("title",Sort.Direction.Ascending)).stream().map(
                post -> new PostDto
                        (post.id,post.title,post.description,post.imageUrl,post.localdate,post.likeCount,post.tags,post.viewCount,clientComment.getAllCommentByPost(post.id))).toList();
    }

    @Override
    public PostEntity savePostEntity(PostEntity postEntity) {
        postEntity.localdate = LocalDate.now();
        if(postEntity.imageUrl == null ){
            postEntity.imageUrl = "https://image.example.com";
        }
        postEntity.persist();
        return postEntity;
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(String idPost) {
        return  clientComment.getAllCommentByPost(idPost);
    }

    @Override
    public Optional<PostEntity> update(PostEntity postEntity) {
        Optional<PostEntity>  post  = PostEntity.findByIdOptional(postEntity.id);
        if(post.isPresent()){
            PostEntity postUpdate = post.get();
            postUpdate.title = postEntity.title;
            postUpdate.description = postEntity.description;
            postUpdate.imageUrl = postEntity.imageUrl;
            postUpdate.localdate = postEntity.localdate;
            postUpdate.likeCount = postEntity.likeCount;
            postUpdate.tags = postEntity.tags;
            postUpdate.viewCount = postEntity.viewCount;
            postUpdate.persist();
            return Optional.of(postUpdate);
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePostEntityById(String id) {
        return PostEntity.deleteById(id);
    }

    @Override
    public boolean likePostEntity(String id) {
        PostEntity post = PostEntity.findById(id);
        if(post!=null){
            post.likeCount+=1;
            post.persist();
            return true;
        }
        return false;

    }

    @Override
    public List<PostEntity> getAllPostEntityByTitle(String title) {
        String filter = "%" + title + "%";
        return PostEntity.list("title ILike ?1",filter);
    }
}
