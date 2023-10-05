package com.Winterhold.controller;

import com.Winterhold.dto.ErrorValidationDto;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.loan.CreateUpdateLoanDTO;
import com.Winterhold.service.absract.BookService;
import com.Winterhold.service.absract.CustomerService;
import com.Winterhold.service.absract.LoanService;
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
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String bookName,
                        @RequestParam(defaultValue = "") String customerName,
                        @RequestParam(defaultValue = "off") String dueDate){
        var dataCustomer = loanService.getAllData(page,bookName,customerName,dueDate);
        model.addAttribute("dto",dataCustomer);
        return "loan/index";
    }
    @GetMapping("/getCustomerDDL")
    public ResponseEntity<?> getDropDownCustomer(){
        var result = new JsonResultDTO();
        try{
            var dto = loanService.getCustomerDDL();
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getBookDDL")
    public ResponseEntity<?> getDropDownBook(){
        var result = new JsonResultDTO();
        try{
            var dto = loanService.getBookDDL();
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CreateUpdateLoanDTO dto,
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
                var hasil = loanService.save(dto);
                return ResponseEntity.status(200).body(hasil);
            }
        }catch (Exception ex){
            return ResponseEntity.status(200).body(new JsonResultDTO(false,ex.getMessage())) ;
        }

    }
    @GetMapping("/update")
    public ResponseEntity<?> updateModal(@RequestParam(required = true) Long id){
        var result = new JsonResultDTO();
        try{
            var dto = loanService.getLoanDTO(id);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/return")
    public ResponseEntity<?> returnDate (@RequestParam(required = true) Long id){
        var message = new JsonResultDTO();
        message = loanService.setReturnDate(id);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/getDataBook")
    public ResponseEntity<?> getDropBook(@RequestParam String bookCode){
        var result = new JsonResultDTO();
        try{
            var dto = bookService.getBookDTO(bookCode);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<?> getCustomer(@RequestParam String customerNumber){
        var result = new JsonResultDTO();
        try{
            var dto = customerService.getCustomer(customerNumber);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getBook")
    public ResponseEntity<?> getBook(@RequestParam String bookCode){
        var result = new JsonResultDTO();
        try{
            var dto = bookService.getBook(bookCode);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }
}
