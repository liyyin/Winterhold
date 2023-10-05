package com.Winterhold.controller;

import com.Winterhold.dao.CategoryRepository;
import com.Winterhold.dto.ErrorValidationDto;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.category.CreateCategoryDTO;
import com.Winterhold.dto.category.UpdateCategoryDTO;
import com.Winterhold.service.absract.BookService;
import com.Winterhold.service.absract.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String searchName){
        var dataCategory = categoryService.getAllData(page,searchName);
        model.addAttribute("dto",dataCategory);
        return "category/index";
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CreateCategoryDTO dto,
                                  BindingResult bindingResult){
        var result = new JsonResultDTO();
        try{
            if (bindingResult.hasErrors()){
                var listError = bindingResult.getAllErrors().stream().toList();
                List<ErrorValidationDto> listErrorDto = new LinkedList<>();
                for (ObjectError err: listError){
                    if (err instanceof FieldError){
                        FieldError fieldError = (FieldError) err;
                        String msg = fieldError.getDefaultMessage();
                        String propertyName = fieldError.getField();

                        listErrorDto.add(new ErrorValidationDto(propertyName,msg));
                    }else {
                        listErrorDto.add(new ErrorValidationDto(err.getDefaultMessage().split("_")[0],err.getDefaultMessage().split("_")[1]));
                    }
                }
                return ResponseEntity.ok(new JsonResultDTO(false,listErrorDto));
            }else {
                categoryService.save(dto);
                return ResponseEntity.status(200).body(new JsonResultDTO(true,"Save Berhasil"));
            }
        }catch (Exception ex){
            return ResponseEntity.status(200).body(new JsonResultDTO(false,ex.getMessage())) ;
        }

    }

    @GetMapping("/update")
    public ResponseEntity<?> getUpdateData(@RequestParam(required = true)String name){
        var result = new JsonResultDTO();
        try{
            var dto = categoryService.getDataCategory(name);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> Update(@Valid @RequestBody UpdateCategoryDTO dto,
                                  BindingResult bindingResult){
        var result = new JsonResultDTO();
        try{
            if (bindingResult.hasErrors()){
                var listError = bindingResult.getAllErrors().stream().toList();
                List<ErrorValidationDto> listErrorDto = new LinkedList<>();
                for (ObjectError err: listError){
                    if (err instanceof FieldError){
                        FieldError fieldError = (FieldError) err;
                        String msg = fieldError.getDefaultMessage();
                        String propertyName = fieldError.getField();

                        listErrorDto.add(new ErrorValidationDto(propertyName,msg));
                    }else {
                        listErrorDto.add(new ErrorValidationDto(err.getDefaultMessage().split("_")[0],err.getDefaultMessage().split("_")[1]));
                    }
                }
                return ResponseEntity.ok(new JsonResultDTO(false,listErrorDto));
            }else {
                categoryService.update(dto);
                return ResponseEntity.status(200).body(new JsonResultDTO(true,"Save Berhasil"));
            }
        }catch (Exception ex){
            return ResponseEntity.status(200).body(new JsonResultDTO(false,ex.getMessage())) ;
        }

    }
    @PostMapping("/delete")
    public ResponseEntity<?> cancel(@RequestParam(required = true) String categoryName){
        var message = new JsonResultDTO();
        message = categoryService.delete(categoryName);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/index2")
    public ResponseEntity<Object> index2(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String searchName){
        var dataCategory = categoryService.getAllDataCategory(searchName);
        return ResponseEntity.ok().body(dataCategory);

    }
}
