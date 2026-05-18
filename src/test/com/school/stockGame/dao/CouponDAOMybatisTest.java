package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.school.stockGame.dao.CouponDAOInterface;
import com.school.stockGame.dao.mybatis.CouponDAOMybatis;
import com.school.stockGame.dao.mybatis.DBCPMybatis;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOMybatisTest {
	CouponDAOInterface dao = new CouponDAOMybatis();
	
	// 등록된 쿠폰 모두 조회
	@Test
	public void getCouponListTest(){
		// NO
		// 조건을 주지 않는 조회 업무라 NO인 상황이 없다.
		// YES
		assertNotNull(dao.getCouponList());
		assertTrue(dao.getCouponList().size() >= 0);
		assertTrue(dao.getCouponList().size() <= 5);
		//System.out.println("등록된 모든 쿠폰 : " + dao.getCouponList());
	}
	
	// 내가 구매한 쿠폰 수량
	@Test
	public void getMyCouponCountTest(){
		// NO
		// 없는 아이디 조회
		assertTrue(dao.getMyCouponCount("keks") == 0);
		assertFalse(!(dao.getMyCouponCount("toto") == 0));
		assertFalse(dao.getMyCouponCount("abc") == 1);
		// YES
		assertTrue(dao.getMyCouponCount("abc") >= 0);
		assertNotNull(dao.getMyCouponCount("abc"));
	}
	
	// 나의 가용포인트 조회
	@Test
	public void getStudentPointTest() {
		// NO
		// 없는 아이디 조회
		//assertTrue(dao.getStudentPoint("keksee1") == 0);
		//assertFalse(!(dao.getStudentPoint("toto") == 0));
		assertFalse(dao.getStudentPoint("abc") == 3800);
		// YES
		assertTrue(dao.getStudentPoint("abc") > 0);
		assertNotNull(dao.getStudentPoint("abc"));
		System.out.println("[abc]의 보유포인트 : " + dao.getStudentPoint("abc") + "P");
	}

	// 쿠폰 구매 (쿠폰구매내역 추가)
	@Test
	public void setPurchaseRecordTest() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			// 쿠폰 구매를 실패 하는 상황은 DB에서 이미 오류를 내버림
			//flag = session.insert("couponMapper.setPurchaseRecord", new CouponPurchaseVO(100, "간식 교환권", 10, "abc", 1)) == 1;
			
			flag = session.insert("couponMapper.setPurchaseRecord", new CouponPurchaseVO(100, "간식 교환권", 1, "abc", 1)) == 1;
			assertTrue(flag);
			//if(flag)
				//System.out.println("쿠폰 구매내역 추가 테스트 완료");
		} finally {
			session.rollback();
			session.close();
		}
	}
	
	// 쿠폰 구매시 학생의 가용포인트 차감 및 보유 쿠폰 수량 증가(가용포인트 부족시 구매 불가)
	@Test
	public void setStudentAssetsTest(){
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			flag = session.update("couponMapper.setStudentAssets", new CouponVO(100, "abc")) == 1;
			assertTrue(flag);
			//if(flag)
				//System.out.println("쿠폰 구매 학생 보유포인트 다운 보유쿠폰 수량 업 테스트 완료");
		} finally {
			session.rollback();
			session.close();
		}
	}
	
	// 나의 보유 쿠폰 조회 테스트
	@Test
	public void getMyCouponListTest(){
		// NO
		// 잘못된 아이디 입력
		assertTrue(dao.getMyCouponList("toto").size() == 0);
		// YES
		assertFalse(dao.getMyCouponList("abc").equals(null));
		assertTrue(dao.getMyCouponList("abc").size() >= 0);
		//System.out.println("[abc]의 보유 쿠폰 : " + dao.getMyCouponList("abc"));
	}

}
