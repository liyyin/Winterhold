package com.Winterhold.service.absract;

import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.category.CategoryDTO;
import com.Winterhold.dto.category.CreateCategoryDTO;
import com.Winterhold.dto.category.UpdateCategoryDTO;
import com.Winterhold.entity.Category;

import java.util.List;

public interface CategoryService {
    public AllDataDTO getAllData(Integer page, String searchName);
    public List<CategoryDTO> getList(Integer page, String searchName);
    public Long getCount(String searchName);
    public Long getPageSize(String searchName);

    public List<CategoryDTO> getAllDataCategory(String searchName);
    public void save(CreateCategoryDTO dto);
    public void update(UpdateCategoryDTO dto);
    public CreateCategoryDTO getDataCategory(String name);
    public JsonResultDTO delete(String categoryName);

    public JsonResultDTO saveRest(CreateCategoryDTO dto);
    public JsonResultDTO updateRest(UpdateCategoryDTO dto);
    public JsonResultDTO updateBay(String categoryName,String bay);

    public Boolean uniqueCategoryName(String categoryName);
}
