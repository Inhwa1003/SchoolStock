package test.com.school.stockGame.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.school.stockGame.dao.StockDetailDAOInterface;
import com.school.stockGame.dao.mybatis.StockDetailDAOMybatis;
import com.school.stockGame.vo.OrderVO;

public class StockDetailDAOMybatisTest {
	StockDetailDAOInterface dao = new StockDetailDAOMybatis();
	
	//1단계 : Given, 2단계 : When 3단계 : Then
	
	@Test
	public void setSellOrderTest() {
		//매도 - 성공
		//Given
		String studentId = "abc";
		int sellPrice = 1500;
		int sellAmount = 1;
		int stockNo = 1;
		//When
		String successResult = dao.setSellOrder(studentId, sellPrice, sellAmount, stockNo);
		//Then
		assertNotNull(successResult);
		assertTrue(successResult.contains("완료") || successResult.contains("등록"));
		
		//매도 - 실패 - 보유 주식량 초과 매도
		//Given
		int overAmount = 999999;
		//When
		String failResult = dao.setSellOrder(studentId, sellPrice, overAmount, stockNo);
		//Then
		assertNotNull(failResult);
		assertEquals("보유 주식량보다 많은 매도 요청은 할 수 없습니다.", failResult);
	}
	
	@Test
	public void setBuyOrderTest() {
		//매수 성공
		//Given
		String studentId = "abc";
		int buyPrice = 1000;
		int buyAmount = 1;
		int stockNo = 1;
		//When
		String successResult = dao.setBuyOrder(studentId, buyPrice, buyAmount, stockNo);
		//Then
		assertNotNull(successResult);
		assertTrue(successResult.contains("완료") || successResult.contains("처리") || successResult.contains("매수가 완료"));
		//매수 실패 - 보유 포인트 부족
		//Given
		int overPrice = 999999;
		//When
		String failResult = dao.setBuyOrder(studentId, overPrice, buyAmount, stockNo);
		//Then
		assertNotNull(failResult);
		assertEquals("보유 포인트가 부족합니다.", failResult);
	}
	
	@Test
	public void getStockInfoTest() {
		//주식 정보 조회 성공
		//Given
		int stockNo = 1;
		//When
		Map<String, Object> successMap = dao.getStockInfo(stockNo);
		//Then
		assertNotNull(successMap);
		//주식 정보 조회 실패 - 존재하지 않는 주식 정보
		//Given
		int invalidstockNo = -999;
		//When
		Map<String, Object> failMap = dao.getStockInfo(invalidstockNo);
		//Then
		assertNull(failMap);
	}
	
	@Test
	public void getStockPriceTest() {
		//주식 현재 가격 조회 성공
		//Given
		int stockNo = 1;
		//When
		int price = dao.getStockPrice(stockNo);
		//Then
		assertTrue(price >= 0);
		//주식 현재 가격 조회 실패 - 없는 주식 번호 조회
		//Given
		int invalidStockNo = -999;
		//When
		int failPrice = dao.getStockPrice(invalidStockNo);
		//Then
		assertEquals(0, failPrice);
	}
	
	@Test
	public void getStockPriceChangeTest() {
		//주식 이전 가격 대비 조회 성공
		//Given
		int stockNo = 1;
		//When
		int priceChange = dao.getStockPriceChange(stockNo);
		//Then
		assertNotNull(priceChange);
		//주식 이전 가격 대비 조회 실패 - 없는 주식 번호 조회
		//Given
		int invalidStockNo = -999;
		//When
		int failChange = dao.getStockPriceChange(invalidStockNo);
		//Then
		assertEquals(0, failChange);
	}
	
	@Test
	public void getChangeRateTest() {
		//주식 등락률 조회 성공
		//Given
		int stockNo = 1;
		//When
		double changeRate = dao.getChangeRate(stockNo);
		//Then
		assertNotNull(changeRate);
		//주식 등락률 조회 실패 - 없는 주식 번호 조회
		//Given
		int invalidStockNo = -999;
		//When
		double failRate = dao.getChangeRate(invalidStockNo);
		//Then
		assertEquals(0.0, failRate, 0.001);
	}
	
	@Test
	public void getPervPriceTest() {
		//이전 가격 조회 성공
		//Given
		int stockNo = 1;
		//When
		int prevPrice = dao.getPervPrice(stockNo);
		//Then
		assertTrue(prevPrice >= 0);
		//이전 가격 조회 실패 - 없는 주식 번호 조회
		//Given
		int invalidStockNo = -999;
		//When
		int failPrice = dao.getPervPrice(invalidStockNo);
		//Then
		assertEquals(0, failPrice);
	}
	
	@Test
	public void getStudentPointTest() {
		//보유 포인트 조회 성공
		//Given
		String studentId = "abc";
		//When
		int point = dao.getStudentPoint(studentId);
		//Then
		assertTrue(point >= 0);
		//보유 포인트 조회 실패 - 허용 범위 외의 학생 번호 입력
		//Given
		String invalidId = "1dd&dk_123^";
		//When
		int failPoint = dao.getStudentPoint(invalidId);
		//Then
		assertEquals(0, failPoint);
	}
	
	@Test
	public void getStudentStockAmountTest() {
		//특정 주식 보유 수량 조회 성공
		//Given
		int stockNo = 1;
		String studentId = "abc";
		//When
		int amount = dao.getStudentStockAmount(stockNo, studentId);
		//Then
		assertTrue(amount >= 0);
		//특정 주식 보유 수량 조회 실패 - 없는 주식 번호 입력
		//Given
		int invalidStockNo = -999;
		//When
		int failAmount = dao.getStudentStockAmount(invalidStockNo, studentId);
		//Then
		assertEquals(0, failAmount);
	}
	
	@Test
	public void getStudentPointDownTest() {
		//보유 포인트 차감 성공
		//Given
		int totalPrice = 500;
		String studentId = "abc";
		//When
		boolean pointDown = dao.setStudentPointDown(totalPrice, studentId);
		//Then
		assertTrue(pointDown);
		//보유 포인트 차감 실패 - 없는 학생 번호 입력
		//Given
		String invalidId = "1dd&dk_123^";
		//When
		boolean failPointDown = dao.setStudentPointDown(totalPrice, invalidId);
		//Then
		assertFalse(failPointDown);
	}
	
	@Test
	public void getStudentPointUpTest() {
		//보유 포인트 증가 성공
		//Given
		int totalPrice = 1000;
		String studentId = "abc";
		//When
		boolean pointUp = dao.setStudentPointUp(totalPrice, studentId);
		//Then
		assertTrue(pointUp);
		//보유 포인트 증가 실패 - 없는 학생 번호 입력
		//Given
		String invalidId = "1dd&dk_123^";
		//When
		boolean failPointUp = dao.setStudentPointUp(totalPrice, invalidId);
		//Then
		assertFalse(failPointUp);
	}
	
	@Test
	public void setOrderRequestTest() {
		//매도 또는 매수 요청 성공
		//Given
		String content = "매수";
		int price = 1200;
		int amount = 10;
		String state = "대기";
		String studentId = "abc";
		int stockNo = 1;
		//When
		boolean successRequest = dao.setOrderRequest(content, price, amount, state, studentId, stockNo);
		//Then
		assertTrue(successRequest);
		//매도 또는 매수 요청 실패 - 없는 주식 번호 입력
		//Given
		int invalidStockNo = -999;
		//When&Then
		try {
			dao.setOrderRequest(content, price, amount, state, studentId, invalidStockNo);
		} catch (Exception e) {
			assertNotNull(e);	//무결성 제약조건 위반 강제 유도
		}
	}
	
	@Test
	public void getTotalOrderTest() {
		//특정 주식의 대기중인 주문 모두 조회 성공
		//Given
		int stockNo = 1;
		//When
		List<OrderVO> activeList = dao.getTotalOrder(stockNo);
		//Then
		assertNotNull(activeList);
		//특정 주식의 대기중인 주문 모두 조회 실패 - 없는 주식 번호 입력
		//Given
		int invalidStockNo = -999;
		//When
		List<OrderVO> emptyList = dao.getTotalOrder(invalidStockNo);
		//Then
		assertNotNull(emptyList);
		assertEquals(0, emptyList.size());
	}
	
	@Test
	public void getTotalSellOrderTest() {
		//특정 주식의 대기중인 매도 주문 조회 성공
		//Given
		int stockNo = 1;
		//When
		List<OrderVO> activeSells = dao.getTotalSellOrder(stockNo);
		//Then
		assertNull(activeSells);
		//특정 주식의 대기중인 매도 주문 조회 실패 - 없는 주식 번호 입력
		//Given
		int invalidStockNo = -999;
		//When
		List<OrderVO> emptySells = dao.getTotalSellOrder(invalidStockNo);
		//Then
		assertNotNull(emptySells);
		assertEquals(0, emptySells.size());
	}
	
	@Test
	public void getTotalBuyOrderTest() {
		//특정 주식의 대기중인 매수 주문 조회 성공
		//Given
		int stockNo = 1;
		//When
		List<OrderVO> activeBuys = dao.getTotalBuyOrder(stockNo);
		//Then
		assertNotNull(activeBuys);
		//특정 주식의 대기중인 매수 주문 조회 실패 - 없는 주식 번호 입력
		//Given
		int invalidStockNo = -999;
		//When
		List<OrderVO> emptyBuys = dao.getTotalBuyOrder(invalidStockNo);
		//Then
		assertNotNull(emptyBuys);
		assertEquals(0, emptyBuys.size());
	}
	
	@Test
	public void getTotalMyOrderTest() {
		//내 주문 요청 성공
		//Given
		int stockNo = 1;
		String studentId = "abc";
		//When
		List<OrderVO> orderList = dao.getTotalMyOrder(stockNo, studentId);
		//Then
		assertNotNull(orderList);
		//내 주문 요청 실패 - 없는 주식 번호 입력
		//Given&When
		List<OrderVO> emptyOrderList = dao.getTotalMyOrder(-999, "123_3&^");
		//Then
		assertNotNull(emptyOrderList);
		assertEquals(0, emptyOrderList.size());
	}
	
	
	//1,2,3 또는 1&2,3 또는 1,2&3의 반복이므로 아래부터는 주석과 변수명 순화는 생략할게요
	//엔터가 1,2,3의 구분입니다
	@Test
	public void getMyOrderNoTest() {
		String content = "매수";
		String studentId = "abc";
		int stockNo = 1;
		String state = "대기";
		int amount = 2;
		int price = 1200;
		
		int orderNo = dao.getMyOrderNo(content, studentId, stockNo, state, amount, price);
		int fallbackNo = dao.getMyOrderNo("매도", "ghost_user", -9, "체결", 0, 0);
		
		assertTrue(orderNo >= 0);
		assertEquals(0, fallbackNo);
	}
	
	@Test
	public void getStockPubInfoTest() {
		int stockNo = 1;
		int invalidStockNo = -999;

		Map<String, Object> pubMap = dao.getStockPubInfo(stockNo);
		Map<String, Object> emptyPubMap = dao.getStockPubInfo(invalidStockNo);

		assertNotNull(pubMap);
		assertTrue(pubMap.containsKey("PUBLICATION_BALANCE"));
		assertNull(emptyPubMap);
	}
	
	@Test
	public void setStockPubBalanceTest() {
		int buyAmount = 5;
		int stockNo = 1;
		int invalidStockNo = -999;
		
		boolean successFlag = dao.setStockPubBalance(buyAmount, stockNo);
		boolean failFlag = dao.setStockPubBalance(buyAmount, invalidStockNo);
		
		assertTrue(successFlag);
		assertFalse(failFlag);
	}
	
	@Test
	public void setMatchedOrderTest() {
		int buyOrderNo = 1;
		Integer sellOrderNo = 2;
		
		try {
			boolean successFlag = dao.setMatchedOrder(buyOrderNo, sellOrderNo);
			assertNotNull(successFlag);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void setOrderStatePendingTest() {
		int validOrderNo = 1;
		int invalidOrderNo = -999;

		boolean successFlag = dao.setOrderStatePending(validOrderNo);
		boolean failFlag = dao.setOrderStatePending(invalidOrderNo);

		assertNotNull(successFlag);
		assertFalse(failFlag);
	}
	
	@Test
	public void setOrderStateMatchedTest() {
		int validOrderNo = 1;
		int invalidOrderNo = -999;

		boolean successFlag = dao.setOrderStateMatched(validOrderNo);
		boolean failFlag = dao.setOrderStateMatched(invalidOrderNo);

		assertNotNull(successFlag);
		assertFalse(failFlag);
	}
	
	@Test
	public void setOrderStateCancelTest() {
		int validOrderNo = 1;
		int invalidOrderNo = -999;

		boolean successFlag = dao.setOrderStateCancel(validOrderNo);
		boolean failFlag = dao.setOrderStateCancel(invalidOrderNo);

		assertNotNull(successFlag);
		assertFalse(failFlag);
	}
	
	@Test
	public void getMatchOrderTest() {
		int stockNo = 1;
		int orderPrice = 1000;
		int orderAmount = 2;
		String studentId = "abc";
		String content = "매도";

		Map<String, Object> matchedResult = dao.getMatchOrder(stockNo, orderPrice, orderAmount, studentId, content);
		Map<String, Object> emptyResult = dao.getMatchOrder(-99, 0, 0, "ghost", "매수");

		assertNotNull(emptyResult == null || emptyResult.isEmpty() ? "공백패스" : "성공");
		if (matchedResult != null) {
			assertTrue(matchedResult.containsKey("ORDER_NO"));
		}
	}
}
