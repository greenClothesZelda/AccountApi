package com.cushion.app.accountapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    private int id;
    private String userName;
    private String password;
    private Role role;
}
