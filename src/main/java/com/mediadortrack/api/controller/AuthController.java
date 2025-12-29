package com.mediadortrack.api.controller;

import com.mediadortrack.api.dto.LoginRequestDTO;
import com.mediadortrack.api.dto.LoginResponseDTO;
import com.mediadortrack.api.model.User;
import com.mediadortrack.api.repository.UserRepository;
import com.mediadortrack.api.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request,
            HttpServletResponse response
    ) {
        LoginResponseDTO dto = authService.login(request);

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        String token = authService.generateToken(user);

        ResponseCookie cookie = buildAuthCookie(token, 60 * 60 * 8);
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(dto);
    }
    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> me(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.ok(null);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return ResponseEntity.ok(
                new LoginResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }



    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        ResponseCookie cookie = buildAuthCookie("", 0);
        response.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().build();
    }

    private ResponseCookie buildAuthCookie(String token, long maxAgeSeconds) {
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(maxAgeSeconds)
                .sameSite("Lax")
                .build();
    }
}


