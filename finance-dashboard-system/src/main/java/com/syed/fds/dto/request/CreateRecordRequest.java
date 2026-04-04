package com.syed.fds.dto.request;

import java.math.BigDecimal;

import com.syed.fds.enums.TransactionCategory;
import com.syed.fds.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateRecordRequest {
	
	@NotNull(message = "Amount is required!!")
	@Positive(message = "Amount must be greater than zero!!")
	private BigDecimal amount;
	
	@NotNull(message = "Transaction type is required!!")
	private TransactionType type;
	
	private TransactionCategory category;
	
	private String description;
	
	public CreateRecordRequest() {

	}

	public CreateRecordRequest(
			@NotNull(message = "Amount is required!!") @Positive(message = "Amount must be greater than zero!!") BigDecimal amount,
			@NotNull(message = "Transaction type is required!!") TransactionType type, TransactionCategory category,
			String description) {
		super();
		this.amount = amount;
		this.type = type;
		this.category = category;
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}


