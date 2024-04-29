package com.dadry.practicaltest.dao;

import com.dadry.practicaltest.models.User;
import com.dadry.practicaltest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAO {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public List<User> findUsersByDates(LocalDate from, LocalDate to) {
        return userRepository.findAllByBirthDateBetween(from, to);
    }

}
