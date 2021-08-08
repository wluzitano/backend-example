package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclients.UserFeignClient;
import com.netflix.client.ClientException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        try {
            User user = userFeignClient.findByEmail(email).getBody();
            logger.info("Email found: ".concat(email));
            return user;
        } catch (FeignException.NotFound e) {
            logger.error("Email not found: ".concat(email));
            throw new IllegalArgumentException();
        } catch (Exception e) {
            logger.error("Eureka host  is unavailable(reason: {})", e.getMessage());
            throw new IllegalStateException();
        }
    }

}
