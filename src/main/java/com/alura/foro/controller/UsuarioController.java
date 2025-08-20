package com.alura.foro.controller;

import com.alura.foro.model.Usuario;
import com.alura.foro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener perfil del usuario autenticado
    @GetMapping("/perfil")
    public ResponseEntity<Usuario> obtenerPerfil(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(usuario);
    }

    // Actualizar perfil de usuario
    @PutMapping("/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(
            @AuthenticationPrincipal Usuario usuarioActual,
            @RequestBody Usuario usuarioActualizado) {

        // Lógica para actualizar el usuario
        usuarioActual.setNombre(usuarioActualizado.getNombre());
        // No permitimos cambiar email por seguridad
        // La contraseña se cambia mediante endpoint específico

        Usuario usuarioGuardado = usuarioService.actualizarUsuario(usuarioActual);
        return ResponseEntity.ok(usuarioGuardado);
    }

    // Endpoint para que un admin pueda listar todos los usuarios
    @GetMapping
    public ResponseEntity<?> listarUsuarios(@AuthenticationPrincipal Usuario usuario) {
        // Verificar si el usuario es administrador
        // if (!usuario.esAdmin()) {
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // }

        return ResponseEntity.ok(usuarioService.listarTodos());
    }
}