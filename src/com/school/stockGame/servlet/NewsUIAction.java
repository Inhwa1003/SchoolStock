package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.jdbc.NewsDAO;

public class NewsUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		NewsDAO dao = new NewsDAO();
		request.setAttribute("newsList", dao.getNewsList());
		return "NewsList.jsp";
	}

}
