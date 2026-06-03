package com.example.projectprogressmanagement.controller;

import com.example.projectprogressmanagement.common.Result;
import com.example.projectprogressmanagement.dto.LoginRequest;
import com.example.projectprogressmanagement.dto.LoginResponse;
import com.example.projectprogressmanagement.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse resp = authService.login(request);
            return Result.ok(resp);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/user-info")
    public Result<Map<String, String>> userInfo(HttpServletRequest request) {
        Map<String, String> info = new HashMap<>();
        info.put("username", (String) request.getAttribute("username"));
        info.put("realName", (String) request.getAttribute("realName"));
        return Result.ok(info);
    }
}
