package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAOInterface;
import com.school.stockGame.dao.mybatis.StockDetailDAOMybatis;

public class StockBuyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
			StockDetailDAOInterface stockDetailDAO = new StockDetailDAOMybatis();
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
			
			session.setAttribute("info", studentInfo);
			session.setAttribute("Message", stockDetailDAO.setBuyOrder(studentId, buyPrice, buyAmount, stockNo));
			
			studentInfo.put("totalPoint", stockDetailDAO.getStudentPoint(studentId));
			session.setAttribute("info", studentInfo);
			
		return "controller?cmd=StockDetailUI&no=" + stockNo;
	}

}
