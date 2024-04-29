package com.dadry.practicaltest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}