package com.example.userinterestcrudrepo.repository;

import com.example.userinterestcrudrepo.entities.UserAcc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserAccJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertUserAcc(UserAcc userAcc) {
        entityManager.persist(userAcc);
    }

    public UserAcc getUserAccById(int id) {
        return entityManager.find(UserAcc.class, id);
    }

    public Optional<UserAcc> getUserAccByUsername(String username) {
        return entityManager
                .createQuery("SELECT u FROM UserAcc u WHERE u.username = :username", UserAcc.class)
                .setParameter("username", username).getResultList()
                .stream().findFirst();
    }
}
