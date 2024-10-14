package com.carbigdata.api_ocorrencia.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
	private String sub;
	private String nome;
	private String cpf;



	public CustomUserDetails(String sub, String nome, String cpf) {
        this.sub = sub;
        this.sub = nome;
        this.sub = cpf;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        }

    @Override
    public String getPassword() {
        return null; 
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
        return true;
    }

    @Override
    public String getUsername() {
        return sub;  
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

}
