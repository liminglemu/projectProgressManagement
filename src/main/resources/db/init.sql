USE project_mgmt;

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码(MD5)',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '角色',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

CREATE TABLE IF NOT EXISTS project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seq_no INT COMMENT '序号',
    project_year INT COMMENT '项目年度',
    project_no VARCHAR(50) COMMENT '项目编号',
    project_name VARCHAR(200) NOT NULL COMMENT '项目名称',
    phase VARCHAR(20) COMMENT '项目阶段',
    leader_name VARCHAR(50) COMMENT '项目负责人',
    dev_members VARCHAR(200) COMMENT '开发成员',
    impl_members VARCHAR(200) COMMENT '实施成员',
    phase_end_date DATE COMMENT '阶段完成时间',
    remark TEXT COMMENT '备注',
    risk1 VARCHAR(10) COMMENT '有无风险1',
    todo1 TEXT COMMENT '待办事项1',
    todo_result1 TEXT COMMENT '事项处理结果1',
    plan_start_date DATE COMMENT '计划开展时间',
    plan_end_date DATE COMMENT '计划结束时间',
    risk2 VARCHAR(10) COMMENT '有无风险2',
    todo2 TEXT COMMENT '待办事项2',
    todo_result2 TEXT COMMENT '事项处理结果2',
    risk3 VARCHAR(10) COMMENT '有无风险3',
    todo3 TEXT COMMENT '待办事项3',
    todo_result3 TEXT COMMENT '事项处理结果3',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_by VARCHAR(50) COMMENT '创建人',
    deleted TINYINT DEFAULT 0,
    INDEX idx_leader (leader_name),
    INDEX idx_phase (phase)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

CREATE TABLE IF NOT EXISTS weekly_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    week_no INT NOT NULL COMMENT '第几周(1-4)',
    week_start DATE COMMENT '周开始日期',
    week_end DATE COMMENT '周结束日期',
    content TEXT COMMENT '本周工作内容',
    create_by VARCHAR(50) COMMENT '填写人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0,
    UNIQUE KEY uk_project_week_user (project_id, week_no, create_by),
    INDEX idx_project (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='周报表';

-- 初始用户 (密码MD5: 123456 -> e10adc3949ba59abbe56e057f20f883e)
INSERT INTO sys_user (username, password, real_name, role) VALUES
('chenlingbin', 'e10adc3949ba59abbe56e057f20f883e', '陈凌斌', 'USER'),
('ditongjuan', 'e10adc3949ba59abbe56e057f20f883e', '邸同娟', 'USER'),
('shiqiang', 'e10adc3949ba59abbe56e057f20f883e', '施其强', 'USER'),
('majian', 'e10adc3949ba59abbe56e057f20f883e', '马健', 'USER'),
('jizhong', 'e10adc3949ba59abbe56e057f20f883e', '嵇众', 'USER'),
('daishacong', 'e10adc3949ba59abbe56e057f20f883e', '代沙聪', 'USER'),
('dingju', 'e10adc3949ba59abbe56e057f20f883e', '丁菊', 'USER'),
('wangxudong', 'e10adc3949ba59abbe56e057f20f883e', '王旭东', 'USER'),
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'ADMIN');

-- 初始项目数据
INSERT INTO project (seq_no, project_year, project_no, project_name, phase, leader_name, dev_members, impl_members, phase_end_date, remark, risk1, risk2, risk3) VALUES
(1, 2024, 'RZ24014', '苏州世嘉定制化MES项目', '运维', '陈凌斌', NULL, NULL, '2025-07-30', NULL, NULL, 'NG', 'NG'),
(2, 2024, 'RZ24015', '江苏新安电器定制化SRM项目', '运维', '邸同娟', '陈凤杰', NULL, '2025-07-30', NULL, 'OK', 'OK', 'OK'),
(3, 2024, 'RZ24017', '北汽株洲PMC项目', '运维', '嵇众', '陈凌斌', '赵文卓', '2025-07-30', NULL, 'OK', 'OK', 'OK'),
(4, 2024, 'RZ25001', '长春医疗MAE项目', '运维', '陈凌斌', NULL, NULL, '2025-07-30', NULL, 'OK', 'OK', 'OK'),
(5, 2025, 'RZ25002', '洛希二期产线项目', '运维', '陈凌斌', NULL, NULL, '2025-07-30', NULL, 'OK', 'OK', 'OK'),
(6, 2025, 'RZ25003', '考比锐特坤泰定子线MES项目', '开发', '嵇众', '杨安、陈凌斌', NULL, '2025-07-30', '20260509产线要整体移位MES联调时间待定', 'NG', 'OK', 'OK'),
(7, 2025, 'RZ25005', '财务中间平台开发', '运维', '邸同娟', '陈凤杰', NULL, '2025-07-30', NULL, 'OK', 'OK', 'NG'),
(8, 2025, 'RZ25011', '青岛天润工业产线MES项目', '验收', '马健', '张珂', NULL, '2025-07-30', NULL, 'OK', 'OK', 'OK'),
(9, 2025, 'RZ25012', '比亚迪高标MES项目', '上线', '嵇众', '陈凤杰、陈凌斌、杨安', NULL, '2025-07-30', NULL, 'OK', 'OK', 'OK'),
(10, 2025, 'RZ25023', '坤泰变速器淮北MES项目', '上线', '嵇众', '刘昊，陈凌斌、杨安', NULL, '2025-07-15', NULL, 'OK', 'OK', 'OK'),
(11, 2025, 'RZ25023', '坤泰变速器淮南MES项目', '开发', '嵇众', '杨安', NULL, NULL, '20260509目前产线动力柜刚上电，MES联调时间待定', NULL, NULL, NULL),
(12, 2025, 'RZ25025', '烟台TS11MES改造', '运维', '王旭东', '杨安', NULL, '2025-07-15', NULL, 'OK', 'OK', 'OK'),
(13, 2025, 'RZ25027', '宁德产线MES调试项目-VW25001', '启动', '王旭东', '王旭东', NULL, NULL, NULL, NULL, 'OK', 'OK'),
(14, 2025, 'RZ25028', '零跑工厂MES系统项目', '启动', '马健', '刘昊', NULL, NULL, NULL, NULL, 'OK', 'OK'),
(15, 2025, 'RZ25029', '淮北坤泰变速器KP31MES项目', '开发', '嵇众', '杨安、陈凌斌', NULL, '2026-04-07', '20260509产线在DMC空转完成', NULL, 'OK', 'OK'),
(16, 2025, 'RZ25030', '上齿NP51MES项目', '启动', '陈凌斌', NULL, NULL, NULL, NULL, NULL, 'OK', 'OK'),
(17, 2025, 'RZ25032', '新安电器可视化项目', '启动', '陈凤杰', NULL, NULL, NULL, NULL, NULL, 'OK', 'OK'),
(18, 2026, NULL, '淮北泰集MOM推广', '启动', '嵇众', '施其强、黎向阳', NULL, NULL, NULL, NULL, 'OK', 'OK'),
(19, 2026, 'RZ26004', '捷云发动机MES项目', '上线', '代沙聪', '陈凌斌', '曾鸿麟', NULL, NULL, NULL, NULL, NULL);
