package com.example.projectprogressmanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer seqNo;
    private Integer projectYear;
    private String projectNo;
    private String projectName;
    private String phase;
    private String leaderName;
    private String devMembers;
    private String implMembers;
    private LocalDate phaseEndDate;
    private String remark;

    private String risk1;
    private String todo1;
    private String todoResult1;

    private LocalDate planStartDate;
    private LocalDate planEndDate;

    private String risk2;
    private String todo2;
    private String todoResult2;

    private String risk3;
    private String todo3;
    private String todoResult3;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String createBy;

    @TableLogic
    private Integer deleted;
}
