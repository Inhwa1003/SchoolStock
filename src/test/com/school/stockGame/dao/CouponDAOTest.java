package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.school.stockGame.dao.CouponDAO;
import com.school.stockGame.vo.CouponPurchaseVO;

public class CouponDAOTest {

	@Test
		public void 보유_쿠폰_확인_테스트() throws Exception {
			CouponDAO dao=new CouponDAO();
			List<CouponPurchaseVO> result=dao.MyCouponList("dldlsghk123");
					assertNotNull(result);
					// 2. 리스트가 비어있지 않은지 확인
				    if (result.isEmpty()) {
				        System.out.println("조회된 쿠폰이 없습니다.");
				    } else {
				        System.out.println("=== 보유 쿠폰 목록 (" + result.size() + "건) ===");
				        
				        // 반복문을 통해 각 VO 객체의 필드값을 직접 출력
				        for (CouponPurchaseVO vo : result) {
				            // VO에 정의된 getName()과 getPrice() 메서드를 사용합니다.
				            System.out.println("쿠폰명: " + vo.getName() + " | 가격: " + vo.getPrice());
				        }
				        
				        System.out.println("==============================");
				    }
	}}