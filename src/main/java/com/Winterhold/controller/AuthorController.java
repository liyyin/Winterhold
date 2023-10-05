package com.Winterhold.controller;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.author.CreateUpdateAuthorDTO;
import com.Winterhold.service.absract.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String searchName){
        var dataAuthor = authorService.getAllData(page,searchName);
        model.addAttribute("dto",dataAuthor);
        return "author/index";
    }

    @GetMapping("/insert")
    public String insert(Model model) {
        CreateUpdateAuthorDTO dto = new CreateUpdateAuthorDTO();
        dto.setId(0l);
        model.addAttribute("dto", dto);
        return "author/createUpdate";
    }

    @PostMapping("/save")
    public String insert(@Valid @ModelAttribute("dto") CreateUpdateAuthorDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dto", dto);
            return "author/createUpdate";
        } else {
            authorService.save(dto);
            return "redirect:/author/index";
        }
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam(required = true) Long id) {
        var dto = authorService.getAuthorDTO(id);
        model.addAttribute("dto", dto);
        return "author/createUpdate";
    }

    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam(required = true) Long id){
        var dataAuthor = authorService.getDataAuthor(id);
        var dataListBook = authorService.getListBookByIdAuthor(id);
        model.addAttribute("dto",dataAuthor);
        model.addAttribute("listDto",dataListBook);
        return "author/detail";
    }

    @PostMapping("/delete")
    public ResponseEntity<?> cancel(@RequestParam(required = true) Long id){
        var message = new JsonResultDTO();
        message = authorService.delete(id);
        return ResponseEntity.ok(message);
    }
}
