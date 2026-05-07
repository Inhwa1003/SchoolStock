package com.school.stockGame.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MyAssetDAOInterface {
	int getMyValue(@Param("stockNo") int stockNo, 
			@Param("studentId") String studentId);
	int getPointValue(@Param("studentId") String studentId);
	int getTotalProfit(@Param("stockNo") int stockNo, 
			@Param("studentId") String studentId, 
			@Param("state") String state);
	int getTotalCoupon(@Param("studentId") String studentId);
	String getStockName(@Param("stockNo") int stockNo);
	int getStockAmount(@Param("studentId") String studentId, 
			@Param("stockNo") int stockNo, 
			@Param("state") String state);
	int getAveragePrice(@Param("studentId") String studentId, 
			@Param("stockNo") int stockNo, 
			@Param("state") String state, 
			@Param("content") String content);
	int getPurchasePrice(@Param("studnetId") String studentId, 
			@Param("stockNo") int stockNo, 
			@Param("state") String state, 
			@Param("content") String content);
	int getStockProfit(@Param("studentId") String studentId, 
			@Param("stockNo") int stockNo, 
			@Param("state") String state);
	List<Integer> getMyStockNos(@Param("studentId") String studentId, 
			@Param("state") String state);
}
