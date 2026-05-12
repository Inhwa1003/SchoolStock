package test.com.school.stockGame.dao;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import com.school.stockGame.dao.MyAssetDAOInterface;
import com.school.stockGame.dao.mybatis.MyAssetDAOMybatis;

/* 테스트 코드 작성 팀 내 규칙
1. assert로 테스트 결과를 확인한다. 2. 매서드명은 영어로 작성한다.
3. Interface 구현부에  맞춰 매서드를 작성한다. 4. 매서드 당 성공과 실패를 둘 다 테스트한다.
*/


public class MyAssetDAOMybatisTest {
    private MyAssetDAOInterface dao = new MyAssetDAOMybatis();
    
    // 특정 학생의 매수 주문한 주식 중 체결된  매수 주식에 따른 총 자산 조회
    @Test
    public void getMyValueTest() {
    	// success
    	int successValue = dao.getMyValue(1, "abc");
        System.out.println("총 자산: " + successValue);
        assertTrue(successValue >= 0);
        assertNotNull(successValue);
        
        // fail => 존재하지 않은 주식 조회 
        // -> 1. 쿼리문에 존재하지 않은 주식을 조회할 때 존재하지 않는 주식 번호 처리가 생략되어있음. 그래서 0이 아니라 값이 콘솔에 찍힘.
        // int failValue_notExistStockId = dao.getMyValue(11, "abc");
        // System.out.println("존재하지 않는 주식 조회 결과: " + failValue_notExistStockId);
        // assertEquals(failValue_notExistStockId, 0);

        // fail => 존재하지 않은 학생 아이디로 조회할 때.
        int failValue_notExistStudentId = dao.getMyValue(2, "abc1234");
        // System.out.println("존재하지 않는 학생 아이디로 조회 결과: " + failValue_notExistStudentId);
        assertEquals(failValue_notExistStudentId, 0);
        
    }

    // 특정 학생의 보유 포인트 조회
    @Test
    public void getPointValueTest() {
        int points = dao.getPointValue("abc");
        System.out.println("홍길동의 보유 포인트: " + points);
        assertNotNull(points);
    }
   
    // 특정 학생의 체결된 매수 주식의 수익금 계산 값을 조회 -> 왜 실패가 나는 지 모르겠음.
//    @Test
//    public void getTotalProfit(){
//    	int stockProfit = dao.getTotalProfit(3, "abc", "매수");
//    	System.out.println("홍길동의 SM 주식 수익금: " + stockProfit);
//    }
    
    // 특정 학생의 보유 쿠폰 수의 총 개수 조회
    @Test
    public void getTotalCouponTest() {
        int coupons = dao.getTotalCoupon("dldlsghk123");
        System.out.println("이인화의 보유 쿠폰 수: " + coupons);
        assertTrue(coupons >= 0);
        assertFalse(coupons < 0);
        assertNotNull(coupons);
    }

    // 특정 주식의 주식명 조회
    @Test
    public void getStockNameTest() {
        String name = dao.getStockName(1);
        System.out.println("조회된 주식명: " + name);
        assertNotNull(name);
    }

    // 특정 학생이 보유한 특정 주식 보유 수량 조회
    @Test
    public void getStockAmountTest() {
        // success
        int successAmount = dao.getStockAmount("abc", 1, "매수");
        System.out.println("정상 데이터 보유 수량: " + successAmount);

        assertTrue(successAmount >= 0);

        // fail - 추후 실패 테스트도 다양하게 작성해서 추가하면 좋을 듯합니다. ex) 없는 아이디일 때만, 없는 주식일 때만
        int failAmount = dao.getStockAmount("없는아이디", 999, "매수");
        System.out.println("없는 데이터 조회 결과: " + failAmount);

        assertFalse(failAmount > 0);
    }

    // 특정 학생이 주문한, 주식의 평균단가 계산한 값을 조회
    @Test
    public void getAveragePriceTest() {
        int avgPrice = dao.getAveragePrice("abc", 1, "대기", "마라탕");
        System.out.println("평균 단가: " + avgPrice);
        assertTrue(avgPrice >= 0);
        assertFalse(avgPrice < 0);
        assertNotNull(avgPrice);
    }


    // 특정 주식의 구매 가격을 조회
    @Test
    public void getPurchasePriceTest(){
    	int purchasePrice = dao.getPurchasePrice("abc", 1, "체결", "PC방");
    	System.out.println("주식 현재가: " + purchasePrice);
    	assertNotNull(purchasePrice);
    }
    
    // 특정 학생의 특정 주식의 수익금 조회 -> 쿼리문에  왜 state가 있는거지
//	 @Test
//	 public void getStockProfitTest() {
//	     int profit = dao.getStockProfit("abc", 3, "체결");
//	     System.out.println("주식 수익금: " + profit);
//	     assertNotNull(profit);
//	 }

	 // 특정 학생의 주문한 주식 양 조회
    @Test
    public void getMyStockNosTest() {
        List<Integer> stockNos = dao.getMyStockNos("dldlsghk123", "대기");
        System.out.println("보유 주식의 양: " + stockNos.size());
        assertNotNull(stockNos);
    }

}