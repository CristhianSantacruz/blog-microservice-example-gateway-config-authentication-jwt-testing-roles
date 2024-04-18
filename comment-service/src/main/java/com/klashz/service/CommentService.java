package com.klashz.service;

import com.klashz.model.CommentEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommentService implements ICommentService{

    @Override
    public Optional<CommentEntity> getCommentById(Long id) {
        return CommentEntity.findByIdOptional(id);
    }

    @Override
    public List<CommentEntity> getAllPostById(String idPost) {
        return CommentEntity.list("idPost",idPost);
    }

    @Override
    public CommentEntity saveComment(CommentEntity comment) {
        comment.localDateTime = LocalDateTime.now();
        comment.persist();
        return comment;
    }

    @Override
    public Optional<CommentEntity> updateComment(CommentEntity comment) {
        Optional<CommentEntity>  commentOptional = CommentEntity.findByIdOptional(comment.id);
        if(commentOptional.isPresent()){
            CommentEntity newComment = commentOptional.get();
            newComment.description = comment.description;
            newComment.likeCount =  comment.likeCount;
            newComment.persist();
            return Optional.of(newComment);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteCommentById(Long id) {
        return CommentEntity.deleteById(id);
    }
}
