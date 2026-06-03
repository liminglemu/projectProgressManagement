package com.example.projectprogressmanagement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.projectprogressmanagement.entity.Project;
import com.example.projectprogressmanagement.mapper.ProjectMapper;
import com.example.projectprogressmanagement.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    private boolean isAdmin(String role) {
        return "ADMIN".equals(role);
    }

    @Override
    public Project getById(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public Page<Project> page(Integer pageNum, Integer pageSize, String keyword, String phase, String currentUser, String role) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        if (!isAdmin(role)) {
            wrapper.eq(Project::getLeaderName, currentUser);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Project::getProjectName, keyword)
                    .or().like(Project::getProjectNo, keyword));
        }
        if (StringUtils.hasText(phase)) {
            wrapper.eq(Project::getPhase, phase);
        }
        wrapper.orderByDesc(Project::getUpdateTime);
        return projectMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    @Transactional
    public Project create(Project project, String currentUser) {
        project.setCreateBy(currentUser);
        if (!StringUtils.hasText(project.getLeaderName())) {
            project.setLeaderName(currentUser);
        }
        projectMapper.insert(project);
        return project;
    }

    @Override
    @Transactional
    public Project update(Project project) {
        Project existing = projectMapper.selectById(project.getId());
        if (existing == null) {
            throw new RuntimeException("项目不存在");
        }
        BeanUtil.copyProperties(project, existing, "id", "createTime", "createBy", "deleted");
        projectMapper.updateById(existing);
        return existing;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        projectMapper.deleteById(id);
    }

    @Override
    public List<String> getAllLeaders() {
        return projectMapper.selectList(null).stream()
                .map(Project::getLeaderName)
                .filter(StringUtils::hasText)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getStats(String currentUser, String role) {
        List<Project> myProjects;
        if (isAdmin(role)) {
            myProjects = projectMapper.selectList(null);
        } else {
            myProjects = projectMapper.selectList(new LambdaQueryWrapper<Project>()
                    .eq(Project::getLeaderName, currentUser));
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", myProjects.size());

        Map<String, Long> phaseCount = myProjects.stream()
                .collect(Collectors.groupingBy(p -> p.getPhase() != null ? p.getPhase() : "未知", Collectors.counting()));
        stats.put("phaseCount", phaseCount);

        long riskCount = myProjects.stream()
                .filter(p -> "NG".equals(p.getRisk1()) || "NG".equals(p.getRisk2()) || "NG".equals(p.getRisk3()))
                .count();
        stats.put("riskCount", riskCount);

        return stats;
    }
}
