package com.mediadortrack.api.controller;

import com.mediadortrack.api.dto.LoginRequestDTO;
import com.mediadortrack.api.dto.LoginResponseDTO;
import com.mediadortrack.api.model.User;
import com.mediadortrack.api.repository.UserRepository;
import com.mediadortrack.api.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {
        LoginResponseDTO dto = authService.login(request);

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        String token = authService.generateToken(user);

        ResponseCookie cookie = buildAuthCookie(token, 60 * 60 * 8, httpRequest);
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
    public ResponseEntity<Void> logout(HttpServletRequest httpRequest, HttpServletResponse response) {
        ResponseCookie cookie = buildAuthCookie("", 0, httpRequest);
        response.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().build();
    }

    private ResponseCookie buildAuthCookie(String token, long maxAgeSeconds, HttpServletRequest request) {
        boolean secure = isSecureRequest(request);
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(secure)
                .path("/")
                .maxAge(maxAgeSeconds)
                .sameSite(secure ? "None" : "Lax")
                .build();
    }

    private boolean isSecureRequest(HttpServletRequest request) {
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        if (forwardedProto != null) {
            return forwardedProto.equalsIgnoreCase("https");
        }
        return request.isSecure();
    }
}


