package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.StockListDAOInterface;
import com.school.stockGame.dao.StockListDAOMybatis;
import com.school.stockGame.vo.StockVO;

public class StockListDAOTest {
    
    private StockListDAOInterface dao;

    @Before
    public void setUp() {
        // 기존 JDBC DAO 대신 MyBatis DAO 구현체를 주입합니다.
        dao = new StockListDAOMybatis();
    }

    @Test
    public void testGetStockNameList() {
        List<StockVO> stockNameList = dao.getStockNameList();

        // MyBatis의 특징: 데이터가 없어도 null이 아니라 빈 리스트를 반환함
        assertNotNull("조회 결과 리스트는 null이 아니어야 합니다.", stockNameList);
        
        // 데이터베이스에 등록된 주식이 1개 이상일 것이라고 가정
        assertFalse("등록된 주식이 최소 1개 이상 존재해야 합니다.", stockNameList.isEmpty());

        System.out.println("=== 주식 목록 (" + stockNameList.size() + "건) ===");
        for (StockVO stockName : stockNameList) {
            System.out.println("주식번호: " + stockName.getStockNo() + " | 주식명: " + stockName.getName());
        }
        System.out.println("===========================");
    }
}
