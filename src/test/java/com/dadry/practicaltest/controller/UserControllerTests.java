package com.dadry.practicaltest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.dadry.practicaltest.controllers.UserController;
import com.dadry.practicaltest.models.User;
import com.dadry.practicaltest.payload.request.CreateUserRequest;
import com.dadry.practicaltest.payload.request.FindUsersByDatesRequest;
import com.dadry.practicaltest.payload.request.UpdateUserRequest;
import com.dadry.practicaltest.payload.response.MessageResponse;
import com.dadry.practicaltest.services.UserService;
import com.dadry.practicaltest.validators.ResponseErrorValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Arrays;
import java.time.LocalDate;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ResponseErrorValidator responseErrorValidator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnSuccess() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setBirthDate("10/02/1990");
        request.setAddress("1234 Main St");
        request.setPhoneNumber("123-456-7890");

        when(responseErrorValidator.mapValidationService(any(BindingResult.class))).thenReturn(null);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created successfully!"));
    }

    @Test
    void createUser_ShouldReturnValidationError() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("");
        request.setLastName("");
        request.setEmail("someWrongEmail");
        request.setBirthDate("10/02/1990");
        request.setAddress("1234 Main St");
        request.setPhoneNumber("123-456-7890");

        when(responseErrorValidator.mapValidationService(any(BindingResult.class)))
                .thenReturn(new ResponseEntity<>(new MessageResponse("Validation error"), HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"));
    }

    @Test
    void updateUser_ShouldReturnSuccess() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();

        request.setFirstName("Johnny");
        request.setLastName("D");
        request.setEmail("johnny.d@example.com");
        request.setBirthDate("10/03/1991");
        request.setAddress("4321 Elm St");
        request.setPhoneNumber("098-765-4321");

        when(responseErrorValidator.mapValidationService(any(BindingResult.class))).thenReturn(null);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully!"));
    }

    @Test
    void deleteUser_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully!"));
    }

    @Test
    void getUsersByDates_ShouldReturnUsers() throws Exception {
        FindUsersByDatesRequest request = new FindUsersByDatesRequest();
        request.setFromDate(LocalDate.of(2000, 1, 1));
        request.setToDate(LocalDate.of(2000, 1, 5));

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 2, 10));
        user.setAddress("1234 Main St");
        user.setPhoneNumber("123-456-7890");

        when(userService.getUsersByDates(any(FindUsersByDatesRequest.class)))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(post("/users/_dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void getUsersByDates_ShouldReturnValidationError() throws Exception {
        FindUsersByDatesRequest request = new FindUsersByDatesRequest();
        request.setFromDate(LocalDate.now());
        request.setToDate(LocalDate.now().minusDays(10));

        mockMvc.perform(post("/users/_dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("IllegalArgumentException"))
                .andExpect(jsonPath("$.message").value("From Date should be before To Date!"));
    }

    private static String asJsonString(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

