package com.Winterhold.controller;

import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.customer.CreateCustomerDTO;
import com.Winterhold.dto.customer.UpdateCustomerDTO;
import com.Winterhold.service.absract.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String searchNumber,
                        @RequestParam(defaultValue = "") String searchName,
                        @RequestParam(defaultValue = "off") String expired){
        var dataCustomer = customerService.getAllData(page,searchNumber,searchName,expired);
        model.addAttribute("dto",dataCustomer);
        return "customer/index";
    }

    @GetMapping("/insert")
    public String insert(Model model) {
        CreateCustomerDTO dto = new CreateCustomerDTO();
        model.addAttribute("dto", dto);
        model.addAttribute("action", "insert");
        return "customer/createUpdate";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") CreateCustomerDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action","insert");
            return "customer/createUpdate";
        } else {
            customerService.save(dto);
            return "redirect:/customer/index";
        }
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam(required = true) String membershipNumber) {
        var dto = customerService.getCustomerDTO(membershipNumber);
        model.addAttribute("dto", dto);
        model.addAttribute("action", "update");
        return "customer/createUpdate";
    }
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dto") UpdateCustomerDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action","update");
            model.addAttribute("dto", dto);
            return "customer/createUpdate";
        } else {
            customerService.update(dto);
            return "redirect:/customer/index";
        }
    }
    @PostMapping("/extend")
    public ResponseEntity<?> returnDate (@RequestParam(required = true) String membershipNumber){
        var message = new JsonResultDTO();
        message = customerService.setExtend(membershipNumber);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> cancel(@RequestParam(required = true) String membershipNumber){
        var message = new JsonResultDTO();
        message = customerService.delete(membershipNumber);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<?> getCustomer(@RequestParam String customerNumber){
        var result = new JsonResultDTO();
        try{
            var dto = customerService.getCustomerDetail(customerNumber);
            result = new JsonResultDTO(true,dto);
        }catch (Exception ex){
            result = new JsonResultDTO(false,"Internal Server Error");
        }
        return ResponseEntity.ok(result);
    }
}
