package com.driverschedule.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDTO {
	private List<DriverDTO> alfreds = new ArrayList<DriverDTO>();
}