package com.bulmeong.basecamp.camp.dto;

import lombok.Data;

@Data
public class CampsiteImageDto {
    private int id;
    private int campsite_id;
	private String location;
	private String origin_filename;
}
