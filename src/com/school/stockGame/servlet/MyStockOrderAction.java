package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.vo.OrderVO;

public class MyStockOrderAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		int stockNo = Integer.parseInt(request.getParameter("no"));
		String studentId = (String) session.getAttribute("studentId");
		
		StockDetailDAO stockDetailDAO = new StockDetailDAO();
	
		List<OrderVO> list = stockDetailDAO.getTotalMyOrder(stockNo, studentId);
		
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
	
		request.setAttribute("jsonData", json);
		
		return null;
	}

}
