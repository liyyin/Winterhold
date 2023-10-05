package com.Winterhold.dto.loan;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUpdateLoanDTO {
    private Long id;
    @NotBlank
    @NotNull
    private String customerId;
    @NotBlank
    @NotNull
    private String bookCode;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "ga boleh tanggal kemarin")
    private LocalDate loanDate;
    private String note;
}
