package com.restaurant.management.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class USersDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private boolean enable;

    public USersDetails(User user) {
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getRoles().split(",")).map(role ->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        this.enable = user.isEnable();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
    public boolean isEnabled() {
        return enable;
    }
}
