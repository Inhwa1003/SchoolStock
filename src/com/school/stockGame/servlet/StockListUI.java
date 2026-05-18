package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.jdbc.StockDetailDAOJdbc;
import com.school.stockGame.dao.jdbc.StockListDAOJdbc;
import com.school.stockGame.vo.StockVO;

public class StockListUI implements Action {
	// 주식 목록 첫 화면용

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {

        StockListDAOJdbc dao_list = new StockListDAOJdbc();
        StockDetailDAOJdbc dao_detail = new StockDetailDAOJdbc();
        HttpSession session = request.getSession();

        String studentId = (String) session.getAttribute("studentId");

		//세션 체크
		if(studentId == null){
			return "controller?cmd=LoginUI";
		}
      
        // 1. 주식명 목록조회
        List<StockVO> stockNameList = dao_list.getStockNameList();

        // 2. JSP에 넘길 주식 목록 데이터 생성
        List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < stockNameList.size(); i++) {

            int stockNo = stockNameList.get(i).getStockNo();
            String stockName = stockNameList.get(i).getName();

            // 주식 발행 정보 가져오기
            Map<String, Object> pubInfo = dao_detail.getStockPubInfo(stockNo);

            int pubAmount = 0;
            int pubPrice = 0;

            if (pubInfo != null && !pubInfo.isEmpty()) {

                if (pubInfo.get("pubAmount") != null) {
                    pubAmount = ((Number) pubInfo.get("pubAmount")).intValue();
                }

                if (pubInfo.get("pubPrice") != null) {
                    pubPrice = ((Number) pubInfo.get("pubPrice")).intValue();
                }
            }

            int currentPrice = 0;

            // 발행잔량이 1개 이상이면 현재가격 = 발행가격
            if (pubAmount > 0) {
                currentPrice = pubPrice;
            } else {
                currentPrice = dao_detail.getStockPrice(stockNo);
            }

            // 이전 장 가격
            int prevPrice = dao_detail.getPervPrice(stockNo);
    
            // 현재가격 - 이전가격은 Action에서 계산 (거래가 없는 주식은 action에서 계산하라고 함)
            int priceChange = currentPrice - prevPrice;          
            // 등락률도 Action에서 계산
            double changeRate = 0.0;

            if (prevPrice != 0) {
                changeRate = ((double) priceChange / prevPrice) * 100;
                changeRate = Math.round(changeRate * 100) / 100.0;
            }

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