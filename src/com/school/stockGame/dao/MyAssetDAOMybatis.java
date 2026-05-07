package com.school.stockGame.dao;

import java.util.List;

public class MyAssetDAOMybatis implements MyAssetDAOInterface {

	@Override
	public int getMyValue(int stockNo, String studentId) {
		
		return 0;
	}

	@Override
	public int getPointValue(String studentId) {
		
		return 0;
	}

	@Override
	public int getTotalProfit(int stockNo, String studentId, String state) {
		
		return 0;
	}

	@Override
	public int getTotalCoupon(String studentId) {
		
		return 0;
	}

	@Override
	public String getStockName(int stockNo) {
		
		return null;
	}

	@Override
	public int getStockAmount(String studentId, int stockNo, String state) {
		
		return 0;
	}

	@Override
	public int getAveragePrice(String studentId, int stockNo, String state, String content) {
		
		return 0;
	}

	@Override
	public int getPurchasePrice(String studentId, int stockNo, String state, String content) {
		
		return 0;
	}

	@Override
	public int getStockProfit(String studentId, int stockNo, String state) {
		
		return 0;
	}

	@Override
	public List<Integer> getMyStockNos(String studentId, String state) {
		
		return null;
	}

}
