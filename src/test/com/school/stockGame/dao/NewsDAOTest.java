package test.com.school.stockGame.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.jdbc.NewsDAO;

public class NewsDAOTest {
	public NewsDAO dao;
	
	@Before
	public void setUp() {
		dao = new NewsDAO();
	}
	
	@Test
    public void testGetNewsContentList() {
		List<String> newsList = dao.getNewsList();
		
		if (newsList.isEmpty()) {
			System.out.println("조회된 뉴스 데이터가 없습니다.");
		} else {
			for (int i = 0; i < newsList.size(); i++) {
				System.out.println((i+1)+". "+newsList.get(i));
			}
		}
		assertNotNull(newsList);
	}
}
