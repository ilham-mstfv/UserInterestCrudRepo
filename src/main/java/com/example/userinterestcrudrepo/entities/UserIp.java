package com.example.userinterestcrudrepo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "user_ips")
public class UserIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @NotNull
    private String ip;

    @NotNull
    private String endpoint;

    @NotNull
    private String requestMethod;

    @NotNull
    private ZonedDateTime dateTime;

    @NotNull
    @JsonIgnore
    private Long epochSecond;

    public UserIp(
            String username,
            String ip,
            String endpoint,
            String requestMethod,
            ZonedDateTime dateTime
    ) {
        this.username = username;
        this.ip = ip;
        this.endpoint = endpoint;
        this.requestMethod = requestMethod;
        this.dateTime = dateTime;
        this.epochSecond = dateTime.toEpochSecond();
    }
}
