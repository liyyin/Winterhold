package com.Winterhold.dto.category;

import com.Winterhold.validation.category.UniqueCategoryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCategoryDTO {
    @NotBlank
    private String categoryName;
    @NotNull
    private Integer floor;
    @NotBlank
    private String isle;
    @NotBlank
    private String bay;
}
