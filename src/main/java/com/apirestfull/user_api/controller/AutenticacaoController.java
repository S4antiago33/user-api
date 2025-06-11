package com.apirestfull.user_api.controller;

import com.apirestfull.user_api.DTOs.LoginDTO;
import com.apirestfull.user_api.DTOs.UsuarioDTO;
import com.apirestfull.user_api.models.Usuario;
import com.apirestfull.user_api.repository.UserRepository;
import com.apirestfull.user_api.requests.LoginRequest;
import com.apirestfull.user_api.requests.CreateUsuarioRequest;
import com.apirestfull.user_api.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Autowired
    public AutenticacaoController(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Endpoint para criar novo usuário
    @PostMapping("/create")
    public ResponseEntity<UsuarioDTO> createUser(@RequestBody @Validated CreateUsuarioRequest request) {
        Usuario usuario = request.converter();

        usuario.setPassword(this.encoder.encode(request.getPassword()));

        this.userRepository.save(usuario);

        return new ResponseEntity<>(new UsuarioDTO(usuario), HttpStatus.CREATED);
    }

    // Endpoint para autenticação de usuário (login)
    @PostMapping
    public ResponseEntity<LoginDTO> login(@RequestBody @Validated LoginRequest request) {
        Usuario usuario = this.userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("E-mail e/ou senha inválidos"));

        boolean isPasswordValid = this.encoder.matches(request.getPassword(), usuario.getPassword());

        if (!isPasswordValid) {
            throw new UsernameNotFoundException("E-mail e/ou senha inválidos");
        }

        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new LoginDTO(usuario.getEmail(), "Bearer", token));

    }
}
