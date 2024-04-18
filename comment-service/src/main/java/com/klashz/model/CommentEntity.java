package com.klashz.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity  extends PanacheEntity {
    @NotBlank @NotNull
    public String description;
    @CreationTimestamp
    public LocalDateTime localDateTime;
    public int likeCount;
    @NotBlank @NotNull
    public String idPost;
}
