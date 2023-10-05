package com.Winterhold.dto.customer;

import com.Winterhold.validation.customer.PhoneNumber;
import com.Winterhold.validation.customer.UniqueMembership;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CreateCustomerDTO {
    @NotBlank
    @UniqueMembership(message = "membership already exist")
    private String membershipNumber;
    @NotBlank
    private String firstName;
    private String lastName;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Not Allowed past current Date")
    private LocalDate birthDate;
    @NotBlank
    private String gender;
    @PhoneNumber(message = "ga osah aneh-aneh")
    private String phone;
    private String address;
}
