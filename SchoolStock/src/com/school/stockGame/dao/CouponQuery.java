package com.school.stockGame.dao;

public interface CouponQuery {

         String GET_COUPON_LIST = "SELECT coupon_no, name, price FROM coupons";

         String UPDATE_STUDENT_FOR_PURCHASE = "UPDATE students SET total_point = total_point - ?, total_coupon = total_coupon + 1 " +
	      "WHERE student_id = ? AND total_point >= ?";
}

