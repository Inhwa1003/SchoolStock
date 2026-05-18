package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.school.stockGame.dao.MyPointHistoryDAOInterface;
import com.school.stockGame.dao.mybatis.MyPointHistoryDAOMybatis;

public class MyPointHistoryDAOMybatisTest {
	MyPointHistoryDAOInterface dao;
	@Test
	public void getMyPointHistoryListTest(){
		dao = new MyPointHistoryDAOMybatis();
		// NO
		// 존재 하지 않는 회원 (selectList특성 없으면 Null 반환x 빈List반환 없는 아이디로 존재하지 않는 회원인지 체크 불가 거래내역이 없다 정도는 체크가능)
		assertNotNull(dao.getMyPointHistoryList("sdo"));
		assertNotNull(dao.getMyPointHistoryList("sdof"));
		// YES
		assertNotNull(dao.getMyPointHistoryList("abc"));
		assertNotNull(dao.getMyPointHistoryList("def"));
		//System.out.println(dao.getMyPointHistoryList("abc"));
	}
}
