package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.StockDetailDAOInterface;
import com.school.stockGame.dao.StockDetailDAOMybatis;

public class StockDetailDAOTest {
    
    private StockDetailDAOInterface dao;

    @Before
    public void setUp() {
        // 기존 JDBC DAO 대신 MyBatis DAO 구현체를 주입합니다.
        dao = new StockDetailDAOMybatis();
    }

    @Test
    public void testGetStockInfo() {
        assertNotNull("주식 기본 정보는 null이 아니어야 합니다.", dao.getStockInfo(1));
        System.out.println("주식 정보(1번): " + dao.getStockInfo(1));
    }

    @Test
    public void testGetStockPrice() {
        int price = dao.getStockPrice(1);
        System.out.println("주식 현재가격(1번): " + price);
        assertTrue(price >= 0);
    }

    @Test
    public void testGetStockPriceChange() {
        int change = dao.getStockPriceChange(1);
        System.out.println("주식 이전가격 대비 변화량(1번): " + change);
    }

    @Test
    public void testGetChangeRate() {
        double rate = dao.getChangeRate(1);
        System.out.println("주식 등락률(1번): " + rate + "%");
    }

    @Test
    public void testGetPervPrice() {
        int prevPrice = dao.getPervPrice(1);
        System.out.println("주식 이전가격(1번): " + prevPrice);
        assertTrue(prevPrice >= 0);
    }

    @Test
    public void testGetTotalOrder() {
        assertNotNull(dao.getTotalOrder(1));
        System.out.println("대기 중인 전체 주문 수(1번): " + dao.getTotalOrder(1).size());
    }

    @Test
    public void testGetTotalSellOrder() {
        assertNotNull(dao.getTotalSellOrder(1));
        System.out.println("대기 중인 매도 주문 수(1번): " + dao.getTotalSellOrder(1).size());
    }

    @Test
    public void testGetTotalBuyOrder() {
        assertNotNull(dao.getTotalBuyOrder(1));
        System.out.println("대기 중인 매수 주문 수(1번): " + dao.getTotalBuyOrder(1).size());
    }

    @Test
    public void testGetTotalMyOrder() {
        assertNotNull(dao.getTotalMyOrder(1, "dldlsghk123"));
        System.out.println("내 주문 요청 수(1번): " + dao.getTotalMyOrder(1, "dldlsghk123").size());
    }

    @Test
    public void testGetPublishInfo() {
        assertNotNull(dao.getStockPubInfo(1));
        System.out.println("발행 정보(1번): " + dao.getStockPubInfo(1));
    }

    @Test
    public void testGetStudentPoint() {
        int point = dao.getStudentPoint("dldlsghk123");
        System.out.println("학생 보유 포인트: " + point);
        assertTrue("학생 보유 포인트는 0 이상이어야 합니다.", point >= 0);
    }

    @Test
    public void testGetStudentStockAmount() {
        int amount = dao.getStudentStockAmount(1, "dldlsghk123");
        System.out.println("학생 보유 주식 수량: " + amount);
        assertTrue(amount >= 0);
    }

    // ==============================================================
    // 아래 메서드들은 DB 데이터(주문, 포인트 등)를 실제로 수정하므로 
    // 주석 처리된 상태를 유지하거나 테스트 필요 시 활용할 수 있게만 정리했습니다.
    // ==============================================================

    @Test
    public void testSetMatchedOrder() {
        // boolean result = dao.setMatchedOrder(1, 4);
        // assertTrue(result);
    }

    @Test
    public void testSetOrderStateCancel() {
        // boolean result = dao.setOrderStateCancel(14);
        // assertTrue(result);
    }

    @Test
    public void testSetOrderStatePending() {
        // boolean result = dao.setOrderStatePending(14);
        // assertTrue(result);
    }

    @Test
    public void testSetOrderStateMatched() {
        // boolean result = dao.setOrderStateMatched(14);
        // assertTrue(result);
    }

    @Test
    public void testSetStockPubBalance() {
        // boolean result = dao.setStockPubBalance(3, 1);
        // assertTrue(result);
    }

    @Test
    public void testGetMatchOrder() {
        // 파라미터 순서: stockNo, orderPrice, orderAmount, studentId, content
        // System.out.println(dao.getMatchOrder(1, 1700, 1, "dldlsghk123", "매도"));
    }

    @Test
    public void testSetOrderRequest() {
        // 매수 주문 요청
        // boolean result = dao.setOrderRequest("매수", 1200, 2, "대기", "dldlsghk123", 1);
        // assertTrue(result);
    }

    @Test
    public void testSetStudentPointDown() {
        // 학생 포인트 감소
        // boolean result = dao.setStudentPointDown(200, "dldlsghk123");
        // assertTrue(result);
    }

    @Test
    public void testSetStudentPointUp() {
        // 학생 포인트 증가
        // boolean result = dao.setStudentPointUp(200, "dldlsghk123");
        // assertTrue(result);
    }
}
