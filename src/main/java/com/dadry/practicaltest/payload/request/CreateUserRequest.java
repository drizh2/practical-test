package com.dadry.practicaltest.payload.request;

import com.dadry.practicaltest.annotations.ValidBirthDate;
import com.dadry.practicaltest.annotations.ValidEmail;
import com.dadry.practicaltest.annotations.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class CreateUserRequest {
    @NonNull
    @NotBlank(message = "First name should not be blank")
    private String firstName;
    @NonNull
    @NotBlank(message = "Last name should not be blank")
    private String lastName;
    @NonNull
    @ValidEmail
    private String email;
    @NonNull
    @ValidBirthDate
    private String birthDate;
    @NotBlank(message = "Last name should not be blank")
    private String address;
    @ValidPhoneNumber
    private String phoneNumber;
}
