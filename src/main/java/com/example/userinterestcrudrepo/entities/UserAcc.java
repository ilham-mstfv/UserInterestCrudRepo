package com.example.userinterestcrudrepo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user_accs")
public class UserAcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 64)
    private String username;

    @Size(max = 64)
    private String password;

    public UserAcc() {}

    public UserAcc (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
