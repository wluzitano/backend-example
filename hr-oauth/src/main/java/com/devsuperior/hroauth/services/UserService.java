package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feignclients.UserFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userFeignClient.findByEmail(userName).getBody();
            logger.info("Email found: ".concat(userName));
            return user;
        } catch (FeignException.NotFound e) {
            logger.error("Email not found: ".concat(userName));
            throw new UsernameNotFoundException("Email not found");
        } catch (Exception e) {
            logger.error("Eureka host  is unavailable(reason: {})", e.getMessage());
            throw new IllegalStateException();
        }
    }
}
