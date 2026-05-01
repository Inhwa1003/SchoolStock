package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.vo.OrderVO;

public class StockOrderStatusAcion implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockDetailDAO stockDetailDAO = new StockDetailDAO();
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
