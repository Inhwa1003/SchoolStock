package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.school.stockGame.dao.jdbc.StockListDAO;
import com.school.stockGame.vo.StockVO;

public class StockListDAOTest {

    @Test
    public void testGetStockNameList() {
        StockListDAO dao = new StockListDAO();

        List<StockVO> stockNameList = dao.getStockNameList();

        assertNotNull(stockNameList);
        assertEquals(3, stockNameList.size());

        System.out.println("조회된 주식 개수: " + stockNameList.size());

        for (StockVO stockName : stockNameList) {
            System.out.println("주식명: " + stockName.getName());
        }
    }
}