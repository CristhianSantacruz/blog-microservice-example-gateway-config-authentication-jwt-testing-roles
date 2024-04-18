package com.klashz.repository;

import com.klashz.model.PostEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PostRepository  implements PanacheRepositoryBase<PostEntity,String> {
}
