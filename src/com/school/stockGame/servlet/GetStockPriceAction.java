package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.school.stockGame.dao.StockDetailDAO;
import com.school.stockGame.dao.MyAssetDAO;

public class GetStockPriceAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			String studentId = (String) session.getAttribute("studentId");
			if (studentId == null) studentId = "abc";

			String strStockNo = request.getParameter("stockNo");
			if (strStockNo == null || strStockNo.equals("")) return null;
			int stockNo = Integer.parseInt(strStockNo);

			StockDetailDAO detail = new StockDetailDAO();
			MyAssetDAO myAsset = new MyAssetDAO();

			// 1. 현재가 가져오기 (팀원 쿼리 수정 없이 자바에서 보완)
			int stockPrice = detail.getStockPrice(stockNo);
			int stockProfit = myAsset.getStockProfit(studentId, stockNo, "체결");

			// 2. 상단 카드를 위한 전체 자산 재계산
			int totalPoint = myAsset.getPointValue(studentId);
			List<Integer> myStockNos = myAsset.getMyStockNos(studentId, "체결");
			int totalStockValue = 0;
			int totalProfit = 0;

			for (int sNo : myStockNos) {
				int amount = myAsset.getStockAmount(studentId, sNo, "체결");
				if(amount > 0) {
					totalStockValue += (amount * detail.getStockPrice(sNo)); // 현재가 기준 가치
					totalProfit += myAsset.getStockProfit(studentId, sNo, "체결");
				}
			}
			int totalAssets = totalPoint + totalStockValue;

			// 3. 배달 사고 방지: 이름을 JSP와 똑같이 맞춥니다.
			request.setAttribute("stockNo", stockNo);
			request.setAttribute("stockPrice", stockPrice);
			request.setAttribute("stockProfit", stockProfit);
			request.setAttribute("totalAssets", totalAssets);
			request.setAttribute("totalProfit", totalProfit);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "MyAssetJson.jsp"; // 404 방지를 위해 파일명 꼭 확인!
	}
}