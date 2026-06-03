package com.example.projectprogressmanagement.service;

import com.example.projectprogressmanagement.entity.WeeklyReport;

import java.util.List;

public interface WeeklyReportService {
    List<WeeklyReport> getByProjectId(Long projectId, String currentUser);
    WeeklyReport save(WeeklyReport report, String currentUser);
    List<WeeklyReport> batchSave(List<WeeklyReport> reports, String currentUser);
}
