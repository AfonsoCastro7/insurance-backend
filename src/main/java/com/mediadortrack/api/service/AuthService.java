package com.mediadortrack.api.service;

import com.mediadortrack.api.dto.LoginRequestDTO;
import com.mediadortrack.api.dto.LoginResponseDTO;
import com.mediadortrack.api.model.User;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    String generateToken(User user);
}
