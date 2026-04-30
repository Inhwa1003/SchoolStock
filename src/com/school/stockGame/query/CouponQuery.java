package com.school.stockGame.query;

public interface CouponQuery {
	// 만들어져있는 쿠폰 모두 조회
	String COUPON_LIST_SQL = "SELECT coupon_no, name, price FROM coupons";
	// 나의 가용포인트 조회
	String MY_POINT_SQL = "SELECT total_point FROM students WHERE student_id=?";
	// 쿠폰 구매 (쿠폰구매내역 추가)
	String BUY_COUPON_SQL = "INSERT INTO coupon_purchase (coupon_purchase_no, purchase_date, price, name, state, student_id, coupon_no) "
			+ "VALUES(coupon_purchase_no_seq.NEXTVAL, sysdate, ?, ?, ?, ?, ?)";
	// 쿠폰 구매시 학생의 가용포인트 차감 및 보유쿠폰 개수 업데이트(가용포인트 부족시 구매 불가) 
	String UPDATE_STUDENT_PURCHASE_SQL = "UPDATE students SET total_point = total_point - ?, total_coupon = total_coupon + 1 "
			+ "WHERE student_id = ? AND total_point >= ?";
	// 나의 보유 쿠폰 조회
	String MY_COUPON_SQL = "SELECT c.name, cp.price "
			+ "FROM coupon_purchase cp "
			+ "INNER JOIN coupons c ON cp.coupon_no = c.coupon_no "
			+ "WHERE student_id= ? ";
}
