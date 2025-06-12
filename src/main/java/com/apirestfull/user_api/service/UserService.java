package com.apirestfull.user_api.service;

import com.apirestfull.user_api.DTOs.UpdateUsuarioDTO;
import com.apirestfull.user_api.DTOs.UsuarioResponseDTO;
import com.apirestfull.user_api.models.Usuario;
import com.apirestfull.user_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }

    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = userRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getEmail(),
                        usuario.getNome()
                ))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        var usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioResponseDTO(usuario);
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        List<Usuario> usuarios = userRepository.findByNomeContainingIgnoreCase(nome);

        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(), usuario.getNome(), usuario.getEmail()))
                .toList();
    }

    public void deletarPorId(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com o ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public UsuarioResponseDTO atualizar(Long id, UpdateUsuarioDTO dados) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setPassword(dados.getPassword());

        Usuario atualizado = userRepository.save(usuario);

        return new UsuarioResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getEmail());
    }


}
