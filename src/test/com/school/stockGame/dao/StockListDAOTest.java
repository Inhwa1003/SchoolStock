package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.school.stockGame.dao.StockListDAO;

public class StockListDAOTest {

    @Test
    public void testGetStockNameList() {
        StockListDAO dao = new StockListDAO();

        List<String> stockNameList = dao.getStockNameList();

        assertNotNull(stockNameList);
        assertEquals(3, stockNameList.size());

        System.out.println("조회된 주식 개수: " + stockNameList.size());

        for (String stockName : stockNameList) {
            System.out.println("주식명: " + stockName);
        }
    }
}