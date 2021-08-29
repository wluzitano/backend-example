package com.devsuperior.hruser.services;

import com.devsuperior.hruser.entities.User;
import com.devsuperior.hruser.repositories.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Given a valid email should return user")
    public void whenFindUserByEmailWithAnExistingEmail_ShouldReturnUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(createMockUser(1L, "User1", "user1@mail.com"));
        User user = userService.findUserByEmail("user1@mail.com");
        assertNotNull(user);
        assertEquals("user1@mail.com", user.getEmail());
    }

    @Test
    @DisplayName("Given a valid id should return user")
    public void whenFindUserWithValidId_ShouldReturnUser() throws NotFoundException {
        when(userRepository.findById(anyLong())).thenReturn(createMockUser(1L, "User1", "user1@mail.com"));
        User user = userService.findUserById(1L);
        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    @DisplayName("when find all users should return users")
    public void whenFindAll_shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(createListOfMockUsers());
        List<User> users = userService.findAllUsers();
        assertNotNull(users);
        assertEquals(1L, users.get(0).getId());
        assertEquals("user1@mail.com", users.get(0).getEmail());
        assertEquals("User1", users.get(0).getName());
    }

    @Test
    @DisplayName("Given a invalid id should throw exception")
    public void whenFindUserByInvalidId_ShouldThrowNullPointerExceptionR() {
        assertThrows(NoSuchElementException.class,
                () -> {
                    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
                    userService.findUserById(1L);
                });
    }

    @Test
    @DisplayName("Given a invalid email should return no content")
    public void whenFindUserWithInvalidEmail_ShouldThrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> {
                    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
                    User user = userService.findUserByEmail(anyString());
                });
    }

    @Test
    @DisplayName("Given a valid user should create user")
    public void createUser_withValidParameters_shouldReturnUser() throws SQLIntegrityConstraintViolationException {

        User user = new User("name", "mail@gmail.com", "password");
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }

    public Optional<User> createMockUser(Long id, String name, String email) {
        User mockedUser = new User();
        mockedUser.setId(id);
        mockedUser.setEmail(email);
        mockedUser.setPassword("userpass");
        mockedUser.setName(name);
        return Optional.of(mockedUser);
    }

    public List<User> createListOfMockUsers() {
        List<User> users = new ArrayList<>();
        users.add(createMockUser(1L, "User1", "user1@mail.com").get());
        users.add(createMockUser(2L, "User2", "user2@mail.com").get());
        return users;
    }

}