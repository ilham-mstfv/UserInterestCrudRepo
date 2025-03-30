package com.example.userinterestcrudrepo.repository;

import com.example.userinterestcrudrepo.entities.UserAcc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public UserAcc getUserAccByUsername(String username) {
        List<UserAcc> results = entityManager.createQuery("SELECT u FROM UserAcc u WHERE u.username = :username", UserAcc.class)
                .setParameter("username", username)
                .getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
