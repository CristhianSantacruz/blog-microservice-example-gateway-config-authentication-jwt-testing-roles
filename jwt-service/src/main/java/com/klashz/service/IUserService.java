package com.klashz.service;

import com.klashz.model.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface IUserService {

    Optional<UserEntity> getUserById(String id);
    Optional<UserEntity> getUserByEmail(String email);

    Optional<UserEntity> getUserByUsername(String username);
    UserEntity saveUser(UserEntity user);
    boolean deleteUser(String id);
    boolean passwordLoginEquals(String passwordAuth,String passwordUserEncode);
}
