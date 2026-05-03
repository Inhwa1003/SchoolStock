package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAO;

public class StockDetailUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {

		HttpSession session = request.getSession();

		String studentId = (String) session.getAttribute("studentId");
		// 세션 체크
		if (studentId == null) {
			return "controller?cmd=LoginUI";
		}

		try {
			StockDetailDAO stockDetailDAO = new StockDetailDAO();
			int stockNo = Integer.parseInt(request.getParameter("no"));
			Map<String, Object> stockInfo = stockDetailDAO.getStockInfo(stockNo);
			Map<String, Object> pubInfo = stockDetailDAO.getStockPubInfo(stockNo);
			
			int nowPrice = stockDetailDAO.getStockPrice(stockNo);
			int pubPrice = (int)pubInfo.get("pubPrice");
			
			request.setAttribute("nowPrice", stockDetailDAO.getStockPrice(stockNo));
			
			if(nowPrice == 0){
				request.setAttribute("nowPrice", pubPrice);
			}
			// 주식 이전가격 가져오기
			request.setAttribute("prevPrice", stockDetailDAO.getPervPrice(stockNo));
			request.setAttribute("stockName", stockInfo.get("name"));
			request.setAttribute("stockContent", stockInfo.get("content"));
		

			// 등락률
			//request.setAttribute("percent", stockDetailDAO.getChangeRate(stockNo));
			// 주식 이전가 대비
			//request.setAttribute("changePrice", stockDetailDAO.getStockPriceChange(stockNo));
			// 주식 현재가
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "StockDetail.jsp";
	}

}
