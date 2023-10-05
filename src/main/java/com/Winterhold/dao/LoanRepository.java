package com.Winterhold.dao;

import com.Winterhold.dto.DropdownListDTO;
import com.Winterhold.dto.customer.CustomerDTO;
import com.Winterhold.dto.loan.LoanDTO;
import com.Winterhold.entity.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {
    @Query("""
            SELECT new com.Winterhold.dto.loan.LoanDTO(
                lo.id,
                bo.code,
                bo.title,
                cus.firstName,
                cus.lastName,
                cus.membershipNumber,
                lo.loanDate,
                lo.dueDate,
                lo.returnDate
            )
            FROM Loan as lo
            INNER JOIN lo.customer as cus
            INNER JOIN lo.book as bo
            WHERE (bo.title LIKE %:bookName% AND 
                    (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%))
                    
            """)
    public List<LoanDTO> getList(Pageable pagging, @Param("bookName") String memberShip, @Param("name") String customerName);

    @Query("""
            SELECT new com.Winterhold.dto.loan.LoanDTO(
                lo.id,
                bo.code,
                bo.title,
                cus.firstName,
                cus.lastName,
                cus.membershipNumber,
                lo.loanDate,
                lo.dueDate,
                lo.returnDate
            )
            FROM Loan as lo
            INNER JOIN lo.customer as cus
            INNER JOIN lo.book as bo
            WHERE (bo.title LIKE %:bookName% AND 
                    (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%)) AND
                    (lo.returnDate IS NULL AND lo.dueDate < GETDATE() AND (lo.loanDate > GETDATE()OR lo.loanDate<GETDATE()))
                    
            """)
    public List<LoanDTO> getListByDueDate(Pageable pagging, @Param("bookName") String memberShip, @Param("name") String customerName);

    @Query("""
            SELECT COUNT(1)
            FROM Loan as lo
            INNER JOIN lo.customer as cus
            INNER JOIN lo.book as bo
            WHERE bo.title LIKE %:bookName% AND 
                    (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%)
            """)
    public Long getCount(@Param("bookName") String memberShip, @Param("name") String customerName);

    @Query("""
            SELECT COUNT(1)
            FROM Loan as lo
            INNER JOIN lo.customer as cus
            INNER JOIN lo.book as bo
            WHERE bo.title LIKE %:bookName% AND 
                    (cus.firstName LIKE %:name% OR cus.lastName LIKE %:name%) AND
                    (lo.returnDate IS NULL AND lo.dueDate < GETDATE() AND (lo.loanDate > GETDATE()OR lo.loanDate<GETDATE()))
            """)
    public Long getCountByDueDate(@Param("bookName") String memberShip, @Param("name") String customerName);

    @Query("""
            SELECT new com.Winterhold.dto.DropdownListDTO(
                cus.membershipNumber,
                cus.firstName,
                cus.lastName
            )
            FROM Customer as cus
            WHERE cus.memberShipExpireDate > GETDATE()
            """)
    public List<DropdownListDTO> getCustomerDDL();

    @Query("""
            SELECT new com.Winterhold.dto.DropdownListDTO(
                bo.code,
                bo.title
            )
            FROM Book as bo
            WHERE bo.isBorrowed =false
            """)
    public List<DropdownListDTO> getBookDDL();

    @Query("""
            SELECT COUNT(1) 
            FROM Loan AS lo
            WHERE lo.bookCode = :bookCode AND lo.loanDate != GETDATE() AND lo.returnDate IS NULL 
            """)
    public Long countLoanByBookCode(@Param("bookCode") String bookCode);

    @Query("""
            SELECT lo.loanDate
            FROM Loan as lo
            WHERE lo.bookCode = :bookCode AND lo.returnDate IS NULL
            """)
    public List<LocalDate> getListLoanByBookCode(@Param("bookCode") String bookCode);
}
