package com.driverschedule.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PointDTO {
	private List<AlfredDTO> alfreds = new ArrayList<>();
}
