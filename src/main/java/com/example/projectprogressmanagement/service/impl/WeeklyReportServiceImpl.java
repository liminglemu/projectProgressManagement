package com.example.projectprogressmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.projectprogressmanagement.entity.WeeklyReport;
import com.example.projectprogressmanagement.mapper.WeeklyReportMapper;
import com.example.projectprogressmanagement.service.WeeklyReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WeeklyReportServiceImpl implements WeeklyReportService {

    private final WeeklyReportMapper weeklyReportMapper;

    public WeeklyReportServiceImpl(WeeklyReportMapper weeklyReportMapper) {
        this.weeklyReportMapper = weeklyReportMapper;
    }

    @Override
    public List<WeeklyReport> getByProjectId(Long projectId, String currentUser) {
        return weeklyReportMapper.selectList(new LambdaQueryWrapper<WeeklyReport>()
                .eq(WeeklyReport::getProjectId, projectId)
                .eq(WeeklyReport::getCreateBy, currentUser)
                .orderByAsc(WeeklyReport::getWeekNo));
    }

    @Override
    @Transactional
    public void insertReport(WeeklyReport report, String currentUser) {
        report.setCreateBy(currentUser);
        weeklyReportMapper.insert(report);
    }

    @Override
    @Transactional
    public void batchSaveReports(List<WeeklyReport> reports, String currentUser) {
        for (WeeklyReport report : reports) {
            report.setCreateBy(currentUser);
            WeeklyReport existing = weeklyReportMapper.selectOne(new LambdaQueryWrapper<WeeklyReport>()
                    .eq(WeeklyReport::getProjectId, report.getProjectId())
                    .eq(WeeklyReport::getWeekNo, report.getWeekNo())
                    .eq(WeeklyReport::getCreateBy, currentUser));
            if (existing != null) {
                report.setId(existing.getId());
                weeklyReportMapper.updateById(report);
            } else {
                weeklyReportMapper.insert(report);
            }
        }
    }
}
