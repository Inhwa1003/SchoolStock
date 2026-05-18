package test.com.school.stockGame.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.school.stockGame.dao.StockListDAOInterface;
import com.school.stockGame.dao.mybatis.StockListDAOMybatis;
import com.school.stockGame.vo.StockVO;

public class StockListDAOMybatisTest {
	StockListDAOInterface dao = new StockListDAOMybatis();
	
	@Test
	public void getStockNameListTest() {
		List<StockVO> stockList = dao.getStockNameList();
		
		assertNotNull(stockList);
	}
}
