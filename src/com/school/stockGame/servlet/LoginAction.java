package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.MemberDAO;

public class LoginAction implements Action {

	@Override
	public String execute(HttpServletRequest request) 
			throws ServletException, IOException {
		String url="Login.jsp";
		String studentId=request.getParameter("studentId");
		String password=request.getParameter("password");

		try{
			MemberDAO dao=new MemberDAO();
			Map<String, Object> m=dao.login(studentId, password);
			if(m.keySet().size()>0){
				HttpSession session=request.getSession(true);
				session.setAttribute("loginOK", studentId);
				session.setAttribute("name", m.get("name"));
				session.setAttribute("grade", m.get("grade"));
				session.setAttribute("className", m.get("class"));
				session.setAttribute("classNumber", m.get("classNumber"));
				session.setAttribute("totalPoint", m.get("totalPoint"));
				session.setAttribute("info", m);
				url="MyAssets.jsp";
			}
			else
				request.setAttribute("errorMessage", "로그인 실패");
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}

}
