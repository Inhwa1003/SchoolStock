package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.school.stockGame.dao.MemberDAOInterface;
import com.school.stockGame.dao.mybatis.DBCPMybatis;
import com.school.stockGame.vo.StudentVO;

public class MemberDAOMybatisTest {
	MemberDAOInterface dao;
	// 회원가입 테스트
	@Test
	public void setMemberTest() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			flag = session.insert("memberMapper.setMember", new StudentVO("test01", "123", "테스트", 5, "3", "33")) == 1;
			assertTrue(flag);
			if(flag){
				System.out.println("회원가입 테스트 완료");
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.rollback();
			session.close();
		}
	}
	
	// 로그인 테스트
	@Test
	public void loginTest() {
	}
	
	// 아이디 중복체크 테스트
	@Test
	public void getIdCheckTest() {
	}

}
