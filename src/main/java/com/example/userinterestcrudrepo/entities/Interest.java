package com.example.userinterestcrudrepo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotBlank
        @Size(max = 32)
        @Column(unique = true)
        private String name;

        @NotBlank
        @Size(max = 32)
        private String tag;

        @NotBlank
        @Size(max = 64)
        private String description;

        public Interest(String name, String tag, String description) {
                this.name = name;
                this.tag = tag;
                this.description = description;
        }
}
