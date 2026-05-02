package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAO;

public class StockBuyAction implements Action {

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
			
			int buyPrice = Integer.parseInt(request.getParameter("buyPrice"));
			int buyAmount = Integer.parseInt(request.getParameter("buyAmount"));
			int stockNo = Integer.parseInt(request.getParameter("stockNo"));
			int totalPoint = (int) studentInfo.get("totalPoint");
			
			studentInfo.put("totalPoint", totalPoint -= (buyPrice*buyAmount));
			session.setAttribute("info", studentInfo);
			
			
			request.setAttribute("orderOk", stockDetailDAO.setBuyOrder(studentId, buyPrice, buyAmount, stockNo));
		return "controller?cmd=StockDetailUI&no=" + stockNo;
	}

}
