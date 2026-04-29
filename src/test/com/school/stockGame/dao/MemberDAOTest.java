package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.school.stockGame.dao.MemberDAO;

public class MemberDAOTest {

	//@Test
	public void 회원가입_테스트() throws Exception {
		MemberDAO dao=new MemberDAO();
		boolean result=dao.addMember("kosta", "1234!", "최현곤", 5, "4", 24);
				assertTrue(result);
	}
	//@Test
	public void 회원가입_실패_테스트_중복아이디() throws Exception {
		MemberDAO dao=new MemberDAO();
		boolean result=dao.addMember("kosta", "12345", "김현곤", 5, "4", 24);
				assertTrue(result);
	}
	//@Test
	public void 회원가입_실패_테스트_학년입력() throws Exception {
		MemberDAO dao=new MemberDAO();
		boolean result=dao.addMember("kosta1", "12345", "김현곤", 99, "4", 24);
				assertTrue(result);
	}
	//@Test //TODO 음수 처리 막기
	public void 회원가입_테스트_음수학년() throws Exception {
		MemberDAO dao=new MemberDAO();
		boolean result=dao.addMember("kosta1", "12345", "김현곤", -9, "4", 24);
				assertTrue(result);
	}
	//@Test
	public void 회원가입_테스트_특수기호_아이디() throws Exception {
		MemberDAO dao=new MemberDAO();
		boolean result=dao.addMember("kosta!", "12345", "김현곤", 7, "4", 24);
				assertTrue(result);
	}
	//@Test //동일년도 동일학년 동일 반에 같은 번호 존재하지 않게 처리하는 방법?
	public void 회원가입_테스트_특수기호_반() throws Exception {
		MemberDAO dao=new MemberDAO();
		boolean result=dao.addMember("kostaa", "12345", "김현곤", 7, "미!@", 24);
				assertTrue(result);
	}

	//@Test
	public void 로그인_테스트() throws Exception{
		MemberDAO dao=new MemberDAO();
		Map<String, Object> m=dao.login("kosta", "1234!");
		assertEquals(m.get("name"), "최현곤");
		System.out.println(m);
	}
	//@Test
	public void 로그인_실패_테스트() throws Exception{
		MemberDAO dao=new MemberDAO();
		Map<String, Object> m=dao.login("kosta", "1235!");
		assertEquals(m.get("name"), "최현곤");
		System.out.println(m);
	}
	//@Test
	public void 로그인_실패_테스트2() throws Exception{
		MemberDAO dao=new MemberDAO();
		Map<String, Object> m=dao.login("kost", "1234!");
		assertEquals(m.get("name"), "최현곤");
		System.out.println(m);
	}
	//@Test
	public void 아이디_중복체크_테스트() throws Exception{
		MemberDAO dao=new MemberDAO();
		boolean result=dao.idCheck("kosta");
		assertTrue(result);
	}
		
	//@Test
	public void 아이디_중복체크_테스트_실패() throws Exception{
		MemberDAO dao=new MemberDAO();
		boolean result=dao.idCheck("chlgusrhs");
		assertTrue(result);
		
	}
}
