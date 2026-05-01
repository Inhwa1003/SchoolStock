package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.MyAssetDAO;
import com.school.stockGame.dao.StockDetailDAO;;

public class MyAssetsAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "MyAssets.jsp";
		try {
			HttpSession session = request.getSession();
			String studentId = (String) session.getAttribute("studentId");
			MyAssetDAO myAsset = new MyAssetDAO();
			StockDetailDAO detail = new StockDetailDAO();

			if(studentId == null) studentId = "abc";
			List<Integer> myStockNos = myAsset.getMyStockNos(studentId, "체결");
			List stockList = new ArrayList();
			//			List<Integer> myStockNos = dao.getMyStockNos(studentId, "체결");


			int totalPoint = myAsset.getPointValue(studentId);
			int totalCoupon = myAsset.getTotalCoupon(studentId);
			int totalStockValue = 0;
			int totalProfit = 0;

			for (int stockNo : myStockNos) {
				int amount = myAsset.getStockAmount(studentId, stockNo, "체결");

				if(amount > 0) { 
					int profit = myAsset.getStockProfit(studentId, stockNo, "체결");
					int stockPrice = detail.getStockPrice(stockNo);
					Map stockMap = new HashMap();
					stockMap.put("stockNo", stockNo);
                    stockMap.put("name", myAsset.getStockName(studentId)); 
                    stockMap.put("amount", amount);
                    stockMap.put("price", stockPrice);
                    stockMap.put("average", myAsset.getAveragePrice(studentId, stockNo, "체결", "매수"));
                    stockMap.put("totalPrice", myAsset.getPurchasePrice(studentId, stockNo, "체결", "매수"));
                    stockMap.put("profit", profit);
					totalStockValue += (amount * stockPrice);
					totalProfit += profit;

					stockList.add(stockMap);
				}
			}
			int totalAssets = totalPoint + totalStockValue;

			request.setAttribute("totalAssets", totalAssets);
			request.setAttribute("totalPoint", totalPoint);
			request.setAttribute("totalProfit", totalProfit);
			request.setAttribute("totalCoupon", totalCoupon);
			request.setAttribute("stockList", stockList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return url;
	}
}