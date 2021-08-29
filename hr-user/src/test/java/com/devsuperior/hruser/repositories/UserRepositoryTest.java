package com.devsuperior.hruser.repositories;


import com.devsuperior.hruser.entities.Role;
import com.devsuperior.hruser.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Given valid id should return user from database")
    void getUserById_ShouldReturnUser() {
        Optional<User> user = userRepository.findById(1L);
        assertNotNull(user);
        assertEquals("Operator User", user.get().getName());
    }

    @Test
    @DisplayName("Given valid email should return user from database")
    void getUserByEmail_ShouldReturnUser() {
        Optional<User> user = userRepository.findByEmail("operator@gmail.com");
        assertNotNull(user);
        assertEquals("operator@gmail.com", user.get().getEmail());
        assertEquals("Operator User", user.get().getName());

    }

    @Test
    @DisplayName("Should return all users from database")
    void getAllUsers_ShouldReturnAllUsersFromDatabase() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertEquals("operator@gmail.com", users.get(0).getEmail());
        assertEquals("admin@gmail.com", users.get(1).getEmail());
    }

    @Test
    @DisplayName("Given a valid user should create user in database")
    void shouldSaveUser() {
        User user = CreateMockUser("name", "email@gmail.com", "password");
        User userSaved = userRepository.save(user);
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());

    }

    public User CreateMockUser(String name, String email, String password) {
        User mockedUser = new User();
        mockedUser.setEmail(email);
        mockedUser.setPassword(password);
        mockedUser.setName(name);
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleName("ROLE_OPERATOR");
        roles.add(role);
        mockedUser.setRoles(roles);
        return mockedUser;
    }
}