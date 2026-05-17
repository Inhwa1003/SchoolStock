package com.school.stockGame.dao;

import java.util.List;

public interface MyAssetDAOInterface {
	int getMyValue(int stockNo, String studentId);
	
	int getPointValue(String studentId);
	
	int getTotalProfit(int stockNo, String studentId, String state);
	
	int getTotalCoupon(String studentId);
	
	String getStockName(int stockNo);
	
	int getStockAmount(String studentId, int stockNo, String state);
	
	int getAveragePrice(String studentId, int stockNo, String state, String content);
	
	int getPurchasePrice(String studentId, int stockNo, String state, String content);
	
	int getStockProfit(String studentId, int stockNo, String state);
	
	List<Integer> getMyStockNos(String studentId, String state);
	

}