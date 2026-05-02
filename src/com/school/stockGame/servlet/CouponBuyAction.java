package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.CouponDAO;
import com.school.stockGame.dao.StockDetailDAO;

public class CouponBuyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, Object> info = (Map<String, Object>) session.getAttribute("info");
		
		String studentId = (String) info.get("studentId");
		int couponNo = Integer.parseInt(request.getParameter("couponNo"));
		int couponPrice = Integer.parseInt(request.getParameter("couponPrice"));
		String couponName = request.getParameter("couponName");
		int state = 0;
		
		CouponDAO coupon = new CouponDAO();
		boolean result = coupon.setBuyCoupon(studentId, couponPrice, couponName, state, couponNo);
		
		if (result) {
			int totalPoint = (int) info.get("totalPoint");
			info.put("totalPoint", totalPoint - couponPrice);
			request.setAttribute("buyMessage", "구매가 완료 되었습니다");
		}else {
			request.setAttribute("buymessage", "구매 실패했습니다");
		}
		return "controller?cmd=CouponMarketUI";
	}
}
