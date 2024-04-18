package com.klashz.model;

import com.klashz.utils.RoleUser;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class UserEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull @NotBlank
    private String email;
    @NotNull @NotBlank
    private String username;
    @NotNull @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleUser rol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleUser getRol() {
        return rol;
    }

    public void setRol(RoleUser rol) {
        this.rol = rol;
    }

    public UserEntity(String id, String email, String username, String password, RoleUser rol) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public UserEntity() {}

    /*public static UserEntity findByUsername(String username) {

        //if using Panache pattern (extends or PanacheEntity PanacheEntityBase)
        //return find("username", username).firstResult();

        String userUsername = "user";

        //generated from password encoder
        String userPassword = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

        String adminUsername = "admin";

        //generated from password encoder
        String adminPassword = "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=";

        if (username.equals(userUsername)) {
            return new UserEntity("123",userUsername, userPassword, RoleUser.user);
        } else if (username.equals(adminUsername)) {
            return new UserEntity("21312",adminUsername, adminPassword, RoleUser.admin);
        } else {
            return null;
        }*/
    }


