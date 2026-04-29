package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.MemberDAO;

public class AddMemberAction implements Action {

	@Override
	public String execute(HttpServletRequest request) 
			throws ServletException, IOException {
		String url="AddMember.jsp";
		try {
			MemberDAO dao=new MemberDAO();
			if(dao.addMember(request.getParameter("studentId"),
					request.getParameter("password"),
					request.getParameter("name"),
					Integer.parseInt(request.getParameter("grade")),
					request.getParameter("className"),
					Integer.parseInt(request.getParameter("classNumber"))))
				url="Login.jsp";
			else
				request.setAttribute("errorMessage", "회원가입실패");
			url="AddMember.jsp";
			
		} catch (Exception e) {
			e.printStackTrace();

		}

		return url;
	}

}
