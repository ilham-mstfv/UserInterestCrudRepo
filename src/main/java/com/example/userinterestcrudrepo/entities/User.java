package com.example.userinterestcrudrepo.entities;

import com.example.userinterestcrudrepo.models.Country;
import com.example.userinterestcrudrepo.models.Lang;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private int id;

    @NotBlank
    @Size(min = 2, max = 64)
    private String personalData;

    @NotNull
    @Min(1)
    @Max(150)
    private int age;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private List<Interest> interests;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Country country;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Lang lang;

    @NotNull
    private BigDecimal balance;

    public User(String personalData, int age, List<Interest> interests,
                Country country, Lang lang, BigDecimal balance) {
        this.personalData = personalData;
        this.age = age;
        this.interests = interests;
        this.country = country;
        this.lang = lang;
        this.balance = balance;

        this.id = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
    }
}



