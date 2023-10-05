package com.Winterhold.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Loan")
public class Loan {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CustomerNumber")
    private String customerNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerNumber", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "BookCode")
    private String bookCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookCode", insertable = false, updatable = false)
    private Book book;

    @Column(name = "LoanDate")
    private LocalDate loanDate;

    @Column(name = "DueDate")
    private LocalDate dueDate;

    @Column(name = "ReturnDate")
    private LocalDate returnDate;

    @Column(name = "Note")
    private String note;

    public Loan(Long id, String customerNumber, String bookCode, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, String note) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.bookCode = bookCode;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.note = note;
    }
}
