package com.example.projectprogressmanagement.service;

import com.example.projectprogressmanagement.dto.LoginRequest;
import com.example.projectprogressmanagement.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
