package com.Winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private String name;
    private Integer floor;
    private String isle;
    private String bay;
    private Long totalBooks;
}
