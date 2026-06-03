package com.example.projectprogressmanagement.controller;

import com.example.projectprogressmanagement.common.Result;
import com.example.projectprogressmanagement.entity.WeeklyReport;
import com.example.projectprogressmanagement.service.WeeklyReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-reports")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    private String currentUser(HttpServletRequest request) {
        return (String) request.getAttribute("realName");
    }

    @GetMapping("/project/{projectId}")
    public Result<List<WeeklyReport>> getByProject(@PathVariable Long projectId, HttpServletRequest request) {
        return Result.ok(weeklyReportService.getByProjectId(projectId, currentUser(request)));
    }

    @PostMapping
    public Result<WeeklyReport> save(@RequestBody WeeklyReport report, HttpServletRequest request) {
        return Result.ok(weeklyReportService.save(report, currentUser(request)));
    }

    @PostMapping("/batch")
    public Result<List<WeeklyReport>> batchSave(@RequestBody List<WeeklyReport> reports, HttpServletRequest request) {
        return Result.ok(weeklyReportService.batchSave(reports, currentUser(request)));
    }
}
