package com.alura.foro.controller;

import com.alura.foro.dto.JwtResponse;
import com.alura.foro.dto.LoginRequest;
import com.alura.foro.dto.RegistroRequest;
import com.alura.foro.model.Usuario;
import com.alura.foro.service.AuthService;
import com.alura.foro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.autenticarUsuario(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@Valid @RequestBody RegistroRequest registroRequest) {
        Usuario usuario = usuarioService.registrarUsuario(
                registroRequest.nombre(),
                registroRequest.email(),
                registroRequest.password()
        );

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}