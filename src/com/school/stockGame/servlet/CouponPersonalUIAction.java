package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.CouponDAO;
import com.school.stockGame.vo.CouponPurchaseVO;

public class CouponPersonalUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) 
			throws ServletException, IOException {
		String url="CouponPersonal.jsp";
		Map<String, Object> studentInfo = (Map<String, Object>) request.getSession().getAttribute("info");

		
		CouponDAO dao = new CouponDAO();
		List<CouponPurchaseVO> list = dao.MyCouponList((String) studentInfo.get("studentId"));
		request.setAttribute("couponlist", list);

		
		
		return url;
	}

}
