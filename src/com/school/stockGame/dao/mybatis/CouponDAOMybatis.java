package com.school.stockGame.dao.mybatis;

import java.util.Collection;
import java.util.Collections;
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
		Integer point = 0;
		try {
			point = session.selectOne("couponMapper.getStudentPoint", studentId);
		} finally {
			session.close();
 		}
		// 잘못된 아이디 입력시 null 방지
		return point == null? 0:point;
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

	// 쿠폰 구매시 학생의 가용포인트 차감 및 보유쿠폰 개수 업데이트(가용포인트 부족시 구매 불가)
	@Override
	public int setStudentAssets(String studentId, int price) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = 0;
		try {
			result = session.update("couponMapper.setStudentAssets", new CouponVO(price, studentId));
		} finally {
			session.close();
 		}
		return result;
	}
	
	// 나의 보유 쿠폰 조회
	@Override
	public List<CouponPurchaseVO> getMyCouponList(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<CouponPurchaseVO> list = null;
		try {
			list = session.selectList("couponMapper.getMyCouponList", studentId);
		} finally {
			session.close();
		}
		// 오류로 인한 null 상황 발생시 빈 리스트 반환
		return list == null? Collections.emptyList():list;
	}

}
