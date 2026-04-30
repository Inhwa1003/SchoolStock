package com.school.stockGame.query;

public interface MyPointHistoryQuery {
	String MY_POINT_HISTORY_SQL="SELECT cp.purchase_date, cp.price, cp.name, gp.content, gp.point, gp.get_date, o.content, o.price, o.amount, o.order_date, s.name, t.transaction_date FROM COUPON_PURCHASE cp, COUPONS c, GET_POINT gp, ORDERS o, STOCKS s, TRANSACTION t, STUDENTS WHERE STUDENTS.student_id='abc'";
	
	
ㅇ
}