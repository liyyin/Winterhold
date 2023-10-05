package com.Winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "Code")
    private String code;

    @Column(name = "Title")
    private String title;

    @Column(name = "CategoryName")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryName", insertable = false, updatable = false)
    private Category category;

    @Column(name = "AuthorId")
    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuthorId", insertable = false, updatable = false)
    private Author author;

    @Column(name = "IsBorrowed")
    private Boolean isBorrowed;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;

    @Column(name = "TotalPage")
    private Integer totalPage;

    @Column(name = "IsReturn")
    private Boolean isReturn;

    public Book(String code, String title, String categoryName, Long authorId, Boolean isBorrowed, String summary, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.isBorrowed = isBorrowed;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}
