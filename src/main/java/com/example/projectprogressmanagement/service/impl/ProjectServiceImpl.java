package com.example.projectprogressmanagement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.projectprogressmanagement.entity.Project;
import com.example.projectprogressmanagement.mapper.ProjectMapper;
import com.example.projectprogressmanagement.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public Page<Project> page(Integer pageNum, Integer pageSize, String keyword, String phase, String currentUser) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getLeaderName, currentUser);
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Project::getProjectName, keyword)
                    .or().like(Project::getProjectNo, keyword));
        }
        if (StringUtils.hasText(phase)) {
            wrapper.eq(Project::getPhase, phase);
        }
        wrapper.orderByDesc(Project::getUpdateTime);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Project create(Project project, String currentUser) {
        project.setCreateBy(currentUser);
        if (!StringUtils.hasText(project.getLeaderName())) {
            project.setLeaderName(currentUser);
        }
        save(project);
        return project;
    }

    @Override
    public Project update(Project project) {
        Project existing = getById(project.getId());
        if (existing == null) {
            throw new RuntimeException("项目不存在");
        }
        BeanUtil.copyProperties(project, existing, "id", "createTime", "createBy", "deleted");
        updateById(existing);
        return existing;
    }

    @Override
    public List<String> getAllLeaders() {
        return list().stream()
                .map(Project::getLeaderName)
                .filter(StringUtils::hasText)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getStats(String currentUser) {
        List<Project> myProjects = list(new LambdaQueryWrapper<Project>()
                .eq(Project::getLeaderName, currentUser));

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
