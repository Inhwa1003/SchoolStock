package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		if(session !=null){
			//session.removeAttribute("loginOK"); //1
			session.invalidate();//2
		}		
		return "index.jsp";
	}

}
