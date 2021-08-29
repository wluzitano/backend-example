package com.devsuperior.hruser.controllers;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.services.UserService;
import javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    @DisplayName("Returns user by id")
    public void shouldReturnUserById () throws NotFoundException {
        when(userService.findUserById(anyLong())).thenReturn(returnMockUser(1L, "User1", "user1@mail.com"));
        ResponseEntity<User> response = userController.findId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());

    }

    @Test
    @DisplayName("Returns user by email")
    public void shouldReturnUserByEmail () throws NotFoundException {
        when(userService.findUserByEmail(anyString())).thenReturn(returnMockUser(1L, "User1", "user1@mail.com"));
        ResponseEntity<User> response = userController.findByEmail("user1@mail.com");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("user1@mail.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Returns no content when Email does not exist")
    public void whenFindUserWithInvalidEmail_ShouldResponseNoContent() {
        ResponseEntity<User> response = userController.findByEmail(anyString());
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Throw Not found when User Id does not exist")
    public void whenFindUserByInvalidId_ShouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
                () -> {
                    when(userService.findUserById(anyLong())).thenReturn(null);
                    ResponseEntity<User> response = userController.findId(anyLong());
                });
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