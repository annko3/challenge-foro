package com.alura.foro.service.impl;

import com.alura.foro.dto.UsuarioDto;
import com.alura.foro.entity.Usuario;
import com.alura.foro.repository.UsuarioRepository;
import com.alura.foro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .filter(Usuario::getActivo)
                .map(this::convertirToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDto> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .filter(Usuario::getActivo)
                .map(this::convertirToDto);
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setContrasena(passwordEncoder.encode(usuarioDto.getContrasena()));
        usuario.setActivo(true); // ← AÑADE ESTO

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirToDto(usuarioGuardado);
    }

    @Override
    public Optional<UsuarioDto> actualizarUsuario(Long id, UsuarioDto usuarioDto) {
        return usuarioRepository.findById(id)
                .filter(Usuario::getActivo)
                .map(usuario -> {
                    if (!usuario.getEmail().equals(usuarioDto.getEmail())) {
                        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
                            throw new RuntimeException("Ya existe un usuario con este email");
                        }
                    }

                    usuario.setNombre(usuarioDto.getNombre());
                    usuario.setEmail(usuarioDto.getEmail());

                    if (usuarioDto.getContrasena() != null && !usuarioDto.getContrasena().isEmpty()) {
                        usuario.setContrasena(passwordEncoder.encode(usuarioDto.getContrasena()));
                    }

                    Usuario usuarioActualizado = usuarioRepository.save(usuario);
                    return convertirToDto(usuarioActualizado);
                });
    }

    @Override
    public boolean eliminarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .filter(Usuario::getActivo)
                .map(usuario -> {
                    usuario.setActivo(false);
                    usuarioRepository.save(usuario);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmailAndActivoTrue(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    private UsuarioDto convertirToDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        // No incluimos la contraseña por seguridad
        return dto;
    }
}