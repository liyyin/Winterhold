package com.Winterhold.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "MembershipNumber")
    private String membershipNumber;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Phone")
    private String phone;

    @Column(name="Address")
    private String address;

    @Column(name = "MemberShipExpireDate")
    private LocalDate memberShipExpireDate;

    @Column(name = "IsDelete")
    private Boolean isDelete;

    public Customer(String membershipNumber, String firstName, String lastName, LocalDate birthDate, String gender, String phone, String address, LocalDate memberShipExpireDate) {
        this.membershipNumber = membershipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.memberShipExpireDate = memberShipExpireDate;
    }
}
