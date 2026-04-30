package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.MemberDAO;

public class IdCheckAction implements Action {

	@Override
	public String execute(HttpServletRequest request) 
			throws ServletException, IOException {
		String studentId=request.getParameter("studentId");
		boolean result=new MemberDAO().idCheck(studentId);
		request.setAttribute("result", result);
		return "IdJsonResult.jsp";
	}

}
