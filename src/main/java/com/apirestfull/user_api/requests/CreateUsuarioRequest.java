package com.apirestfull.user_api.requests;

import com.apirestfull.user_api.models.Usuario;
import com.apirestfull.user_api.validations.EmailIsUnique;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUsuarioRequest {
    @NotNull
    @NotEmpty
    private String nome;
    @NotNull
    @NotEmpty
    @EmailIsUnique(entity = Usuario.class, message = "Este email já está em uso")
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 8)
    private String password;

    public Usuario converter() {
        Usuario usuario = new Usuario();
        usuario.setEmail(this.email);
        usuario.setNome(this.nome);
        usuario.setPassword(this.password);
        return usuario;
    }

    public Usuario atualizar(Usuario usuario) {
        if (this.email != null) usuario.setEmail(this.email);
        if (this.nome != null) usuario.setNome(this.nome);
        return usuario;
    }
}
