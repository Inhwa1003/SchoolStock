package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.MyAssetDAO;

public class MyAssetDAOTset {
	private MyAssetDAO dao;
    private String testStudentId = "abc";
    private int testStockNo = 1;
    
    @Before
    public void setUp() {
    	dao = new MyAssetDAO();
    }
    
    @Test
    public void testGetMyValue_성공() {
        int value = dao.getMyValue(testStockNo, testStudentId);
        System.out.println("1. 총 자산: " + value);
        assertTrue("총 자산은 0 이상이어야 합니다.", value >= 0);
    }
    
    @Test
    public void testGetMyValue_자산이_음수일때() {
        int value = dao.getMyValue(testStockNo, testStudentId);
        System.out.println("1. 총 자산: " + value);
        assertFalse("총 자산은 0 이상이어야 합니다.", value < 0);
    }
    
    @Test
    public void testGetPointValue_성공() {
        int points = dao.getPointValue(testStudentId);
        System.out.println("2. 보유 포인트: " + points);
        assertTrue("포인트는 0 이상이어야 합니다.", points >= 0);
    }
     
    @Test
    public void testGetPointValue_음수일경우() {
        int points = dao.getPointValue(testStudentId);
        System.out.println("2. 보유 포인트: " + points);
        assertFalse("포인트는 0 이상이어야 합니다.", points < 0);
    }

    @Test
    public void testGetTotalProfit() {
        int totalProfit = dao.getTotalProfit(testStockNo, testStudentId, "체결");
        System.out.println("3. 총 손익 합계: " + totalProfit);
        assertNotNull(totalProfit);
    }

    @Test
    public void testGetTotalCoupon() {
        int coupons = dao.getTotalCoupon(testStudentId);
        System.out.println("4. 보유 쿠폰 수: " + coupons);
        assertTrue("쿠폰 수는 0 이상이어야 합니다.", coupons >= 0);
    }

    @Test
    public void testGetStockName() {
        String name = dao.getStockName(testStudentId);
        System.out.println("5. 조회된 종목명: " + name);
        assertNotNull("종목명이 조회되어야 합니다.", name);
    }

    @Test
    public void testGetStockAmount() {
        int amount = dao.getStockAmount(testStudentId, testStockNo, "체결");
        System.out.println("6. 보유 주식 수량: " + amount);
        assertTrue("주식 수량은 0 이상이어야 합니다.", amount >= 0);
    }

    @Test
    public void testGetAveragePrice() {
        int avgPrice = dao.getAveragePrice(testStudentId, testStockNo, "체결", "매수");
        System.out.println("7. 평균 매수 단가: " + avgPrice);
        assertTrue("평단가는 0 이상이어야 합니다.", avgPrice >= 0);
    }

    @Test
    public void testGetPurchasePrice() {
        int purchasePrice = dao.getPurchasePrice(testStudentId, testStockNo, "체결", "매수");
        System.out.println("8. 총 구매 비용: " + purchasePrice);
        assertTrue("구매 비용은 0 이상이어야 합니다.", purchasePrice >= 0);
    }

    @Test
    public void testGetStockProfit() {
        int profit = dao.getStockProfit(testStudentId, testStockNo, "체결");
        System.out.println("9. 개별 종목 손익: " + profit);
        assertNotNull(profit);
    }
}
