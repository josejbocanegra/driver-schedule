package com.driverschedule.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDistanceDetailDTO extends DriverDistanceDTO {
	private List<ScheduleDTO> schedules = new ArrayList<>();
}
