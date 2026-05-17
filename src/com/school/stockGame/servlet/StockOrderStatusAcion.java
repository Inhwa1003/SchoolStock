package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.school.stockGame.dao.StockDetailDAOInterface;
import com.school.stockGame.dao.jdbc.StockDetailDAO;
import com.school.stockGame.dao.mybatis.StockDetailDAOMybatis;
import com.school.stockGame.vo.OrderVO;

public class StockOrderStatusAcion implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String studentId = (String) session.getAttribute("studentId");
		//세션 체크
		if(studentId == null){
			return "controller?cmd=LoginUI";
		}
		
		StockDetailDAOInterface stockDetailDAO = new StockDetailDAOMybatis();
		List<OrderVO> list = null;
		
		int stockNo = Integer.parseInt(request.getParameter("no"));
		if(request.getParameter("type").equals("sell")){
			list = stockDetailDAO.getTotalSellOrder(stockNo);
		}else{
			list = stockDetailDAO.getTotalBuyOrder(stockNo);
		}
		Gson gson = new Gson();
		String json = gson.toJson(list);
		
		request.setAttribute("jsonData", json);
		return null;
	}

}
