package com.Winterhold.controller;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.book.CreateBookDTO;
import com.Winterhold.dto.book.UpdateBookDTO;
import com.Winterhold.service.absract.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/index")
    public String index(@RequestParam(required = true)String categoryName,
                        @RequestParam(defaultValue = "1")Integer page,
                        @RequestParam(defaultValue = "")String bookName,
                        @RequestParam(defaultValue = "")String author,
                        @RequestParam(defaultValue = "off")String isAvail,
                        Model model){
        var dataBook = bookService.getAllData(page,categoryName,bookName,author,isAvail);
        model.addAttribute("dto",dataBook);
        return "book/index";
    }
    @GetMapping("/insert")
    public String insert(Model model,@RequestParam(required = true)String categoryName) {
        CreateBookDTO dto = new CreateBookDTO();
        var dropdownAuthor = bookService.getAuthorDDL();
        model.addAttribute("dto", dto);
        model.addAttribute("action","insert");
        model.addAttribute("categoryName",categoryName);
        model.addAttribute("dropDownAuthor",dropdownAuthor);
        return "book/createUpdate";
    }
    @GetMapping("/getAuthorDDL")
    public ResponseEntity<?> getDropDownAuthor(){
        var result = new JsonResultDTO();
        try{
            var dto = bookService.getAuthorDDL();
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public String insert(@Valid @ModelAttribute("dto") CreateBookDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var dropdownAuthor = bookService.getAuthorDDL();
            model.addAttribute("dto", dto);
            model.addAttribute("action","insert");
            model.addAttribute("dropDownAuthor",dropdownAuthor);
            return "book/createUpdate";
        } else {
            bookService.insert(dto,dto.getCategoryName());
            return String.format("redirect:/book/index?categoryName=%s",dto.getCategoryName());
        }
    }
    @GetMapping("/update")
    public String update(Model model, @RequestParam(required = true) String bookCode) {
        var dto = bookService.getBookDTO(bookCode);
        var dropdownAuthor = bookService.getAuthorDDL();
        model.addAttribute("action","update");
        model.addAttribute("dropDownAuthor",dropdownAuthor);
        model.addAttribute("dto", dto);
        return "book/createUpdate";
    }
    @PostMapping("/update")
    public String insert(@Valid @ModelAttribute("dto") UpdateBookDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var dropdownAuthor = bookService.getAuthorDDL();
            model.addAttribute("dto", dto);
            model.addAttribute("dropDownAuthor",dropdownAuthor);
            model.addAttribute("action","update");
            return "book/createUpdate";
        } else {
            bookService.update(dto,dto.getCategoryName());
            return String.format("redirect:/book/index?categoryName=%s",dto.getCategoryName());
        }
    }
    @GetMapping("/showSummary")
    public ResponseEntity<?> getSummary(@RequestParam String bookCode){
        var result = new JsonResultDTO();
        try{
            var dto = bookService.getSummary(bookCode);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/delete")
    public ResponseEntity<?> cancel(@RequestParam(required = true) String bookCode){
        var message = new JsonResultDTO();
        message = bookService.delete(bookCode);
        return ResponseEntity.ok(message);
    }
}
