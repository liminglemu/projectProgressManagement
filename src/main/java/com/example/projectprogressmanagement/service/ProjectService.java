package com.example.projectprogressmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.projectprogressmanagement.entity.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService extends IService<Project> {
    Page<Project> page(Integer pageNum, Integer pageSize, String keyword, String phase, String currentUser);
    Project create(Project project, String currentUser);
    Project update(Project project);
    List<String> getAllLeaders();
    Map<String, Object> getStats(String currentUser);
}
