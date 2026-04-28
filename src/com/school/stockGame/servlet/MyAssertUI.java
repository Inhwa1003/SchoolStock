package com.school.stockGame.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.MyAssetDAO;

public class MyAssertUI implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {

        MyAssetDAO dao = new MyAssetDAO();

        // 일단 테스트용
        // 나중에는 로그인할 때 session에 저장한 studentId를 꺼내면 됨
        HttpSession session = request.getSession();

        String studentId = (String) session.getAttribute("studentId");

        if (studentId == null) {
            studentId = "abc";
        }

        // 일단 1번 종목 기준 테스트
        int stockNo = 1;

        String stockNoParam = request.getParameter("stockNo");

        if (stockNoParam != null && !stockNoParam.equals("")) {
            stockNo = Integer.parseInt(stockNoParam);
        }

        int myValue = dao.getMyValue(stockNo, studentId);
        int pointValue = dao.getPointValue(studentId);
        int totalProfit = dao.getTotalProfit(stockNo, studentId, "체결");
        int totalCoupon = dao.getTotalCoupon(studentId);

        String stockName = dao.getStockName(studentId);
        int stockAmount = dao.getStockAmount(studentId, stockNo, "체결");
        int averagePrice = dao.getAveragePrice(studentId, stockNo);
        int purchasePrice = dao.getPurchasePrice(studentId, stockNo, "체결", "매수");
        int stockProfit = dao.getStockProfit(studentId, stockNo, "체결");

        request.setAttribute("myValue", myValue);
        request.setAttribute("pointValue", pointValue);
        request.setAttribute("totalProfit", totalProfit);
        request.setAttribute("totalCoupon", totalCoupon);

        request.setAttribute("stockName", stockName);
        request.setAttribute("stockAmount", stockAmount);
        request.setAttribute("averagePrice", averagePrice);
        request.setAttribute("purchasePrice", purchasePrice);
        request.setAttribute("stockProfit", stockProfit);

        System.out.println("=== MyAssertUI 실행됨 ===");
        System.out.println("studentId = " + studentId);
        System.out.println("stockNo = " + stockNo);

        System.out.println("myValue = " + myValue);
        System.out.println("pointValue = " + pointValue);
        System.out.println("totalProfit = " + totalProfit);
        System.out.println("totalCoupon = " + totalCoupon);
        System.out.println("stockName = " + stockName);
        System.out.println("stockAmount = " + stockAmount);
        System.out.println("averagePrice = " + averagePrice);
        System.out.println("purchasePrice = " + purchasePrice);
        System.out.println("stockProfit = " + stockProfit);
        
        // 중요: /view/ 붙이지 말기
        return "my_assets.jsp";
    }
}