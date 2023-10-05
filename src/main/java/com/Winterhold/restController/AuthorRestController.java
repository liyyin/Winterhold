package com.Winterhold.restController;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.author.CreateUpdateAuthorDTO;
import com.Winterhold.service.absract.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public JsonResultDTO getList(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "") String searchName){
        try{
        var dataAuthor = authorService.getAllData(page, searchName);
        return new JsonResultDTO("Berhasil",true,dataAuthor);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }
    @PostMapping
    public JsonResultDTO Insert(@Valid @RequestBody CreateUpdateAuthorDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = authorService.saveRest(dto);
            return result;
        }
    }
    @PutMapping
    public JsonResultDTO update(@Valid @RequestBody CreateUpdateAuthorDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = authorService.updateRest(dto);
            return result;
        }
    }
    @PatchMapping
    public JsonResultDTO updateSummary(@RequestParam Long id,
                                       @RequestParam String summary){
        var result = authorService.updateSummary(id,summary);
        return result;
    }
    @DeleteMapping
    public JsonResultDTO deleteData(@RequestParam Long id){
        var result = authorService.deleteRest(id);
        return result;
    }
}
