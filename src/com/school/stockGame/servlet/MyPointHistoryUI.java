package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.MyPointHistoryDAO;

public class MyPointHistoryUI implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
			MyPointHistoryDAO dao = new MyPointHistoryDAO();
			HttpSession session = request.getSession();

	        String studentId = (String) session.getAttribute("studentId");

	        if (studentId == null) {
	            studentId = "abc";
	        }
	        List<Map<String, Object>> historyList = dao.getMyPointHistoryList(studentId);
		
	        request.setAttribute("historyList", historyList);
	        
		return "MyPointHistory.jsp";
	}

}


