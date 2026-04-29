package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//업무의 표준- 서블릿에서 request 기능만 담당하는 Action  //SpringMVC N개 제공
public interface Action {
	/** url(view page)을 문자열로 리턴 */
	String execute(HttpServletRequest request) 
			throws ServletException, IOException;
}