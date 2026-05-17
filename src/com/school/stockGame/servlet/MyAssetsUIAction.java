package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.jdbc.MyAssetDAOJdbc;
import com.school.stockGame.servlet.Action;

public class MyAssetsUIAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		return "MyAssets.jsp";
	}
}
