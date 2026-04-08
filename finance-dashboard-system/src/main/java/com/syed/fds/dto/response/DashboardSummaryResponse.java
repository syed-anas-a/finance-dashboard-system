package com.syed.fds.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardSummaryResponse {
	
	private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
    private List<Map<String, Object>> categoryBreakdown;
    private List<Map<String, Object>> monthlyTrends;
    private List<Map<String, Object>> recentActivity;
	
	public DashboardSummaryResponse() {

	}

	public DashboardSummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal netBalance,
			List<Map<String, Object>> categoryBreakdown, List<Map<String, Object>> monthlyTrends,
			List<Map<String, Object>> recentActivity) {
		super();
		this.totalIncome = totalIncome;
		this.totalExpense = totalExpense;
		this.netBalance = netBalance;
		this.categoryBreakdown = categoryBreakdown;
		this.monthlyTrends = monthlyTrends;
		this.recentActivity = recentActivity;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}

	public BigDecimal getNetBalance() {
		return netBalance;
	}

	public void setNetBalance(BigDecimal netBalance) {
		this.netBalance = netBalance;
	}

	public List<Map<String, Object>> getCategoryBreakdown() {
		return categoryBreakdown;
	}

	public void setCategoryBreakdown(List<Map<String, Object>> categoryBreakdown) {
		this.categoryBreakdown = categoryBreakdown;
	}

	public List<Map<String, Object>> getMonthlyTrends() {
		return monthlyTrends;
	}

	public void setMonthlyTrends(List<Map<String, Object>> monthlyTrends) {
		this.monthlyTrends = monthlyTrends;
	}

	public List<Map<String, Object>> getRecentActivity() {
		return recentActivity;
	}

	public void setRecentActivity(List<Map<String, Object>> recentActivity) {
		this.recentActivity = recentActivity;
	}
	

}