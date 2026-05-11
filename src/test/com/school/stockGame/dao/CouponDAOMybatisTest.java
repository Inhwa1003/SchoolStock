package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.school.stockGame.dao.CouponDAOInterface;
import com.school.stockGame.dao.mybatis.CouponDAOMybatis;
import com.school.stockGame.dao.mybatis.DBCPMybatis;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOMybatisTest {
	CouponDAOInterface dao;
	// 등록된 쿠폰 모두 조회
	@Test
	public void getCouponListTest(){
		dao = new CouponDAOMybatis();
		// NO
		// 조건을 주지 않는 조회 업무라 NO인 상황이 없다.
		// YES
		assertNotNull(dao.getCouponList());
		assertTrue(dao.getCouponList().size() > 0);
	}
	
	// 내가 구매한 쿠폰 수량
	@Test
	public void getMyCouponCountTest(){
		dao = new CouponDAOMybatis();
		// NO
		// 없는 아이디 조회
		assertTrue(dao.getMyCouponCount("keks") == 0); 
		// YES
		assertTrue(dao.getMyCouponCount("abc") > 0);
		assertNotNull(dao.getMyCouponCount("abc"));
	}
	
	// 쿠폰 구매 (쿠폰구매내역 추가)
	@Test
	public void setPurchaseRecordTest() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			flag = session.insert("couponMapper.setPurchaseRecord", new CouponPurchaseVO(100, "간식 교환권", 1, "abc", 1)) == 1;
			assertTrue(flag);
		} finally {
			session.rollback();
			session.close();
		}
	}
	
	// 쿠폰 구매시 학생의 가용포인트 차감 및 보유쿠폰 개수 업데이트(가용포인트 부족시 구매 불가)
	@Test
	public void setStudentAssetsTest(){
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			flag = session.update("couponMapper.setStudentAssets", new CouponVO(100, "abc")) == 1;
			assertTrue(flag);
		} finally {
			session.rollback();
			session.close();
		}
	}

}
