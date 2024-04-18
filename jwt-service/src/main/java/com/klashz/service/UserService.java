package com.klashz.service;

import com.klashz.model.UserEntity;
import com.klashz.repository.UserRepository;
import com.klashz.utils.PBKDF2Encoder;
import com.klashz.utils.RoleUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PBKDF2Encoder pbkdf2Encoder;

    @Override
    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findByIdOptional(id);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return Optional.of(userRepository.find("email",email).firstResult());
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return Optional.of(userRepository.find("username",username).firstResult());
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        String password = user.getPassword();
        String passwordEncrypted = pbkdf2Encoder.encode(password);
        user.setPassword(passwordEncrypted);
        if(user.getRol()!= RoleUser.admin){
            user.setRol(RoleUser.user);
        }
        userRepository.persist(user);
        return user;
    }

    @Override
    public boolean deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public boolean passwordLoginEquals(String passwordAuth,String passwordUser) {
        return passwordUser.equals(pbkdf2Encoder.encode(passwordAuth));
    }
}
