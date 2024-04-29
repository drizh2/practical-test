package com.dadry.practicaltest.controllers;

import com.dadry.practicaltest.models.User;
import com.dadry.practicaltest.payload.request.CreateUserRequest;
import com.dadry.practicaltest.payload.request.FindUsersByDatesRequest;
import com.dadry.practicaltest.payload.request.UpdateUserRequest;
import com.dadry.practicaltest.payload.response.ErrorResponse;
import com.dadry.practicaltest.payload.response.MessageResponse;
import com.dadry.practicaltest.services.UserService;
import com.dadry.practicaltest.validators.ResponseErrorValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidator responseErrorValidator;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserRequest request,
                                     BindingResult bindingResult) {
        ResponseEntity<Object> errorMap = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errorMap)) return errorMap;

        userService.createUser(request);

        return new ResponseEntity<>(new MessageResponse("User created successfully!"), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId,
                                             @Valid @RequestBody UpdateUserRequest request,
                                             BindingResult bindingResult) {
        ResponseEntity<Object> errorMap = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errorMap)) return errorMap;

        userService.updateUser(userId, request);
        return new ResponseEntity<>(new MessageResponse("User updated successfully!"), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUserFields(@PathVariable Long userId,
                                             @Valid @RequestBody UpdateUserRequest request,
                                             BindingResult bindingResult) {
        ResponseEntity<Object> errorMap = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errorMap)) return errorMap;

        userService.updateUserFields(userId, request);
        return new ResponseEntity<>(new MessageResponse("User updated successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @PostMapping("/_dates")
    public ResponseEntity<Object> getUsersByDates(@Valid @RequestBody FindUsersByDatesRequest request,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException("From Date should be before To Date!");

        List<User> users = userService.getUsersByDates(request);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequestException(IllegalArgumentException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
