package test.com.school.stockGame.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.school.stockGame.dao.NewsDAOInterface;
import com.school.stockGame.dao.mybatis.NewsDAOMybatis;

public class NewsDAOMybatisTest {
	public NewsDAOInterface dao = new NewsDAOMybatis();

	
	@Test
    public void getNewsContentListTest() {
		List<String> newsList = dao.getNewsList();
		System.out.println(newsList);
		assertNotNull(newsList);
	}
}
