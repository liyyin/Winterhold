package com.Winterhold.dto.book;

import com.Winterhold.entity.Author;
import com.Winterhold.entity.Book;
import com.Winterhold.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BookDetailDTO {
    private String title;
    private String category;
    private String author;
    private Integer floor;
    private String isle;
    private String bay;

    public BookDetailDTO(Book book, Category cat, Author author) {
        this.title = book.getTitle();
        this.category = book.getCategoryName();
        if (author.getLastName() == null){
            this.author = String.format("%s. %s",author.getTitle(),author.getFirstName());
        }else {
            this.author = String.format("%s. %s %s",author.getTitle(),author.getFirstName(),author.getLastName());
        }
        this.floor = cat.getFloor();
        this.isle = cat.getIsle();
        this.bay = cat.getBay();
    }

    public BookDetailDTO(String title, String category) {
        this.title = title;
        this.category = category;
    }
}
