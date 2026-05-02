package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAO;

public class StockSellAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockDetailDAO stockDetailDAO = new StockDetailDAO();
		HttpSession session = request.getSession();
		Map<String, Object> studentInfo = (Map<String, Object>) session.getAttribute("info");
		
		String studentId = (String) session.getAttribute("studentId");
		//세션 체크
		if(studentId == null){
			return "controller?cmd=LoginUI";
		}
		
		int sellPrice = Integer.parseInt(request.getParameter("sellPrice"));
		int sellAmount = Integer.parseInt(request.getParameter("sellAmount"));
		int stockNo = Integer.parseInt(request.getParameter("stockNo"));
		int totalPoint = (int) studentInfo.get("totalPoint");
		
		studentInfo.put("totalPoint", totalPoint += (sellPrice*sellAmount));
		session.setAttribute("info", studentInfo);
		
		request.setAttribute("ordereOk", stockDetailDAO.setSellOrder(studentId, sellPrice, sellAmount, stockNo));
	return "controller?cmd=StockDetailUI&no=" + stockNo;
	}

}
