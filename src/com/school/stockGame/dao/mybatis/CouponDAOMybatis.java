package com.school.stockGame.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.CouponDAOInterface;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOMybatis implements CouponDAOInterface{
	
	// 등록된 쿠폰 모두 조회
	@Override
	public List<CouponVO> getCouponList() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<CouponVO> list = null;
		try {
			list = session.selectList("couponMapper.getCouponList");
		} finally {
			session.close(); 
		}
		return list;
	}

	@Override
	public String setBuyCoupon(String studentId, int couponPrice, String couponName, int state, int couponNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMyCouponCount(String studentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStudentPoint(String studentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, int state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setStudentAssets(String studentId, int price) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CouponPurchaseVO> getMyCouponList(String studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
