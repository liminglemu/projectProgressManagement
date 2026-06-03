package com.example.projectprogressmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.projectprogressmanagement.entity.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    Project getById(Long id);
    Page<Project> page(Integer pageNum, Integer pageSize, String keyword, String phase, String currentUser, String role);
    Project create(Project project, String currentUser);
    Project update(Project project);
    void deleteById(Long id);
    List<String> getAllLeaders();
    Map<String, Object> getStats(String currentUser, String role);
}
