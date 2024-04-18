package com.klashz.client;

import java.time.LocalDateTime;

public class CommentDto {

    private Long id;
    private String description;
    private LocalDateTime localDateTime;
    private int likeCount;

    public CommentDto(Long id, String description, LocalDateTime localDateTime, int likeCount) {
        this.id = id;
        this.description = description;
        this.localDateTime = localDateTime;
        this.likeCount = likeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

}
