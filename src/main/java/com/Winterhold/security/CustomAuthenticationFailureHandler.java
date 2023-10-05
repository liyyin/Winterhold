package com.Winterhold.security;

import com.Winterhold.service.absract.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String role = request.getParameter("role");
//        String url = String.format("%s%s",request.getContextPath(),"/account/failError");
        String url= "";
        if (exception.getMessage().equals("Maximum sessions of 1 for this principal exceeded")){
            url = String.format("%s%s?error=%s", request.getContextPath(), "/account/loginForm", exception.getMessage());
        }else {
            url = String.format("%s%s?error=%s", request.getContextPath(), "/account/loginForm", "Username/password Wrong");
        }
        response.sendRedirect(url);
    }

}
