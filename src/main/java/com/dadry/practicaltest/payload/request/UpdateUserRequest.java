package com.dadry.practicaltest.payload.request;

import com.dadry.practicaltest.annotations.ValidBirthDate;
import com.dadry.practicaltest.annotations.ValidEmail;
import com.dadry.practicaltest.annotations.ValidPhoneNumber;
import lombok.Data;
import lombok.NonNull;

@Data
@NonNull
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    @ValidEmail
    private String email;
    @ValidBirthDate
    private String birthDate;
    private String address;
    @ValidPhoneNumber
    private String phoneNumber;
}
