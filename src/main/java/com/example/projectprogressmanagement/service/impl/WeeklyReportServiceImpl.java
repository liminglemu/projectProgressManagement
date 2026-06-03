package com.example.projectprogressmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.projectprogressmanagement.entity.WeeklyReport;
import com.example.projectprogressmanagement.mapper.WeeklyReportMapper;
import com.example.projectprogressmanagement.service.WeeklyReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyReportServiceImpl extends ServiceImpl<WeeklyReportMapper, WeeklyReport> implements WeeklyReportService {

    @Override
    public List<WeeklyReport> getByProjectId(Long projectId, String currentUser) {
        return list(new LambdaQueryWrapper<WeeklyReport>()
                .eq(WeeklyReport::getProjectId, projectId)
                .eq(WeeklyReport::getCreateBy, currentUser)
                .orderByAsc(WeeklyReport::getWeekNo));
    }

    @Override
    public WeeklyReport save(WeeklyReport report, String currentUser) {
        report.setCreateBy(currentUser);
        save(report);
        return report;
    }

    @Override
    public List<WeeklyReport> batchSave(List<WeeklyReport> reports, String currentUser) {
        for (WeeklyReport report : reports) {
            report.setCreateBy(currentUser);
            WeeklyReport existing = getOne(new LambdaQueryWrapper<WeeklyReport>()
                    .eq(WeeklyReport::getProjectId, report.getProjectId())
                    .eq(WeeklyReport::getWeekNo, report.getWeekNo())
                    .eq(WeeklyReport::getCreateBy, currentUser));
            if (existing != null) {
                report.setId(existing.getId());
                updateById(report);
            } else {
                save(report);
            }
        }
        return reports;
    }
}
