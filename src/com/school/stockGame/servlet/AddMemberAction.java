package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.MemberDAO;

public class AddMemberAction implements Action {

	@Override
	public String execute(HttpServletRequest request) 
			throws ServletException, IOException {
		System.out.println("===> AddMemberAction 진입 성공!"); // 이 글자가 찍히는지 확인
		String url="AddMember.jsp";
		try {
			MemberDAO dao=new MemberDAO();
			System.out.println("===> AddMemberAction 진입 성공!"); // 이 글자가 찍히는지 확인
			if(dao.addMember(request.getParameter("studentId"),
					request.getParameter("password"),
					request.getParameter("name"),
					Integer.parseInt(request.getParameter("grade")),
					request.getParameter("className"),
					Integer.parseInt(request.getParameter("classNumber"))))
				url="Login.jsp";
			else
				request.setAttribute("errorMessage", "회원가입실패");			
		} catch (Exception e) {
			e.printStackTrace();

		}

		return url;
	}

}
