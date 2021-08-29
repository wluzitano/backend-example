package com.devsuperior.hruser.controllers;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.services.UserService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("Given a valid id response should return user")
    public void shouldReturnUserById() throws NotFoundException {
        when(userService.findUserById(anyLong())).thenReturn(returnMockUser(1L, "User1", "user1@mail.com"));
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());

    }

    @Test
    @DisplayName("Given a valid email response should return user and ok status 200")
    public void shouldReturnUserByEmail() throws NotFoundException {
        when(userService.findUserByEmail(anyString())).thenReturn(returnMockUser(1L, "User1", "user1@mail.com"));
        ResponseEntity<User> response = userController.findByEmail("user1@mail.com");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("user1@mail.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Given a invalid email response should return no content status 204")
    public void whenFindUserWithInvalidEmail_ShouldResponseNoContent() {
        ResponseEntity<User> response = userController.findByEmail(anyString());
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Given an invalid id response should return not found 404")
    public void whenFindUserByInvalidId_ShouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> {
                    ResponseEntity<User> response = userController.findById(1L);
                });
    }

    @Test
    @DisplayName("Given a valid user response should return ok")
    public void whenCreateUser_ShouldResponseCreated() throws SQLIntegrityConstraintViolationException {
        ResponseEntity<User> response = userController.createUser(new User("name", "email@email.com", "password"));
        assertEquals(200, response.getStatusCodeValue());
    }

    public User returnMockUser(Long id, String name, String email) {
        User mockedUser = new User();
        mockedUser.setId(id);
        mockedUser.setEmail(email);
        mockedUser.setPassword("userpass");
        mockedUser.setName(name);
        return mockedUser;
    }

    public List<User> returnMockUsers() {
        List<User> users = new ArrayList<>();
        users.add(returnMockUser(1L, "User1", "user1@mail.com"));
        users.add(returnMockUser(2L, "User2", "user2@mail.com"));
        return users;
    }

}