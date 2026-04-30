package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.dao.StockListDAO;

public class StockPriceAjax implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockListDAO daoList = new StockListDAO();
		StockDetailDAO daoDetail = new StockDetailDAO();

		// 1. 등록된 주식명 목록 조회
		List<String> stockNameList = daoList.getStockNameList();

		// 2. JSON으로 보낼 데이터 생성
		List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < stockNameList.size(); i++) {
			String stockName = stockNameList.get(i);

			// 현재 요구사항상 stock_no는 1번부터 순서대로 존재
			int stockNo = i + 1;

			int currentPrice = daoDetail.getStockPrice(stockNo);
			int prevPrice = daoDetail.getPervPrice(stockNo);
			int priceChange = daoDetail.getStockPriceChange(stockNo);
			int changeRate = daoDetail.getChangeRate(stockNo);

			Map<String, Object> stock = new LinkedHashMap<String, Object>();

			// JS에서 stock.stockName으로 쓰고 있으므로 key 이름 반드시 stockName
			stock.put("stockName", stockName);
			stock.put("currentPrice", currentPrice);
			stock.put("prevPrice", prevPrice);
			stock.put("priceChange", priceChange);
			stock.put("changeRate", changeRate);

			stockList.add(stock);
		}

		// 3. Java 객체를 JSON 문자열로 변환
		Gson gson = new Gson();
		String json = gson.toJson(stockList);

		System.out.println("StockPriceAjax Json="+json);
		// 4. JSP에서 출력할 수 있도록 request에 저장
		request.setAttribute("jsonData", json);

		
		return null;
	}
}