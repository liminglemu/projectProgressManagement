package com.example.projectprogressmanagement.service;

import com.example.projectprogressmanagement.entity.WeeklyReport;

import java.util.List;

public interface WeeklyReportService {
    List<WeeklyReport> getByProjectId(Long projectId, String currentUser);
    void insertReport(WeeklyReport report, String currentUser);
    void batchSaveReports(List<WeeklyReport> reports, String currentUser);
}
