package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.stockGame.dao.jdbc.CouponDAOJdbc;

public class CouponBuyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, Object> info = (Map<String, Object>) session.getAttribute("info");
		
		// 세션 체크
		if (info == null) {
			return "controller?cmd=LoginUI";
		}
		
		String studentId = (String) info.get("studentId");
		int couponNo = Integer.parseInt(request.getParameter("couponNo"));
		int couponPrice = Integer.parseInt(request.getParameter("couponPrice"));
		String couponName = request.getParameter("couponName");
		int state = 0;
		
		CouponDAOJdbc coupon = new CouponDAOJdbc();
		
		// DAO에서 성공/실패 메시지를 받아옴
		String resultMessage = coupon.setBuyCoupon(studentId, couponPrice, couponName, state, couponNo);
		
		// 구매 성공일 때만 세션 포인트 차감
		if ("쿠폰 구매가 완료되었습니다.".equals(resultMessage)) {
			int totalPoint = (int) info.get("totalPoint");
			info.put("totalPoint", totalPoint - couponPrice);
		}
		
		// 성공/실패 메시지를 그대로 화면에 전달
		session.setAttribute("buyMessage", resultMessage);
		
		return "controller?cmd=CouponMarketUI";
	}
}