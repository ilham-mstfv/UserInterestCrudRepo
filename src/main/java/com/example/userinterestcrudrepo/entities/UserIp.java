package com.example.userinterestcrudrepo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "user_ips")
public class UserIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @NotNull
    private String ip;

    @NotNull
    private ZonedDateTime dateTime;

    @NotNull
    private String endpoint;

    @NotNull
    private String requestMethod;

    public UserIp(
            String username,
            String ip, ZonedDateTime dateTime,
            String endpoint,
            String requestMethod
    ) {
        this.username = username;
        this.ip = ip;
        this.dateTime = dateTime;
        this.endpoint = endpoint;
        this.requestMethod = requestMethod;
    }
}
