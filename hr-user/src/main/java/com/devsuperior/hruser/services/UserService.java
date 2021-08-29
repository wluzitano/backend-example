package com.devsuperior.hruser.services;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.repositories.UserRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Transactional
@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("This user is already registered");
        }
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
