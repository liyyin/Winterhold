package com.Winterhold.restController;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.customer.CreateCustomerDTO;
import com.Winterhold.dto.customer.UpdateCustomerDTO;
import com.Winterhold.service.absract.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public JsonResultDTO getList(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "") String searchNumber,
                                 @RequestParam(defaultValue = "") String searchName,
                                 @RequestParam(defaultValue = "off") String expired){
        try{
            var dataCategory = customerService.getAllData(page,searchNumber, searchName,expired);
            return new JsonResultDTO("Berhasil",true,dataCategory);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }
    @PostMapping
    public JsonResultDTO Insert(@Valid @RequestBody CreateCustomerDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = customerService.saveRest(dto);
            return result;
        }
    }
    @PutMapping
    public JsonResultDTO update(@Valid @RequestBody UpdateCustomerDTO dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {

            return new JsonResultDTO(false,"terjadi kesalahan input");
        } else {
            var result = customerService.updateRest(dto);
            return result;
        }
    }
    @PatchMapping
    public JsonResultDTO updateSummary(@RequestParam String membership,
                                       @RequestParam String address){
        var result = customerService.updateAddress(membership,address);
        return result;
    }
    @DeleteMapping
    public JsonResultDTO deleteData(@RequestParam String membership){
        var result = customerService.delete(membership);
        return result;
    }
}
