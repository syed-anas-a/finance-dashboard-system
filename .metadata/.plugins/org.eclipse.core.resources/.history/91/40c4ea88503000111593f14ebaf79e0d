package com.syed.fds.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syed.fds.entity.FinancialRecord;
import com.syed.fds.enums.TransactionCategory;
import com.syed.fds.enums.TransactionType;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> { 
	
	List<FinancialRecord> findByCreatedBy_Id(Long id);
	
	List<FinancialRecord> findByType(TransactionType type);
	
	List<FinancialRecord> findByCategory(TransactionCategory category);
	
	List<FinancialRecord> findByDateBetween(LocalDate from, LocalDate to);

}
