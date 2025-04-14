package com.example.userinterestcrudrepo.repository;

import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.requests.UserFilterRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public void insertUser(User user) {
        entityManager.persist(user);
    }

    public List<User> getUsersByFilter(UserFilterRequest filter) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getPersonalData() != null) {
            predicates.add(
                    builder.equal(userRoot.get("personalData"), filter.getPersonalData()));
        }
        if (filter.getAge() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(userRoot.get("age"), filter.getAge().getFrom()));
            predicates.add(
                    builder.lessThanOrEqualTo(userRoot.get("age"), filter.getAge().getTo()));
        }
        if (filter.getCountry() != null) {
            predicates.add(
                    builder.equal(userRoot.get("country"), filter.getCountry()));
        }
        if (filter.getLang() != null) {
            predicates.add(
                    builder.equal(userRoot.get("lang"), filter.getLang()));
        }
        if (filter.getBalance() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(userRoot.get("balance"), filter.getBalance().getFrom()));
            predicates.add(
                    builder.lessThanOrEqualTo(userRoot.get("balance"), filter.getBalance().getTo()));
        }
        criteria.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteria).getResultList();
    }
}
