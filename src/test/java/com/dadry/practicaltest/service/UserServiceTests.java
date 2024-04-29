package com.dadry.practicaltest.service;

import com.dadry.practicaltest.dao.UserDAO;
import com.dadry.practicaltest.models.User;
import com.dadry.practicaltest.payload.request.CreateUserRequest;
import com.dadry.practicaltest.payload.request.FindUsersByDatesRequest;
import com.dadry.practicaltest.payload.request.UpdateUserRequest;
import com.dadry.practicaltest.repositories.UserRepository;
import com.dadry.practicaltest.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserDAO userDAO;

    @Test
    void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setBirthDate("10/02/1990");
        request.setAddress("1234 Main St");
        request.setPhoneNumber("123-456-7890");

        User createdUser = userService.createUser(request);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals("John", createdUser.getFirstName());
        Assertions.assertEquals("Doe", createdUser.getLastName());
        Assertions.assertEquals("john.doe@example.com", createdUser.getEmail());
        Assertions.assertEquals(LocalDate.of(1990, 2, 10), createdUser.getBirthDate());
        Assertions.assertEquals("1234 Main St", createdUser.getAddress());
        Assertions.assertEquals("123-456-7890", createdUser.getPhoneNumber());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 2, 10));
        user.setAddress("1234 Main St");
        user.setPhoneNumber("123-456-7890");

        user = userRepository.save(user);

        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setFirstName("Johnny");
        updateRequest.setLastName("D");
        updateRequest.setEmail("johnny.d@example.com");
        updateRequest.setBirthDate("10/03/1991");
        updateRequest.setAddress("4321 Elm St");
        updateRequest.setPhoneNumber("098-765-4321");

        Mockito.when(userDAO.findById(user.getId())).thenReturn(user);

        User updatedUser = userService.updateUser(user.getId(), updateRequest);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("Johnny", updatedUser.getFirstName());
        Assertions.assertEquals("D", updatedUser.getLastName());
        Assertions.assertEquals("johnny.d@example.com", updatedUser.getEmail());
        Assertions.assertEquals(LocalDate.of(1991, 3, 10), updatedUser.getBirthDate());
        Assertions.assertEquals("4321 Elm St", updatedUser.getAddress());
        Assertions.assertEquals("098-765-4321", updatedUser.getPhoneNumber());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 2, 10));
        user.setAddress("1234 Main St");
        user.setPhoneNumber("123-456-7890");

        user = userRepository.save(user);

        userService.deleteUser(user.getId());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        Assertions.assertTrue(deletedUser.isEmpty());
    }

    @Test
    void testGetUsersByDates() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setBirthDate(LocalDate.of(1990, 2, 10));
        user1.setAddress("1234 Main St");
        user1.setPhoneNumber("123-456-7890");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setBirthDate(LocalDate.of(1995, 3, 15));
        user2.setAddress("4321 Elm St");
        user2.setPhoneNumber("987-654-3210");
        userRepository.save(user2);

        FindUsersByDatesRequest request = new FindUsersByDatesRequest();
        request.setFromDate(LocalDate.of(1990, 1, 1));
        request.setToDate(LocalDate.of(1995, 12, 31));

        Mockito.when(userDAO.findUsersByDates(request.getFromDate(), request.getToDate()))
                .thenReturn(List.of(user1, user2));

        List<User> users = userService.getUsersByDates(request);

        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.size());
    }
}

