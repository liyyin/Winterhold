package com.Winterhold.dto.author;

import com.Winterhold.validation.author.CheckDeceasedDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@CheckDeceasedDate(birthDateField = "birthDate",deceasedDateField = "deceasedDate",message = "No body die before they born")
public class CreateUpdateAuthorDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String firstName;
    private String lastName;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Not Allowed past current Date")
    private LocalDate birthDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deceasedDate;
    private String education;
    private String summary;
}
