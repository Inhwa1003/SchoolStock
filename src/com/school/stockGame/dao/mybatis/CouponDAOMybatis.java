package com.school.stockGame.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.CouponDAOInterface;
import com.school.stockGame.dao.jdbc.DBCP;
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
	
	// 내가 구매한 쿠폰 수량
	@Override
	public int getMyCouponCount(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int couponCount = 0;
		try {
			couponCount = session.selectOne("couponMapper.getMyCouponCount", studentId);
		} finally {
			session.close();
 		}
		return couponCount;
	}

	// 나의 가용포인트 조회
	@Override
	public int getStudentPoint(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int point = 0;
		try {
			point = session.selectOne("couponMapper.getStudentPoint", studentId);
		} finally {
			session.close();
 		}
		return point;
	}
	
	// 쿠폰 구매 (쿠폰구매내역 추가)
	@Override
	public int setPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, int state) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = 0;
		try {
			result = session.insert("couponMapper.setPurchaseRecord", new CouponPurchaseVO(couponPrice, couponName, state, studentId, couponNo));
		} finally {
			session.close();
 		}
		return result;
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
