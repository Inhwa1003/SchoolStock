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
			// 주식 기본 정보 가져오기 - 나중에 주식목록 페이지에서 stockNo request에 넘어온 값으로 조회
			Map<String, Object> m = stockDetailDAO.getStockInfo(stockNo);
			request.setAttribute("stockName", m.get("name"));
			request.setAttribute("stockContent", m.get("content"));
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
