package com.Winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Author")
public class Author {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> book;

    @Column(name = "Title")
    private String title;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name ="DeceasedDate")
    private LocalDate deceasedDate;

    @Column(name = "Education")
    private String education;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "IsRemove")
    private Boolean isRemove;

    public Author(Long id, String title, String firstName, String lastName, LocalDate birthDate, LocalDate deceasedDate, String education, String summary) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
        this.education = education;
        this.summary = summary;
    }
}
