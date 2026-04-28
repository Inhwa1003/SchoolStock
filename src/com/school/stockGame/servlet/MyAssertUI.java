package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class MyAssertUI implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		return "my_assets.jsp";
	}

}
