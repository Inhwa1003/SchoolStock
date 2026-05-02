package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.dao.StockListDAO;
import com.school.stockGame.vo.StockVO;

public class StockListUI implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		StockListDAO dao_list = new StockListDAO();
		StockDetailDAO dao_detail = new StockDetailDAO();
		HttpSession session = request.getSession();
		
		String studentId = (String) session.getAttribute("studentId");

        if (studentId == null) {
            studentId = "abc";
        }
        
        // 1. 주식명 목록조회
        List<StockVO> stockNameList = dao_list.getStockNameList();
        // 2. JSP에 넘길 주식 목록 데이터 생성
        List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < stockNameList.size(); i++) {

            //int stockNo = i + 1;
            
        	int stockNo = stockNameList.get(i).getStockNo();
            String stockName = stockNameList.get(i).getName();

            // 발행 잔량이 1개 이상일 경우엔, 현재가격은 발행 가격으로 표시한다.
            // 발행 잔량이 0개일 때부터 학생들이 거래가 이루어지기 때문에, 최신 거래 가격이 현재가격으로 이뤄진다.
            
            // 주식 발행 정보 가져오기
            Map<String, Object> pubInfo = dao_detail.getStockPubInfo(stockNo);

            int pubAmount = 0;
            int pubPrice = 0;

            // 발행 잔량과 가격이 null이 아닐 때
            if (pubInfo.get("pubAmount") != null) {
                pubAmount = ((Number) pubInfo.get("pubAmount")).intValue();
            }

            if (pubInfo.get("pubPrice") != null) {
                pubPrice = ((Number) pubInfo.get("pubPrice")).intValue();
            }

            int currentPrice = 0;

            // 발행잔량이 1이상일 때. 현재가격을 발행 가격으로 표시해주는 조건 넣어줌.
            if (pubAmount > 0) {
                currentPrice = pubPrice;
            } else {
                currentPrice = dao_detail.getStockPrice(stockNo);
            }

            int prevPrice = dao_detail.getPervPrice(stockNo);
            int priceChange = dao_detail.getStockPriceChange(stockNo);
            int changeRate = dao_detail.getChangeRate(stockNo);
            

            Map<String, Object> stock = new HashMap<String, Object>();
            
            stock.put("stockNo", stockNo);
            stock.put("stockName", stockName);
            stock.put("currentPrice", currentPrice);
            stock.put("prevPrice", prevPrice);
            stock.put("priceChange", priceChange);
            stock.put("changeRate", changeRate);

            stockList.add(stock);
        }
        
        request.setAttribute("stockList", stockList);
        
		return "StockList.jsp";
	}

}


