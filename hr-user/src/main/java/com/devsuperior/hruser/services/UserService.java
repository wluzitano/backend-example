package com.devsuperior.hruser.services;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.repositories.UserRepository;

import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User findUserById(Long id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }
}
