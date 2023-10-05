package com.Winterhold.service.implementation;

import com.Winterhold.dao.AccountRepository;
import com.Winterhold.entity.Account;
import com.Winterhold.security.ApplicationUserDetail;
import com.Winterhold.service.absract.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountImplementation implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account userLogin = accountRepository.findById(username).get();
        return new ApplicationUserDetail(userLogin);
    }

    @Override
    public Account getData(String username) {
        return accountRepository.getReferenceById(username);
    }
}
