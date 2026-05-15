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

	// 쿠폰 구매
	@Override
	public String setBuyCoupon(String studentId, int couponPrice, String couponName, int state, int couponNo) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		String message = "";
		int maxCoupon = 3;
		try {
			// 1. 학생이 이미 구매한 쿠폰 개수 확인
			if (getMyCouponCount(session, studentId) >= maxCoupon) {
				message = "쿠폰은 최대 3개 까지만 구매할 수 있습니다.";
				return message;
			}

			// 2. 학생 보유 포인트 확인
			if (getStudentPoint(session, studentId) < couponPrice) {
				message = "보유 포인트가 부족합니다.";
				return message;
			}
			
			// 보유포인트 충분하다면
			// 2-1. 쿠폰 구매 내역 등록
			int addRecord = setPurchaseRecord(session, studentId, couponNo, couponName, couponPrice, state);
			// 2-2. 학생 포인트 차감 및 보유쿠폰 증가
			int pointDown = setStudentAssets(session, studentId, couponPrice);
			
			// 2-3. 둘 다 성공시 commit
			if (addRecord == 1 && pointDown == 1) {
				session.commit();
				message = "쿠폰 구매가 완료 되었습니다.";
			} else {
				session.rollback();
				message = "쿠폰 구매에 실패했습니다.";
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			message = "쿠폰 구매 처리 중 오류가 발생했습니다.";
		} finally {
			session.close();
		}
		return message;
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
	
	// 내가 구매한 쿠폰 수량 (트랜잭션 관리용)
	public int getMyCouponCount(SqlSession session, String studentId) {
		int couponCount = session.selectOne("couponMapper.getMyCouponCount", studentId);
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
	
	// 나의 가용포인트 조회 (트랜잭션 관리용)
	public int getStudentPoint(SqlSession session, String studentId) {
		int point = session.selectOne("couponMapper.getStudentPoint", studentId);
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
	
	// 쿠폰 구매 내역 추가 (트랜잭션 관리용)
		public int setPurchaseRecord(SqlSession session, String studentId, int couponNo, String couponName, int couponPrice, int state) {
			int result = session.insert("couponMapper.setPurchaseRecord", new CouponPurchaseVO(couponPrice, couponName, state, studentId, couponNo));
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
	
	// 쿠폰 구매시 학생의 가용포인트 차감 및 보유쿠폰 개수 업데이트 (트랜잭션 관리용)
		public int setStudentAssets(SqlSession session, String studentId, int price) {
			int result = session.update("couponMapper.setStudentAssets", new CouponVO(price, studentId));
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
