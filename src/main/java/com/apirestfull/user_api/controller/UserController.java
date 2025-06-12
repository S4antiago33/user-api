package com.apirestfull.user_api.controller;

import com.apirestfull.user_api.DTOs.UsuarioResponseDTO;
import com.apirestfull.user_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = userService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@PathVariable String email) {
        UsuarioResponseDTO usuario = userService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(@PathVariable String nome) {
        List<UsuarioResponseDTO> usuarios = userService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        userService.deletarPorId(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
