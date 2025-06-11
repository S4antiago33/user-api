package com.apirestfull.user_api.DTOs;

import com.apirestfull.user_api.models.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.id = usuario.getId();
        this.email = usuario.getEmail();
    }

}