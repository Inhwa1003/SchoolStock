package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.jdbc.MemberDAOJdbc;

public class LoginAction implements Action {

	@Override
	public String execute(HttpServletRequest request) 
			throws ServletException, IOException {
		String url="Login.jsp";
		String studentId=request.getParameter("studentId");
		String password=request.getParameter("password");

		try{
			MemberDAOJdbc dao=new MemberDAOJdbc();
			Map<String, Object> m=dao.login(studentId, password);
			if(m != null && m.size() > 0){
				HttpSession session=request.getSession(true);
				session.setAttribute("studentId", studentId);
				session.setAttribute("loginOK", studentId);
				session.setAttribute("info", m);
				return "controller?cmd=MyAssetsAction";
			}
			else
				request.setAttribute("errorMessage", "아이디 또는 비밀번호를 확인해 주세요.");
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

}
