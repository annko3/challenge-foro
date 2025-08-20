package com.alura.foro.security;

import com.alura.foro.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean activo;

    public CustomUserDetails(Long id, String email, String password,
                             Collection<? extends GrantedAuthority> authorities, Boolean activo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.activo = activo;
    }

    public static CustomUserDetails build(Usuario usuario) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getContrasena(),
                authorities,
                usuario.getActivo()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Usamos el email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cuenta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cuenta nunca está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciales nunca expiran
    }

    @Override
    public boolean isEnabled() {
        return activo; // Usamos el campo activo para determinar si está habilitado
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomUserDetails that = (CustomUserDetails) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}