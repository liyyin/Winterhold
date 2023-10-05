package com.Winterhold.dto.customer;

import com.Winterhold.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private String memberShip;
    private String fullName;
    private LocalDate expireDate;
    private String phone;


    public CustomerDTO(String memberShip, String firstName,String lastName, LocalDate expireDate,String phone) {
        this.memberShip = memberShip;
        if (lastName == null){
            this.fullName = String.format("%s",firstName);
        }else {
            this.fullName = String.format("%s %s",firstName,lastName);
        }
        this.expireDate = expireDate;
        this.phone = phone;
    }

    public CustomerDTO(Customer cus) {
        this.memberShip = cus.getMembershipNumber();
        if (cus.getLastName() == null){
            this.fullName = String.format("%s",cus.getFirstName());
        }else {
            this.fullName = String.format("%s %s",cus.getFirstName(),cus.getLastName());
        }
        this.expireDate = cus.getMemberShipExpireDate();
        this.phone = cus.getPhone();
    }
}
