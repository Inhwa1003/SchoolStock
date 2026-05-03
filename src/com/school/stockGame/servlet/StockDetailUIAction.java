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
			
			int pubAmount = (int) pubInfo.get("pubAmount");
			int pubPrice = (int)pubInfo.get("pubPrice");
			
			// 주식 발행 개수가 0이고 최초의 거래내역이 없을때 이전 발행 가격으로 이전가 현재가 띄워주기
			if(stockDetailDAO.getStockPrice(stockNo) == 0 && pubAmount == 0){
				request.setAttribute("prevPrice", pubPrice);
				request.setAttribute("nowPrice", pubPrice);
			}
			// 발행 잔량이 남아 있으면 발행 가격으로 현재가 이전가 고정
			if(pubAmount > 0){
				request.setAttribute("prevPrice", pubPrice);
				request.setAttribute("nowPrice", pubPrice);
			}
			request.setAttribute("stockName", stockInfo.get("name"));
			request.setAttribute("stockContent", stockInfo.get("content"));
			// 주식 이전가격 가져오기
			request.setAttribute("prevPrice", stockDetailDAO.getPervPrice(stockNo));

			// 등락률
			request.setAttribute("percent", stockDetailDAO.getChangeRate(stockNo));
			// 주식 이전가 대비
			request.setAttribute("changePrice", stockDetailDAO.getStockPriceChange(stockNo));
			// 주식 현재가
			request.setAttribute("nowPrice", stockDetailDAO.getStockPrice(stockNo));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "StockDetail.jsp";
	}

}
