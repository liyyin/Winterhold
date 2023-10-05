package com.Winterhold.dto.book;

import com.Winterhold.validation.book.UniqueBookCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBookDTO {
    @NotBlank
    private String code;
    @NotBlank
    private String title;
    @NotNull
    private String categoryName;
    @NotNull
    private Long author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;
}
