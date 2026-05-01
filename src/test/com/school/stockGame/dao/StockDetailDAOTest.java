package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

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
	public void 매도기능테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		//YES
		//System.out.println(dao.setSellOrder(1, "abc", 1, 1700));

	}
	@Test
	public void 매수기능테스트() throws ClassNotFoundException, SQLException {
		dao = new StockDetailDAO();
		//yes
		//System.out.println(dao.setBuyOrder(1, "abc", 2, 1600));
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
	public void getPublishInfo(){
		dao = new StockDetailDAO();
		System.out.println(dao.getStockPublishInfo(1));
	}

}