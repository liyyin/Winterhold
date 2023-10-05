package com.Winterhold.restController;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.book.CreateBookDTO;
import com.Winterhold.dto.book.UpdateBookDTO;
import com.Winterhold.service.absract.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public JsonResultDTO getList(@RequestParam(required = true)String categoryName,
                                 @RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "")String bookName,
                                 @RequestParam(defaultValue = "")String author,
                                 @RequestParam(defaultValue = "off")String isAvail){
        try{
            var data = bookService.getAllData(page,categoryName,bookName,author,isAvail);
            return new JsonResultDTO("Berhasil",true,data);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }

    @PostMapping
    public JsonResultDTO Insert(@Valid @RequestBody CreateBookDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = bookService.saveRest(dto);
            return result;
        }
    }
    @PutMapping
    public JsonResultDTO update(@Valid @RequestBody UpdateBookDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = bookService.updateRest(dto);
            return result;
        }
    }
    @PatchMapping
    public JsonResultDTO updateSummary(@RequestParam String bookCode,
                                   @RequestParam String summary){
        var result = bookService.updateSummary(bookCode,summary);
        return result;
    }
    @DeleteMapping
    public JsonResultDTO deleteData(@RequestParam String bookCode){
        var result = bookService.delete(bookCode);
        return result;
    }
}
