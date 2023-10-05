package com.Winterhold.restController;

import com.Winterhold.dto.RequestTokenDTO;
import com.Winterhold.dto.ResponseTokenDTO;
import com.Winterhold.security.rest.JwtManager;
import com.Winterhold.service.absract.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticateRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
            authenticationManager.authenticate(token);
        }catch (Exception ex){
            return new ResponseTokenDTO(dto.getUsername(), dto.getAudience(),"","FAIL" );
        }
        var dataAccount = accountService.getData(dto.getUsername());
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String role = accountService.getData(dto.getUsername()).getRole();
        String token = jwtManager.generateToke(dto.getSubject(),dto.getUsername(),dto.getSecretKey(),role ,dto.getAudience());
        ResponseTokenDTO response = new ResponseTokenDTO(dto.getUsername(),role,token,"SUKSES");
        return response;
    }
}
