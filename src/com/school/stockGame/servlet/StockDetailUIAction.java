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
			
			// 주식 기본 정보 가져오기 - 나중에 주식목록 페이지에서 주식 아이디 request에 넘어온 값으로 조회
			Map<String, Object>m = stockDetailDAO.getStockInfo(1);
			request.setAttribute("stockName", m.get("name"));
			request.setAttribute("stockContent", m.get("content"));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "stockDetail.jsp";
	}

}
