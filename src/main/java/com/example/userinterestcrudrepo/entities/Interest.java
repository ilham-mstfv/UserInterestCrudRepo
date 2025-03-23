package com.example.userinterestcrudrepo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "interests")
public class Interest {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        @NotBlank
        @Size(max = 32)
        private String name;

        @NotBlank
        @Size(max = 32)
        private String tag;

        @NotBlank
        @Size(max = 64)
        private String description;
}
