package com.example.projectprogressmanagement.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.projectprogressmanagement.config.JwtUtil;
import com.example.projectprogressmanagement.dto.LoginRequest;
import com.example.projectprogressmanagement.dto.LoginResponse;
import com.example.projectprogressmanagement.entity.User;
import com.example.projectprogressmanagement.mapper.UserMapper;
import com.example.projectprogressmanagement.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername()));
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!user.getPassword().equals(DigestUtil.md5Hex(request.getPassword()))) {
            throw new RuntimeException("密码错误");
        }
        String token = jwtUtil.generateToken(user.getUsername(), user.getRealName());
        return new LoginResponse(token, user.getRealName(), user.getUsername());
    }
}
