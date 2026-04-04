package com.syed.fds.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syed.fds.dto.request.CreateRecordRequest;
import com.syed.fds.dto.request.UpdateRecordRequest;
import com.syed.fds.dto.response.RecordResponse;
import com.syed.fds.service.FinancialRecordService;

import jakarta.validation.Valid;

@RequestMapping("/api/records")
@RestController
public class FinancialRecordController {
	
	private final FinancialRecordService recordService;
	
	public FinancialRecordController(FinancialRecordService recordService) {
		this.recordService = recordService;
	}
	
	@GetMapping
	public ResponseEntity<?> getAllRecords() {
		
		List<RecordResponse> responseBody = recordService.getAllRecords();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getRecordById(@PathVariable Long id) {
		
		RecordResponse responseBody = recordService.getRecordById(id);
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	@PostMapping 
	public ResponseEntity<?> createRecord(@Valid @RequestBody CreateRecordRequest recordRequest) {
		
		Map<String, Object> responseBody = recordService.createRecord(recordRequest);
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> createRecord(
			@PathVariable Long id,
			@Valid @RequestBody UpdateRecordRequest updateRequest) {
		
		Map<String, Object> responseBody = recordService.updateRecord(id, updateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> createRecord(@PathVariable Long id) {
		
		Map<String, Object> responseBody = recordService.deleteRecord(id);
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		
	}

}
