package com.example.userinterestcrudrepo.entities;

import com.example.userinterestcrudrepo.models.UserRole;
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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserAcc() {}

    public UserAcc (String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.USER;
    }

    public UserAcc (String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
