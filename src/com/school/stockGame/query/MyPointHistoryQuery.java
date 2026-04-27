package com.school.stockGame.query;

public interface MyPointHistoryQuery {
	// 내 포인트 내역 정보 조회(쿠폰, 지급, 주식)
	String MY_POINT_HISTORY_SQL="SELECT cp.purchase_date, cp.price, cp.name, gp.content, gp.point, gp.get_date, o.content, o.price, o.amount, o.order_date, s.name, t.transaction_date FROM COUPON_PURCHASE cp, COUPONS c, GET_POINT gp, ORDERS o, STOCKS s, TRANSACTION t, STUDENTS WHERE STUDENTS.student_id='abc'";
	
	

}