
package com.ems.employee_management_system.controller;

import com.ems.employee_management_system.dto.LoginRequest;
import com.ems.employee_management_system.service.AuthService;
import com.ems.employee_management_system.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest request) {

        Authentication authentication = authService.login(request);

        String token = jwtService.generateToken(
                authentication.getName()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "username", authentication.getName(),
                        "token", token
                )
        );
    }
}
