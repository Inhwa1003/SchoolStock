package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.school.stockGame.dao.jdbc.DBCP;
import com.school.stockGame.dao.jdbc.StockDetailDAOJdbc;

public class StockDetailDAOTest {
	static StockDetailDAOJdbc dao;
	static Connection conn;
	@Test
	public void getStockInfoTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getStockInfo(3));
	}
	@Test
	public void getStockPriceTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getStockPrice(3));
	}
	@Test
	public void getStockPriceChangeTest() throws ClassNotFoundException, SQLException{
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getStockPriceChange(3));
	}
	@Test
	public void getChangeRateTest() throws ClassNotFoundException, SQLException{
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getChangeRate(3)+"%");
	}
	@Test
	public void getPervPriceTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getPervPrice(3));
	}
	@Test
	public void getTotalOrderTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getTotalOrder(1));
	}
	@Test
	public void getTotalSellOrderTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getTotalSellOrder(1));
	}
	@Test
	public void getTotalBuyOrderTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getTotalBuyOrder(1));
	}
	@Test
	public void getTotalMyOrderTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getTotalMyOrder(1, "abc"));
	}
	@Test
	public void getPublishInfoTest() {
		dao = new StockDetailDAOJdbc();
		System.out.println(dao.getStockPubInfo(1));
	}
	
	@Test
	public void setMatchedOrderTest() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAOJdbc();
		//assertTrue(dao.setMatchedOrder(1, 4));
	}
	@Test
	public void setOrderStateCancelTest(){
		dao = new StockDetailDAOJdbc();
		//assertTrue(dao.setOrderStateCancel(14));
	}
	@Test
	public void setOrderStatePendingTest(){
		dao = new StockDetailDAOJdbc();
		//assertTrue(dao.setOrderStatePending(14));
	}
	@Test
	public void setOrderStateMatchedTest(){
		dao = new StockDetailDAOJdbc();
		//assertTrue(dao.setOrderStateMatched(14));
	}
	@Test
	public void setStockPubBalanceTest(){
		dao = new StockDetailDAOJdbc();
		//assertTrue(dao.setStockPubBalance(3, 1));
	}
	@Test
	public void getMatchBuyOrderTest(){
		dao = new StockDetailDAOJdbc();
		//수량O, 가격O
		//assertTrue(dao.getMatchBuyOrder(1, 1700, 1) != null);
		//가격O, 수량X
		//assertTrue(dao.getMatchBuyOrder(1, 1700, 2) == null);
		//가격X, 수량O
		//assertTrue(dao.getMatchBuyOrder(1, 1300, 1) == null);
	}
	@Test
	public void getStudentPointTest(){
		dao = new StockDetailDAOJdbc();
		// 보유 포인트를 반환 하는지 확인
		assertNotNull(dao.getStudentPoint("dong"));
		System.out.println(dao.getStudentPoint("dong"));
	}
	@Test
	public void getStudentStockAmountTest(){
		dao = new StockDetailDAOJdbc();
		// 보유 한 주식을 반환 하는지 확인
		assertNotNull(dao.getStudentStockAmount(1, "abc"));
		System.out.println(dao.getStudentStockAmount(1, "abc"));
	}
	@Test
	public void setOrderRequetTest(){
		dao = new StockDetailDAOJdbc();
		// 매수 주문 요청
		//assertTrue(dao.setOrderRequest("매수", 1200, 2, "대기", "dong", 1));
		// 매도 주문 요청
		//assertTrue(dao.setOrderRequest("매도", 1400, 2, "대기", "dong", 1));
	}
	@Test
	public void setStudentPointDownTest(){
		dao = new StockDetailDAOJdbc();
		//학생 포인트 내려감
		//assertTrue(dao.setStudentPointDown(200, "dong"));
		//학생 포인트 올라감 x
		//assertTrue(dao.setStudentPointDown(-200, "dong"));
		//학생 포인트 내려감
		//assertTrue(dao.setStudentPointDown(+200, "dong"));
	}
	@Test
	public void setStudentPointUpTest(){
		dao = new StockDetailDAOJdbc();
		//학생 포인트 올라감
		//assertTrue(dao.setStudentPointUp(200, "dong"));
		//학생 포인트 올라감 
		//assertTrue(dao.setStudentPointUp(-200, "dong"));
		//학생 포인트 올라감
		//assertTrue(dao.setStudentPointUp(+200, "dong"));
	}
}
