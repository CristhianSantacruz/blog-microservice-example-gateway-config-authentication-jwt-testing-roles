package com.klashz.model;


import com.klashz.client.CommentDto;

import java.time.LocalDate;
import java.util.List;

public class PostDto {
    private String id;
    private  String title;
    private  String description;
    private String imageUrl;
    private LocalDate localdate;
    private int likeCount;
    private List<String> tags;
    private int viewCount;
    private List<CommentDto> commentDtos;

    public PostDto(String id, String title, String description, String imageUrl, LocalDate localdate, int likeCount, List<String> tags, int viewCount, List<CommentDto> commentDtos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.localdate = localdate;
        this.likeCount = likeCount;
        this.tags = tags;
        this.viewCount = viewCount;
        this.commentDtos = commentDtos;
    }

    public int getCommentCount(){
        return this.commentDtos.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getLocaldate() {
        return localdate;
    }

    public void setLocaldate(LocalDate localdate) {
        this.localdate = localdate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<CommentDto> getCommentDtos() {
        return commentDtos;
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }


}
