package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.school.stockGame.dao.StockDetailDAO;

public class GetNowPriceAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
        String studentId = (String) session.getAttribute("studentId");
        
        // 세션 체크
        if (studentId == null) {
            return "controller?cmd=LoginUI";
        }
        
        // 종목 번호 받기
        int stockNo = Integer.parseInt(request.getParameter("stockNo"));
        
        // DAO에서 현재가만 조회
        StockDetailDAO stockDetailDAO = new StockDetailDAO();
        int nowPrice = stockDetailDAO.getStockPrice(stockNo);
        
        // Map으로 감싸서 JSON 변환
        Map<String, Object> result = new HashMap<>();
        result.put("nowPrice", nowPrice);
        
        Gson gson = new Gson();
        String json = gson.toJson(result);
        
        request.setAttribute("jsonData", json);
        return null;
	}

}
