package test.com.school.stockGame.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.NewsDAOInterface;
import com.school.stockGame.dao.NewsDAOMybatis;

public class NewsDAOTest {
    
    // 구현체 대신 인터페이스를 타입으로 지정하여 다형성을 활용합니다.
    private NewsDAOInterface dao;
    
    @Before
    public void setUp() {
        // 기존 JDBC DAO 대신 MyBatis DAO 구현체를 주입합니다.
        dao = new NewsDAOMybatis();
    }
    
    @Test
    public void testGetNewsContentList_성공() {
        List<String> newsList = dao.getNewsList();
        
        if (newsList.isEmpty()) {
            System.out.println("조회된 뉴스 데이터가 없습니다.");
        } else {
            System.out.println("=== 게임 뉴스 목록 (" + newsList.size() + "건) ===");
            for (int i = 0; i < newsList.size(); i++) {
                System.out.println((i + 1) + ". " + newsList.get(i));
            }
            System.out.println("===============================");
        }
        
        // MyBatis의 selectList는 결과가 없어도 null을 반환하지 않고 빈 리스트를 반환합니다.
        assertNotNull("MyBatis 조회 결과는 null이 아니어야 합니다.", newsList);
    }
}
