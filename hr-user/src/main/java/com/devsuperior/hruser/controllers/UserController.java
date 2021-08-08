package com.devsuperior.hruser.controllers;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findId(@PathVariable Long id) {
        logger.info("Calling findUserById on userService for id: " + id);
        return Optional.ofNullable(userService.findUserById(id))
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        logger.info("Calling findUserByEmail on userService for email: " + email);
        return Optional.ofNullable(userService.findUserByEmail(email))
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
