package com.school.stockGame.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOMybatis implements CouponDAOInterface {

	@Override
	public List<CouponVO> getCouponList() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<CouponVO> result = session.selectList("stockGameMapper.getCouponList");
		return result;
	}

	@Override
	public boolean setBuyCoupon(String studentId, int couponPrice, String couponName, String state, int couponNo) {
		return false;
	}

	@Override
	public int getStudentPoint(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.selectOne("stockGameMapper.getStudentPoint", studentId);
		return result;
	}

	@Override
	public int insertPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, String state) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.insert("stockGameMapper.buyCoupon", new CouponPurchaseVO(studentId, couponPrice, couponName, state, couponNo));
		return result;
	}

	@Override
	public int updateStudentAssets(String studentId, int price) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		int result = session.update("stockGamemapper.updatePurchase", new CouponPurchaseVO(studentId, price));
		return result;
	}

	@Override
	public List<CouponPurchaseVO> MyCouponList(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<CouponPurchaseVO> result = session.selectList("stockGameMapper.getMycoupon", studentId);
		return result;
	}
}
