package com.Winterhold.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AllDataDTO {
    private List<Object> listData;
    private Long currentPage;
    private Long totalPage;
    private String categoryName;
    private String searchName;
    private String searchOther;
    private String isTrue;

    public AllDataDTO(List<Object> listData, Long currentPage, Long totalPage, String searchName, String searchOther, String isTrue) {
        this.listData = listData;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.searchName = searchName;
        this.searchOther = searchOther;
        this.isTrue = isTrue;
    }
}
