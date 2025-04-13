package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.UserIp;
import com.example.userinterestcrudrepo.models.UserIpFilterRequest;
import com.example.userinterestcrudrepo.repository.UserIpJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserIpService {

    @PersistenceContext
    private EntityManager em;

    private final UserIpJpaRepository userIpJpaRepository;

    public UserIpService(UserIpJpaRepository userIpJpaRepository) {
        this.userIpJpaRepository = userIpJpaRepository;
    }


    public void createAndInsertUserIpByRequest(HttpServletRequest request, String username) {
        userIpJpaRepository.save(new UserIp(
                username,
                request.getRemoteAddr(),
                request.getRequestURL().toString(),
                request.getMethod(),
                ZonedDateTime.now()
        ));
    }

    public List<UserIp> getAll() {
        return userIpJpaRepository.findAll();
    }

    public List<UserIp> getUserIpListByFilter (UserIpFilterRequest filter) {
        StringBuilder query = new StringBuilder("SELECT u FROM UserIp u WHERE ");
        if (filter != null) {
            if (filter.getZonedDateTime() != null) {
                query.append(" u.epochSecond >= ")
                        .append(filter.getZonedDateTime().getFrom().toEpochSecond());
                query.append(" AND u.epochSecond <= ")
                        .append(filter.getZonedDateTime().getTo().toEpochSecond());
            }
        }
        return em.createQuery(query.toString(), UserIp.class)
                .getResultList();
    }
}
