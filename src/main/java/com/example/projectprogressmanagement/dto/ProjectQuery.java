package com.example.projectprogressmanagement.dto;

import lombok.Data;

@Data
public class ProjectQuery {
    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private String keyword;
    private String phase;
    private String leaderName;
}
