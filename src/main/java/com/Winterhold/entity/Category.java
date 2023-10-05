package com.Winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> book;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Isle")
    private String isle;

    @Column(name="Bay")
    private String bay;

    @Column(name = "IsDelete")
    private Boolean isDelete;

    public Category(String name, Integer floor, String isle, String bay) {
        this.name = name;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }
}
