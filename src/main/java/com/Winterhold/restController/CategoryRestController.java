package com.Winterhold.restController;

import com.Winterhold.dao.CategoryRepository;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.category.CreateCategoryDTO;
import com.Winterhold.dto.category.UpdateCategoryDTO;
import com.Winterhold.entity.Category;
import com.Winterhold.service.absract.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public JsonResultDTO getList(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "") String searchName){
        try{
            var data = categoryService.getAllData(page, searchName);
            return new JsonResultDTO("Berhasil",true,data);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }



    @PostMapping
    public JsonResultDTO Insert(@Valid @RequestBody CreateCategoryDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = categoryService.saveRest(dto);
            return result;
        }
    }
    @PutMapping
    public JsonResultDTO update(@Valid @RequestBody UpdateCategoryDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = categoryService.updateRest(dto);
            return result;
        }
    }
    @PatchMapping
    public JsonResultDTO updateBay(@RequestParam String categoryName,
                                       @RequestParam String bay){
        var result = categoryService.updateBay(categoryName,bay);
        return result;
//        return new JsonResultDTO(true,"succse");
    }
    @DeleteMapping
    public JsonResultDTO deleteData(@RequestParam String categoryName){
        var result = categoryService.delete(categoryName);
        return result;
    }
}
