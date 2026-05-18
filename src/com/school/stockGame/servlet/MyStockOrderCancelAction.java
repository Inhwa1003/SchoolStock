package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.jdbc.StockDetailDAOJdbc;

public class MyStockOrderCancelAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockDetailDAOJdbc stockDetailDAO = new StockDetailDAOJdbc();
		HttpSession session = request.getSession();
		Map<String, Object> studentInfo = (Map<String, Object>) session.getAttribute("info");
		
		
		String studentId = (String) session.getAttribute("studentId");
		
		//세션 체크
		if(studentId == null){
			return "controller?cmd=LoginUI";
		}
		
		int orderNo = Integer.parseInt(request.getParameter("orderNo"));
		int stockNo = Integer.parseInt(request.getParameter("stockNo"));
		
		stockDetailDAO.setOrderStateCancel(orderNo);
		
		return "controller?cmd=StockDetailUI&no=" + stockNo;
	}

}
