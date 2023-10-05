package com.Winterhold.service.implementation;

import com.Winterhold.dao.CategoryRepository;
import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.category.CategoryDTO;
import com.Winterhold.dto.category.CreateCategoryDTO;
import com.Winterhold.dto.category.UpdateCategoryDTO;
import com.Winterhold.entity.Category;
import com.Winterhold.service.absract.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImplementation implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public AllDataDTO getAllData(Integer page, String searchName) {
        var data = getList(page,searchName);
        List<Object> data2 = new ArrayList<>();
        data2.addAll(data);
        var currentPage = (long)page;
        var totalPage = getPageSize(searchName);
        var categoryData = new AllDataDTO(data2,currentPage,totalPage,searchName,null,null);
        return categoryData;
    }

    @Override
    public List<CategoryDTO> getList(Integer page, String searchName) {
        Pageable pagging = PageRequest.of(page-1,10, Sort.by("id"));
        return categoryRepository.getList(pagging,searchName);
    }

    @Override
    public Long getCount(String searchName) {
        return categoryRepository.getCount(searchName);
    }

    @Override
    public Long getPageSize(String searchName) {
        var totalData = (double)getCount(searchName);
        var totalPage = (long)Math.ceil(totalData/10);
        return totalPage ;
    }

    @Override
    public List<CategoryDTO> getAllDataCategory(String searchName) {
        var data = categoryRepository.getAllDataCategory(searchName);
        return data.stream().map(a -> new CategoryDTO(a.getName(),a.getFloor(),a.getIsle(),a.getBay(),0L)).toList();
    }

    @Override
    public void save(CreateCategoryDTO dto) {
        var entity=new Category();
        entity.setName(dto.getCategoryName());
        entity.setFloor(dto.getFloor());
        entity.setIsle(dto.getIsle());
        entity.setBay(dto.getBay());
        entity.setIsDelete(false);
        categoryRepository.save(entity);
    }
    @Override
    public void update(UpdateCategoryDTO dto) {
        var entity= categoryRepository.getReferenceById(dto.getCategoryName());
        entity.setName(dto.getCategoryName());
        entity.setFloor(dto.getFloor());
        entity.setIsle(dto.getIsle());
        entity.setBay(dto.getBay());
        categoryRepository.save(entity);
    }

    @Override
    public CreateCategoryDTO getDataCategory(String name) {
        var data = categoryRepository.getReferenceById(name);
        var dto = new CreateCategoryDTO(data.getName(),data.getFloor(),data.getIsle(),data.getBay());
        return dto;
    }

    @Override
    public JsonResultDTO delete(String categoryName) {
        var category = categoryRepository.getReferenceById(categoryName);
        try{
            if (category != null){
                    category.setIsDelete(true);
                    categoryRepository.save(category);
                    return new JsonResultDTO(true,String.format("category %s telah di delete",categoryName));
            }else {
                return new JsonResultDTO(false,String.format("category %s tidak ditemukan",categoryName));
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }

    @Override
    public JsonResultDTO saveRest(CreateCategoryDTO dto) {
        var entity=new Category();
        entity.setName(dto.getCategoryName());
        entity.setFloor(dto.getFloor());
        entity.setIsle(dto.getIsle());
        entity.setBay(dto.getBay());
        entity.setIsDelete(false);
        entity=categoryRepository.save(entity);
        return new JsonResultDTO("berhasil insert",true,entity);
    }

    @Override
    public JsonResultDTO updateRest(UpdateCategoryDTO dto) {
        try {
            Category category = categoryRepository.getReferenceById(dto.getCategoryName());
            if (category !=null){
                category.setName(dto.getCategoryName());
                category.setFloor(dto.getFloor());
                category.setIsle(dto.getIsle());
                category.setBay(dto.getBay());
                category = categoryRepository.save(category);
                return new JsonResultDTO("Berhasil Update data",true,category);
            }else {
                return new JsonResultDTO(false,"Category tidak di temukan");
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Update");
        }
    }

    @Override
    public JsonResultDTO updateBay(String categoryName, String bay) {
        var data = categoryRepository.getReferenceById(categoryName);
        if (data == null){
            return new JsonResultDTO(false,"category tidak ditemukan");
        }else {
            data.setBay(bay);
            data = categoryRepository.save(data);
            var data2 = getDataCategory(data.getName());
            return new JsonResultDTO("Berhasil update bay",true,data2);
        }
    }

    @Override
    public Boolean uniqueCategoryName(String categoryName) {
        boolean result;
        if (categoryRepository.uniqueCategoryName(categoryName) > 0){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
