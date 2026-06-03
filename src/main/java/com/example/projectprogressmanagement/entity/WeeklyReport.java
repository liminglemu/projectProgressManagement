package com.example.projectprogressmanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("weekly_report")
public class WeeklyReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Integer weekNo;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String content;
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
