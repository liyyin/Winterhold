package com.Winterhold.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/loginForm")
    public String loginForm(Model model){
        return "account/loginForm";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "account/accessDenied";
    }

}
