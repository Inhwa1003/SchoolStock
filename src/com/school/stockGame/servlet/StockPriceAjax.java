package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.school.stockGame.dao.jdbc.StockDetailDAO;
import com.school.stockGame.dao.jdbc.StockListDAO;
import com.school.stockGame.vo.StockVO;

public class StockPriceAjax implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {

        StockListDAO daoList = new StockListDAO();
        StockDetailDAO daoDetail = new StockDetailDAO();

        // 1. 등록된 주식 목록 조회
        List<StockVO> stockNameList = daoList.getStockNameList();

        // 2. JSON으로 보낼 데이터 생성
        List<Map<String, Object>> stockList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < stockNameList.size(); i++) {

            int stockNo = stockNameList.get(i).getStockNo();
            String stockName = stockNameList.get(i).getName();

            /*
             * 현재가격 기준
             *
             * 1. 발행잔량이 1개 이상이면 현재가격 = 발행가격
             * 2. 발행잔량이 0개이면 현재가격 = 최신 거래가격
             *
             * 이전가격 기준
             *
             * 이전가격 = 이전 장 가격(STOCKS.PREV_PRICE)
             *
             * 계산 기준
             *
             * 현재가격 - 이전가격 = currentPrice - prevPrice
             * 등락률 = (현재가격 - 이전가격) / 이전가격 * 100
             */

            // 3. 발행 정보 조회
            Map<String, Object> pubInfo = daoDetail.getStockPubInfo(stockNo);

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

            // 4. 현재가격 결정
            int currentPrice = 0;

            if (pubAmount > 0) {
                // 발행잔량이 남아 있으면 현재가격은 발행가격
                currentPrice = pubPrice;
            } else {
                // 발행잔량이 없으면 최신 거래가격
                currentPrice = daoDetail.getStockPrice(stockNo);
            }

            // 5. 이전 장 가격 조회
            int prevPrice = daoDetail.getPervPrice(stockNo);

            // 6. 현재가격 - 이전 장 가격 계산
            int priceChange = currentPrice - prevPrice;

            // 7. 등락률 계산
            double changeRate = 0.0;

            if (prevPrice != 0) {
                changeRate = ((double) priceChange / prevPrice) * 100;
                changeRate = Math.round(changeRate * 100) / 100.0;
            }

            // 8. JSON으로 보낼 데이터 구성
            Map<String, Object> stock = new LinkedHashMap<String, Object>();

            stock.put("stockNo", stockNo);
            stock.put("stockName", stockName);
            stock.put("currentPrice", currentPrice);
            stock.put("prevPrice", prevPrice);
            stock.put("priceChange", priceChange);
            stock.put("changeRate", changeRate);

            stockList.add(stock);
        }

        // 9. Java 객체를 JSON 문자열로 변환
        Gson gson = new Gson();
        String json = gson.toJson(stockList);

        // 디버깅용
        //System.out.println("StockPriceAjax Json=" + json);

        // 10. FrontControllerServlet에서 jsonData를 출력하는 구조라면 그대로 사용
        request.setAttribute("jsonData", json);

        return null;
    }
}