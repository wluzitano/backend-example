package com.devsuperior.hruser.controllers;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.services.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping()
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Retrieving all users");
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) throws NotFoundException {
        logger.info("Calling findUserById on userService for id: " + id);
        return Optional.ofNullable(userService.findUserById(id))
                .map(user -> ResponseEntity.ok(user))
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
    }

    @GetMapping(value = "users/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        logger.info("Calling findUserByEmail on userService for {email}", email);
        return Optional.ofNullable(userService.findUserByEmail(email))
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws SQLIntegrityConstraintViolationException {
        logger.info("Saving user {}: ", user);
        User userCreated = userService.saveUser(user);
        return ResponseEntity.ok(userCreated);
    }

}
