package com.klashz.service;

import com.klashz.model.CommentEntity;

import javax.xml.stream.events.Comment;
import java.util.*;
public interface ICommentService {
    Optional<CommentEntity> getCommentById(Long id);
    List<CommentEntity> getAllPostById(String idPost);
    CommentEntity saveComment(CommentEntity comment);
    Optional<CommentEntity> updateComment(CommentEntity comment);
    boolean deleteCommentById(Long id);
}
