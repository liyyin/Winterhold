package com.Winterhold.dto.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class BookDTO {
    private String bookCode;
    private String categoryName;
    private String bookTitle;
    private String authorName;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;

    public BookDTO(String bookCode, String bookTitle, String title, String firstName,String lastName, Boolean isBorrowed, LocalDate releaseDate, Integer totalPage, String summary) {
        this.bookCode = bookCode;
        this.bookTitle = bookTitle;
        if (lastName == null){
            this.authorName = String.format("%s. %s",title,firstName);
        }else {
            this.authorName = String.format("%s. %s %s",title,firstName,lastName);
        }
        this.isBorrowed = isBorrowed;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
        this.summary = summary;
    }
    public BookDTO(String bookCode,String categoryName,String bookTitle, Boolean isBorrowed, LocalDate releaseDate, Integer totalPage) {
        this.bookCode = bookCode;
        this.categoryName = categoryName;
        this.bookTitle = bookTitle;
        this.isBorrowed = isBorrowed;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}
