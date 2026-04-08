package com.syed.fds.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syed.fds.service.DashboardService;

@RequestMapping("/api/dashboard")
@RestController
public class DashboardController {
	
	private final DashboardService dashboardService;
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("/summary")
	public ResponseEntity<?> getDashboardSummary() {
		return ResponseEntity.status(HttpStatus.OK).body(dashboardService.getDashboardSummary()); 
	}

}
