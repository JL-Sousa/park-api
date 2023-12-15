package com.tecsoftblue.parkapi.jwt;

import com.tecsoftblue.parkapi.entities.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private Usuario usuario;

    public JwtUserDetails(Usuario usuario) {
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    public Long getId() {
        return usuario.getId();
    }

    public String getRole() {
       return usuario.getRole().name();
    }
}
