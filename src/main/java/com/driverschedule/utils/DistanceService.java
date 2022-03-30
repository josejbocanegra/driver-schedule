package com.driverschedule.utils;

import org.springframework.stereotype.Service;

@Service
public class DistanceService {
	public float getDistanceBetweenTwoPoints(int x1, int y1, int x2, int y2) {
		return (float) Math.sqrt( Math.pow((x2 - x1), 2)  +  Math.pow((y2 - y1), 2));
	}
}
