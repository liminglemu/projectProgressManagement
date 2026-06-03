package com.example.projectprogressmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.projectprogressmanagement.common.Result;
import com.example.projectprogressmanagement.entity.Project;
import com.example.projectprogressmanagement.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    private String currentUser(HttpServletRequest request) {
        return (String) request.getAttribute("realName");
    }

    private String currentRole(HttpServletRequest request) {
        return (String) request.getAttribute("role");
    }

    @GetMapping
    public Result<Page<Project>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String phase,
            HttpServletRequest request) {
        return Result.ok(projectService.page(pageNum, pageSize, keyword, phase,
                currentUser(request), currentRole(request)));
    }

    @GetMapping("/{id}")
    public Result<Project> getById(@PathVariable Long id) {
        return Result.ok(projectService.getById(id));
    }

    @PostMapping
    public Result<Project> create(@RequestBody Project project, HttpServletRequest request) {
        return Result.ok(projectService.create(project, currentUser(request)));
    }

    @PutMapping("/{id}")
    public Result<Project> update(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        return Result.ok(projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        projectService.deleteById(id);
        return Result.ok();
    }

    @GetMapping("/leaders")
    public Result<List<String>> leaders() {
        return Result.ok(projectService.getAllLeaders());
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> stats(HttpServletRequest request) {
        return Result.ok(projectService.getStats(currentUser(request), currentRole(request)));
    }
}
