package com.alura.foro.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {  // ← Implementa la interfaz correcta

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Log the error (opcional)
        System.err.println("Error de autenticación: " + authException.getMessage());

        // Enviar respuesta de error 401 Unauthorized
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(
                "{\"error\": \"No autorizado\", \"message\": \"Token inválido o faltante\"}"
        );
    }
}