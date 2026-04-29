package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.dao.StockListDAO;

public class StockListUI implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockListDAO dao = new StockListDAO();
		StockDetailDAO dao1 = new StockDetailDAO();
		
		
		return "StockList.jsp";
	}

}
