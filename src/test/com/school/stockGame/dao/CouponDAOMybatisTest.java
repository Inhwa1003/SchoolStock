package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.school.stockGame.dao.mybatis.DBCPMybatis;
import com.school.stockGame.vo.CouponPurchaseVO;

public class CouponDAOMybatisTest {
	
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

}
