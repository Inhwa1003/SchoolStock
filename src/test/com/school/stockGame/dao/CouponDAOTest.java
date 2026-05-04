package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.CouponDAO;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOTest {
    private CouponDAO dao;
    
    private String validId = "dldlsghk123";
    private String invalidId = "none";
    private int testCouponNo = 3;
    private String testCouponName = "분리수거 면제권";
    private int testPrice = 1000;

    @Before
    public void setUp() {
        dao = new CouponDAO();
    }

    @Test
    public void 보유_쿠폰_확인_테스트() throws Exception {
        List<CouponPurchaseVO> result = dao.MyCouponList(validId);
        assertNotNull(result);
        
        if (result.isEmpty()) {
            System.out.println("조회된 쿠폰이 없습니다.");
        } else {
            System.out.println("=== 보유 쿠폰 목록 (" + result.size() + "건) ===");
            for (CouponPurchaseVO vo : result) {
                System.out.println("쿠폰명: " + vo.getName() + " | 가격: " + vo.getPrice());
            }
            System.out.println("==============================");
        }
    }

    // 테스트할 때, 보유 쿠폰이 3개 이상일 경우에는 실패로 뜨는 게 맞음.
    @Test
    public void testGetCouponList_성공() {
        List<CouponVO> list = dao.getCouponList();
        System.out.println("상점 판매 쿠폰 개수: " + list.size());
        
        assertNotNull("상점 리스트 객체는 null이 아니어야 합니다.", list);
        assertTrue("상점에 판매 중인 쿠폰이 최소 1개는 있어야 합니다.", list.size() > 0);
    }

    @Test
    public void testSetBuyCoupon_성공() {
        String result = dao.setBuyCoupon(validId, testPrice, testCouponName, 0, testCouponNo);
        System.out.println("쿠폰 구매 성공 테스트 결과: " + result);
        
        assertEquals("정상적인 정보와 충분한 포인트가 있다면 성공 메시지가 반환되어야 합니다.",
                "쿠폰 구매가 완료되었습니다.", result);
    }

    @Test
    public void testSetBuyCoupon_실패_포인트부족() {
        int expensivePrice = 99999999;

        String result = dao.setBuyCoupon(validId, expensivePrice, testCouponName, 0, testCouponNo);
        System.out.println("포인트 부족 구매 실패 테스트 결과: " + result);
        
        assertEquals("포인트가 부족하면 포인트 부족 메시지가 반환되어야 합니다.",
                "보유 포인트가 부족합니다.", result);
    }

    @Test
    public void testSetBuyCoupon_실패_잘못된아이디() {
        String result = dao.setBuyCoupon(invalidId, 100, testCouponName, 0, testCouponNo);
        System.out.println("잘못된 아이디 구매 실패 테스트 결과: " + result);
        
        assertEquals("존재하지 않는 학생은 구매에 실패해야 합니다.",
                "보유 포인트가 부족합니다.", result);
    }

    @Test
    public void testMyCouponList_데이터없음() {
        List<CouponPurchaseVO> result = dao.MyCouponList(invalidId);
        System.out.println("무실적 학생 쿠폰 조회 결과 개수: " + result.size());
        
        assertEquals("구매 내역이 없는 경우 리스트의 크기는 0이어야 합니다.", 0, result.size());
    }
}