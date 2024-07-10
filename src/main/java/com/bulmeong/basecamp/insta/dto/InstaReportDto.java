package com.bulmeong.basecamp.insta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InstaReportDto {
    private int id;
    private int reporter_id;
    private int reported_id;
    private int report_reason_id;
    private String custom_reason_text;
    private Date created_at;
}
