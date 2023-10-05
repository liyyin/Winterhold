package com.Winterhold.dto.customer;

import com.Winterhold.entity.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter@Setter
public class CustomerDetailDTO {
    private String membership;
    private String fullName;
    @DateTimeFormat(pattern = "yyyy/MMMM/dd")
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;

    public CustomerDetailDTO(Customer cus) {
        this.membership = cus.getMembershipNumber();
        if (cus.getLastName() == null){
            this.fullName = String.format("%s",cus.getFirstName());
        }else {
            this.fullName = String.format("%s %s",cus.getFirstName(),cus.getLastName());
        }
        this.birthDate = cus.getBirthDate();
        this.gender = cus.getGender();
        this.phone = cus.getPhone();
        this.address = cus.getAddress();
    }
}
