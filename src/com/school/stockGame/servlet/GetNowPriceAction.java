package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.school.stockGame.dao.StockDetailDAO;

public class GetNowPriceAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		// ★ 빈 값 방어 (이게 핵심!) ★
		String stockNoStr = request.getParameter("stockNo");
		
		if (stockNoStr == null || stockNoStr.trim().isEmpty()) {
			Map<String, Object> emptyResult = new HashMap<>();
			emptyResult.put("nowPrice", 0);
			request.setAttribute("jsonData", new Gson().toJson(emptyResult));
			return null;
		}
		
		// 종목 번호 받기
		int stockNo = Integer.parseInt(stockNoStr);
		
		// DAO에서 현재가만 조회
		StockDetailDAO stockDetailDAO = new StockDetailDAO();
		int nowPrice = stockDetailDAO.getStockPrice(stockNo);
		
		// 거래 없으면 발행가 사용
		if (nowPrice == 0) {
			Map<String, Object> pubInfo = stockDetailDAO.getStockPubInfo(stockNo);
			if (pubInfo != null && pubInfo.get("pubPrice") != null) {
				nowPrice = ((Number) pubInfo.get("pubPrice")).intValue();
			}
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("nowPrice", nowPrice);
		
		Gson gson = new Gson();
		String json = gson.toJson(result);
		
		request.setAttribute("jsonData", json);
		return null;
	}
}