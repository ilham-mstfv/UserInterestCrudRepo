package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.UserIp;
import com.example.userinterestcrudrepo.models.requests.UserIpFilterRequest;
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
        StringBuilder query = new StringBuilder("SELECT u FROM UserIp u WHERE 1=1 ");

        if (filter.getUsername() != null) {
            query.append(" AND u.username LIKE '%")
                    .append(filter.getUsername()).append("%'");
        }
        if (filter.getIp() != null) {
            query.append(" AND u.ip = '").append(filter.getIp()).append("'");
        }
        if (filter.getEndpoint() != null) {
            query.append(" AND u.endpoint = '").append(filter.getEndpoint()).append("'");
        }
        if (filter.getRequestMethod() != null) {
            query.append(" AND u.requestMethod = '").append(filter.getRequestMethod()).append("'");
        }
        if (filter.getDateTime() != null) {
            query.append(" AND u.epochSecond >= ")
                    .append(filter.getDateTime().getFrom().toEpochSecond());
            query.append(" AND u.epochSecond <= ")
                    .append(filter.getDateTime().getTo().toEpochSecond());
        }
        return em.createQuery(query.toString(), UserIp.class)
                .getResultList();
    }
}
