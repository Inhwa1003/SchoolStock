package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.StockDetailDAO;

public class StockDetailUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		try {
			StockDetailDAO stockDetailDAO = new StockDetailDAO();
			
			// 주식 기본 정보 가져오기 - 나중에 주식목록 페이지에서 stockNo request에 넘어온 값으로 조회
			Map<String, Object>m = stockDetailDAO.getStockInfo(3);
			request.setAttribute("stockName", m.get("name"));
			request.setAttribute("stockContent", m.get("content"));
			// 주식 이전가격 가져오기
			request.setAttribute("prevPrice", stockDetailDAO.getPervPrice(3));
			
			// 등락률
			request.setAttribute("percent", stockDetailDAO.getChangeRate(3));
			// 주식 이전가 대비
			request.setAttribute("changePrice", stockDetailDAO.getStockPriceChange(3));
			// 주식 현재가
			request.setAttribute("nowPrice", stockDetailDAO.getStockPrice(3));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "stockDetail.jsp";
	}

}
