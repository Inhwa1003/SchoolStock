package test.com.school.stockGame.dao;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.jdbc.MyAssetDAO;

public class MyAssetDAOTest {
    private MyAssetDAO dao;
    
    // 테스트용 기준 데이터
    private String validStudentId = "abc";    // DB에 존재하는 아이디
    private String invalidStudentId = "none"; // 존재하지 않는 아이디
    private int validStockNo = 1;             // DB에 존재하는 종목 번호
    private int invalidStockNo = 999;         // 존재하지 않는 종목 번호
    private String state = "체결";
    private String content = "매수";

    @Before
    public void setUp() {
        dao = new MyAssetDAO();
    }

    // --- [1. 총 자산 가치 조회 테스트] ---
    @Test
    public void testGetMyValue_성공() {
        int value = dao.getMyValue(validStockNo, validStudentId);
        System.out.println("1. 총 자산: " + value);
        assertTrue("총 자산은 0 이상이어야 합니다.", value >= 0);
    }

    @Test
    public void testGetMyValue_실패_잘못된정보() {
        int value = dao.getMyValue(invalidStockNo, invalidStudentId);
        assertEquals("잘못된 정보 입력 시 자산은 0이어야 합니다.", 0, value);
    }

    // --- [2. 보유 포인트 조회 테스트] ---
    @Test
    public void testGetPointValue_성공() {
        int points = dao.getPointValue(validStudentId);
        System.out.println("2. 보유 포인트: " + points);
        assertTrue("포인트는 0 이상이어야 합니다.", points >= 0);
    }

    @Test
    public void testGetPointValue_실패_유령회원() {
        int points = dao.getPointValue(invalidStudentId);
        assertEquals("존재하지 않는 회원의 포인트는 0이어야 합니다.", 0, points);
    }

    // --- [3. 보유 쿠폰 수 조회 테스트] ---
    @Test
    public void testGetTotalCoupon_성공() {
        int coupons = dao.getTotalCoupon(validStudentId);
        System.out.println("3. 보유 쿠폰 수: " + coupons);
        assertTrue(coupons >= 0);
    }

    // --- [4. 종목명 조회 테스트] ---
    @Test
    public void testGetStockName_성공() {
        String name = dao.getStockName(validStockNo);
        System.out.println("4. 조회된 종목명: " + name);
        assertNotNull("종목명이 조회되어야 합니다.", name);
    }

    @Test
    public void testGetStockName_실패_없는종목() {
        String name = dao.getStockName(invalidStockNo);
        assertNull("없는 종목 번호 조회 시 null을 반환해야 합니다.", name);
    }

    // --- [5. 보유 주식 수량 조회 테스트] ---
    @Test
    public void testGetStockAmount_성공() {
        int amount = dao.getStockAmount(validStudentId, validStockNo, state);
        System.out.println("5. 보유 수량: " + amount);
        assertTrue(amount >= 0);
    }

    // --- [6. 평균 단가 및 구매 비용 테스트] ---
    @Test
    public void testGetAveragePrice_성공() {
        int avgPrice = dao.getAveragePrice(validStudentId, validStockNo, state, content);
        System.out.println("6. 평균 단가: " + avgPrice);
        assertTrue(avgPrice >= 0);
    }

    // --- [7. 종목별 손익 조회 테스트 (신규 추가)] ---
    @Test
    public void testGetStockProfit_성공() {
        int profit = dao.getStockProfit(validStudentId, validStockNo, state);
        System.out.println("7. 종목 수익금: " + profit);
    }

    // --- [8. 보유 중인 종목 번호 리스트 테스트 (신규 추가)] ---
    @Test
    public void testGetMyStockNos_성공() {
        List<Integer> stockNos = dao.getMyStockNos(validStudentId, state);
        System.out.println("8. 보유 종목 리스트 크기: " + stockNos.size());
        assertNotNull(stockNos);
    }

    @Test
    public void testGetMyStockNos_데이터없음() {
        List<Integer> stockNos = dao.getMyStockNos(invalidStudentId, state);
        assertEquals("거래 내역이 없는 학생의 리스트 크기는 0이어야 합니다.", 0, stockNos.size());
    }
}