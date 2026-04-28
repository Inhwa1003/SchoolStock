package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface Action{
	String execute(HttpServletRequest request) throws ServletException, IOException;
}