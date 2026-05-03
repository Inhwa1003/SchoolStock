package com.school.stockGame.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.school.stockGame.dao.CouponDAO;
import com.school.stockGame.vo.CouponVO;

public class CouponMarketUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		CouponDAO dao = new CouponDAO();
		List<CouponVO> list = dao.getCouponList();
		
		request.setAttribute("couponList", list);
		
		return "CouponMarket.jsp";
	}

}
