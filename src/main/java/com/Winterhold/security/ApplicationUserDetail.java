package com.Winterhold.security;


import com.Winterhold.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ApplicationUserDetail implements UserDetails {

    private String username;
    private List<GrantedAuthority> role = new ArrayList<>();
    private String roleName;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUserDetail that = (ApplicationUserDetail) o;
        return username.equals(that.username) && role.equals(that.role) && roleName.equals(that.roleName) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role, roleName, password);
    }

    public ApplicationUserDetail(Account account) {
        this.username = account.getUsername();
        this.role.add(new SimpleGrantedAuthority(account.getRole()));
        this.roleName = account.getRole();
        this.password = account.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getRoleName(){return this.roleName;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {return true;}
}
