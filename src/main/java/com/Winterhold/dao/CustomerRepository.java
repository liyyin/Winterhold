package com.Winterhold.dao;

import com.Winterhold.dto.category.CategoryDTO;
import com.Winterhold.dto.customer.CustomerDTO;
import com.Winterhold.dto.customer.CustomerDetailDTO;
import com.Winterhold.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Query("""
            SELECT new com.Winterhold.dto.customer.CustomerDTO(
                cus.membershipNumber,
                cus.firstName,
                cus.lastName,
                cus.memberShipExpireDate,
                cus.phone
            )
            FROM Customer as cus
            WHERE cus.membershipNumber LIKE %:member% AND 
                    (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%) AND
                    (( cus.memberShipExpireDate < GETDATE() AND :expired = 'on') OR                   
                    ( cus.memberShipExpireDate > GETDATE() AND :expired = 'off')) AND (cus.isDelete = false)
            """)
    public List<CustomerDTO> getList(Pageable pagging, @Param("member") String memberShip, @Param("name") String searchName, @Param("expired")String expired);

    @Query("""
            SELECT Count(1)
            FROM Customer as cus
            WHERE cus.membershipNumber LIKE %:member% AND 
                    (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%) AND
                    (( cus.memberShipExpireDate < GETDATE() AND :expired = 'on') OR
                        ( cus.memberShipExpireDate > GETDATE() AND :expired = 'off')) AND (cus.isDelete = false)
            """)
    public Long getCount(@Param("member") String memberShip, @Param("name") String searchName,@Param("expired")String expired);

    @Query("""
            SELECT new com.Winterhold.dto.customer.CustomerDTO(
                cus
            )
            FROM Customer AS cus
            WHERE cus.membershipNumber = :member
            """)
    public CustomerDTO getCustomer(@Param("member") String customerNumber);

    @Query("""
            SELECT new com.Winterhold.dto.customer.CustomerDetailDTO(
                cus
            )
            FROM Customer AS cus
            WHERE cus.membershipNumber = :member
            """)
    public CustomerDetailDTO getCustomerDetail(@Param("member") String membership);

    @Query("""
            SELECT COUNT(1)
            FROM Customer AS cus
            WHERE cus.membershipNumber = :member
            """)
    public Long  uniqueMembership(@Param("member") String membership);
}
