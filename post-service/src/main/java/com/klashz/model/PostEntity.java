package com.klashz.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class PostEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;



    @NotNull @NotBlank
    public  String title;
    @NotNull @NotBlank
    public  String description;
    public String imageUrl;
    public LocalDate localdate;
    public int likeCount;
    public List<String> tags;
    public int viewCount;

    @Override
    public String toString() {
        return "PostEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", localdate=" + localdate +
                ", likeCount=" + likeCount +
                ", tags=" + tags +
                ", viewCount=" + viewCount +
                '}';
    }
}
