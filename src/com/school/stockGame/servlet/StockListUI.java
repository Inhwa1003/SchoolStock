package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.dao.StockListDAO;

public class StockListUI implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockListDAO dao_list = new StockListDAO();
		StockDetailDAO dao_detail = new StockDetailDAO();
		HttpSession session = request.getSession();
		
		String studentId = (String) session.getAttribute("studentId");

        if (studentId == null) {
            studentId = "abc";
        }
        
        // 1. 주식명 목록조회
        List<String> stockNameList = dao_list.getStockNameList();
        // 2. JSP에 넘길 주식 목록 데이터 생성
        List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < stockNameList.size(); i++) {

            int stockNo = i + 1;
            
            String stockName = stockNameList.get(i);

            int currentPrice = dao_detail.getStockPrice(stockNo);
            int prevPrice = dao_detail.getPervPrice(stockNo);
            int priceChange = dao_detail.getStockPriceChange(stockNo);
            int changeRate = dao_detail.getChangeRate(stockNo);

            Map<String, Object> stock = new HashMap<String, Object>();
            
            stock.put("stockName", stockName);
            stock.put("currentPrice", currentPrice);
            stock.put("prevPrice", prevPrice);
            stock.put("priceChange", priceChange);
            stock.put("changeRate", changeRate);

            stockList.add(stock);
        }
        
        request.setAttribute("stockList", stockList);
        
		return "StockList.jsp";
	}

}


