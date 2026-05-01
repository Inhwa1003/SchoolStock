package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.school.stockGame.dao.DBCP;
import com.school.stockGame.dao.StockDetailDAO;

public class StockDetailDAOTest {
	static StockDetailDAO dao;
	static Connection conn;
	@Test
	public void 주식기본정보조회테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getStockInfo(3));
	}
	@Test
	public void 주식현재가격조회테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getStockPrice(3));
	}
	@Test
	public void 주식이전가격대비조회테스트() throws ClassNotFoundException, SQLException{
		dao = new StockDetailDAO();
		System.out.println(dao.getStockPriceChange(3));
	}
	@Test
	public void 주식등락률조회테스트() throws ClassNotFoundException, SQLException{
		dao = new StockDetailDAO();
		System.out.println(dao.getChangeRate(3)+"%");
	}
	@Test
	public void 주식이전가격조회테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getPervPrice(3));
	}
	@Test
	public void 매도매수총조회테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getTotalOrder(1));
	}
	@Test
	public void 매도총조회테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getTotalSellOrder(1));
	}
	@Test
	public void 매수총조회테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getTotalBuyOrder(1));
	}
	@Test
	public void 내주문조회() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		System.out.println(dao.getTotalMyOrder(1, "abc"));
	}
//	@Test 테스트 잘못함
//	public void 내주문취소() throws ClassNotFoundException, SQLException {
//		dao = new StockDetailDAO();
//		//System.out.println(dao.myOrderCancel(12));
//	}
	@Test
	public void getPublishInfoTest() {
		dao = new StockDetailDAO();
		System.out.println(dao.getStockPubInfo(1));
	}
	
	@Test
	public void setMatchedOrderTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		//assertTrue(dao.setMatchedOrder(1, 4));
	}
	@Test
	public void setOrderStateCancelTest(){
		dao = new StockDetailDAO();
		//assertTrue(dao.setOrderStateCancel(14));
	}
	@Test
	public void setOrderStatePendingTest(){
		dao = new StockDetailDAO();
		//assertTrue(dao.setOrderStatePending(14));
	}
	@Test
	public void setOrderStateMatchedTest(){
		dao = new StockDetailDAO();
		//assertTrue(dao.setOrderStateMatched(14));
	}
	@Test
	public void setStockPubBalanceTest(){
		dao = new StockDetailDAO();
		//assertTrue(dao.setStockPubBalance(3, 1));
	}
	@Test
	public void getMatchBuyOrderTest(){
		dao = new StockDetailDAO();
		//수량O, 가격O
		//assertTrue(dao.getMatchBuyOrder(1, 1700, 1) != null);
		//가격O, 수량X
		//assertTrue(dao.getMatchBuyOrder(1, 1700, 2) == null);
		//가격X, 수량O
		//assertTrue(dao.getMatchBuyOrder(1, 1300, 1) == null);
	}
	@Test
	public void getStudentPointTest(){
		dao = new StockDetailDAO();
		// 보유 포인트를 반환 하는지 확인
		assertNotNull(dao.getStudentPoint("dong"));
		System.out.println(dao.getStudentPoint("dong"));
	}
	@Test
	public void getStudentStockAmountTest(){
		dao = new StockDetailDAO();
		// 보유 한 주식을 반환 하는지 확인
		assertNotNull(dao.getStudentStockAmount(1, "abc"));
		System.out.println(dao.getStudentStockAmount(1, "abc"));
	}
}