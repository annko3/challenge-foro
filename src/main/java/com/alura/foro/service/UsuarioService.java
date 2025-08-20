package com.alura.foro.service;

import com.alura.foro.dto.UsuarioDto;
import com.alura.foro.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<UsuarioDto> listarUsuarios();
    Optional<UsuarioDto> obtenerUsuarioPorId(Long id);
    UsuarioDto crearUsuario(UsuarioDto usuarioDto);
    Optional<UsuarioDto> actualizarUsuario(Long id, UsuarioDto usuarioDto);
    boolean eliminarUsuario(Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
