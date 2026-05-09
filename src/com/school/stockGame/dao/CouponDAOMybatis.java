package com.school.stockGame.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOMybatis implements CouponDAOInterface {

    @Override
    public List<CouponVO> getCouponList() {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return session.selectList("stockGameMapper.getCouponList");
        }
    }

    @Override
    public boolean setBuyCoupon(String studentId, int couponPrice, String couponName, String state, int couponNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            try {
                // 1. 쿠폰 구매 내역 추가
                CouponPurchaseVO purchaseRecord = new CouponPurchaseVO(studentId, couponPrice, couponName, state, couponNo);
                int insertCount = session.insert("stockGameMapper.buyCoupon", purchaseRecord);

                // 2. 학생 자산 업데이트 (포인트 차감 및 쿠폰 개수 증가)
                CouponPurchaseVO updateRecord = new CouponPurchaseVO(studentId, couponPrice);
                int updateCount = session.update("stockGameMapper.updatePurchase", updateRecord);

                // 둘 다 성공했을 때만 커밋
                if (insertCount > 0 && updateCount > 0) {
                    session.commit();
                    return true;
                } else {
                    session.rollback();
                    return false;
                }
            } catch (Exception e) {
                session.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public int getStudentPoint(String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Integer result = session.selectOne("stockGameMapper.getStudentPoint", studentId);
            return result == null ? 0 : result;
        }
    }

    @Override
    public int insertPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, String state) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            CouponPurchaseVO purchase = new CouponPurchaseVO(studentId, couponPrice, couponName, state, couponNo);
            int result = session.insert("stockGameMapper.buyCoupon", purchase);
            session.commit();
            return result;
        }
    }

    @Override
    public int updateStudentAssets(String studentId, int price) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            CouponPurchaseVO purchase = new CouponPurchaseVO(studentId, price);
            int result = session.update("stockGameMapper.updatePurchase", purchase);
            session.commit();
            return result;
        }
    }

    @Override
    public List<CouponPurchaseVO> MyCouponList(String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return session.selectList("stockGameMapper.getMyCoupon", studentId);
        }
    }
}
