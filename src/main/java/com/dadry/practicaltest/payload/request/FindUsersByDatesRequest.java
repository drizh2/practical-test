package com.dadry.practicaltest.payload.request;

import com.dadry.practicaltest.annotations.FromBeforeTo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@FromBeforeTo
public class FindUsersByDatesRequest {
    @NotNull
    private LocalDate fromDate;
    private LocalDate toDate;
}
