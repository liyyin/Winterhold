package com.Winterhold.dto.loan;

import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class LoanDTO {
    private Long id;
    private String bookName;
    private String bookCode;
    private String customerName;
    private String customerNumber;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LoanDTO(Long id, String bookCode,String bookName, String firstName,String lastName,String customerNumber, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.bookCode = bookCode;
        this.bookName = bookName;
        if (lastName == null){
            this.customerName = String.format("%s",firstName);
        }else {
            this.customerName = String.format("%s %s",firstName,lastName);
        }
        this.customerNumber = customerNumber;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
}
