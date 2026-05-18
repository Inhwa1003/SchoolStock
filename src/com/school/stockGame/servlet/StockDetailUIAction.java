package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAOInterface;
import com.school.stockGame.dao.mybatis.StockDetailDAOMybatis;

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
			StockDetailDAOInterface stockDetailDAO = new StockDetailDAOMybatis();
			int stockNo = Integer.parseInt(request.getParameter("no"));
			Map<String, Object> stockInfo = stockDetailDAO.getStockInfo(stockNo);
			Map<String, Object> pubInfo = stockDetailDAO.getStockPubInfo(stockNo);
			
			int nowPrice = stockDetailDAO.getStockPrice(stockNo);
			
			// MyBatis에서 반환된 Map은 값의 타입이 Number(BigDecimal 등)일 수 있으므로 
			// Number로 먼저 캐스팅한 후 intValue()를 호출하여 안전하게 변환합니다.
			int pubPrice = 0;
			if(pubInfo != null && pubInfo.get("pubPrice") != null) {
				pubPrice = ((Number)pubInfo.get("pubPrice")).intValue();
			}
			
			request.setAttribute("nowPrice", stockDetailDAO.getStockPrice(stockNo));
			
			if(nowPrice == 0){
				request.setAttribute("nowPrice", pubPrice);
			}
			// 주식 이전가격 가져오기
			request.setAttribute("prevPrice", stockDetailDAO.getPervPrice(stockNo));
			
			// stockInfo 널 체크
			if(stockInfo != null) {
				request.setAttribute("stockName", stockInfo.get("name"));
				request.setAttribute("stockContent", stockInfo.get("content"));
			}
		

			// 등락률
			request.setAttribute("percent", stockDetailDAO.getChangeRate(stockNo));
			// 주식 이전가 대비
			request.setAttribute("changePrice", stockDetailDAO.getStockPriceChange(stockNo));
			// 주식 현재가
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "StockDetail.jsp";
	}

}