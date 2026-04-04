package com.syed.fds.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.syed.fds.enums.TransactionCategory;
import com.syed.fds.enums.TransactionType;

public class RecordResponse {
	
	private Long id;
	private BigDecimal amount;
	private String createdBy;
	private TransactionType type;
	private TransactionCategory category;
	private LocalDate date;
	private LocalDateTime createdAt;
	private String description;
	
	public RecordResponse() {
		
	}

	public RecordResponse(Long id, BigDecimal amount, String createdBy, TransactionType type,
			TransactionCategory category, LocalDate date, LocalDateTime createdAt, String description) {
		super();
		this.id = id;
		this.amount = amount;
		this.createdBy = createdBy;
		this.type = type;
		this.category = category;
		this.date = date;
		this.createdAt = createdAt;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
