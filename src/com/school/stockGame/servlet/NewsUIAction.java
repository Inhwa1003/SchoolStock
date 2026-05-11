package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.jdbc.NewsDAOJdbc;

public class NewsUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		NewsDAOJdbc dao = new NewsDAOJdbc();
		request.setAttribute("newsList", dao.getNewsList());
		return "NewsList.jsp";
	}

}
