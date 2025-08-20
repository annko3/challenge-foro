package com.alura.foro.controller;

import com.alura.foro.dto.JwtResponse;
import com.alura.foro.dto.LoginRequest;
import com.alura.foro.dto.UsuarioDto;
import com.alura.foro.security.JwtUtil;
import com.alura.foro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        // Obtener detalles del usuario
        var userDetails = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();

        // Buscar el usuario completo para obtener más información
        var usuario = usuarioService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre()
        ));
    }

    @PostMapping("/registro")
    public ResponseEntity<JwtResponse> registrarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        // Crear usuario
        UsuarioDto usuarioCreado = usuarioService.crearUsuario(usuarioDto);

        // Autenticar automáticamente
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDto.getEmail(),
                        usuarioDto.getContrasena()
                )
        );

        String jwt = jwtUtil.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                usuarioCreado.getId(),
                usuarioCreado.getEmail(),
                usuarioCreado.getNombre()
        ));
    }
}