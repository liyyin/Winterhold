package com.Winterhold.restController;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.customer.CreateCustomerDTO;
import com.Winterhold.dto.customer.UpdateCustomerDTO;
import com.Winterhold.dto.loan.CreateUpdateLoanDTO;
import com.Winterhold.service.absract.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
public class LoanRestController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public JsonResultDTO getList(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "") String bookName,
                                 @RequestParam(defaultValue = "") String customerName,
                                 @RequestParam(defaultValue = "off") String dueDate){
        try{
            var dataCategory = loanService.getAllData(page,bookName, customerName,dueDate);
            return new JsonResultDTO("Berhasil",true,dataCategory);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }
    @PostMapping
    public JsonResultDTO Insert(@Valid @RequestBody CreateUpdateLoanDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = loanService.saveRest(dto);
            return result;
        }
    }
    @PutMapping
    public JsonResultDTO update(@Valid @RequestBody CreateUpdateLoanDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = loanService.updateRest(dto);
            return result;
        }
    }
    @PatchMapping
    public JsonResultDTO updateNote(@RequestParam Long idLoan,
                                       @RequestParam String note){
        var result = loanService.updateNote(idLoan,note);
        return result;
    }
}
