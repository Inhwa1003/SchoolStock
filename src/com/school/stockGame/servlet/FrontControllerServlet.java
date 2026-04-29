package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet { 

    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	
		request.getRequestDispatcher("/view/"+
			ActionFactory.getAction(request.getParameter("cmd")
					).execute(request))
		.forward(request, response);	
        

    }
}