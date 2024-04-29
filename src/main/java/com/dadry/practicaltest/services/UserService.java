package com.dadry.practicaltest.services;

import com.dadry.practicaltest.dao.UserDAO;
import com.dadry.practicaltest.models.User;
import com.dadry.practicaltest.payload.request.CreateUserRequest;
import com.dadry.practicaltest.payload.request.FindUsersByDatesRequest;
import com.dadry.practicaltest.payload.request.UpdateUserRequest;
import com.dadry.practicaltest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final UserRepository userRepository;
    private static final String DATE_FORMAT = "d/MM/yyyy";

    public User createUser(CreateUserRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate userBirthDate = LocalDate.parse(request.getBirthDate(), formatter);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .birthDate(userBirthDate)
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();

        return userRepository.save(user);
    }

    public User updateUser(Long id, UpdateUserRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate userBirthDate = LocalDate.parse(request.getBirthDate(), formatter);
        User user = userDAO.findById(id);

        if (user != null) {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setBirthDate(userBirthDate);
            user.setAddress(request.getAddress());
            user.setPhoneNumber(request.getPhoneNumber());

            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public User updateUserFields(Long id, UpdateUserRequest request) {
        LocalDate userBirthDate = null;
        if (request.getBirthDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            userBirthDate = LocalDate.parse(request.getBirthDate(), formatter);
        }
        User user = userDAO.findById(id);

        if (user != null) {
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLastName(request.getLastName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getAddress() != null) {
                user.setAddress(request.getAddress());
            }
            if (request.getPhoneNumber() != null) {
                user.setPhoneNumber(request.getPhoneNumber());
            }
            if (userBirthDate != null) {
                user.setBirthDate(userBirthDate);
            }

            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    public List<User> getUsersByDates(FindUsersByDatesRequest request) {
        if (Objects.isNull(request.getToDate())) {
            request.setToDate(request.getFromDate());
        }

        return userDAO.findUsersByDates(request.getFromDate(), request.getToDate());
    }

}
