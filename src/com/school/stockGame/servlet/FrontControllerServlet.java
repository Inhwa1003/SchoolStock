package com.school.stockGame.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontControllerServlet
 */
@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String path = ActionFactory.getAction(request.getParameter("cmd")).execute(request);
		
		if(path == null){
			String jsonData = (String) request.getAttribute("jsonData");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(jsonData);
		}else
			request.getRequestDispatcher("/view/" + path).forward(request, response);
	}
	
}
