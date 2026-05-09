package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.school.stockGame.dao.MemberDAOInterface;
import com.school.stockGame.dao.MemberDAOMybatis;

// 테스트 순서 보장을 위해 이름순으로 정렬
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberDAOTest {

    private MemberDAOInterface dao;
    // 매번 다른 아이디로 테스트하기 위해 UUID 사용
    private static final String testId = "test_" + UUID.randomUUID().toString().substring(0, 8);
    private static final String testPassword = "password123";
    private static final String testName = "테스터";

    @Before
    public void setUp() {
        dao = new MemberDAOMybatis();
    }

    @Test
    public void test1_회원가입_성공() {
        // 1. 새로운 아이디로 회원가입 시도
        boolean result = dao.addMember(testId, testPassword, testName, 1, "1", 1);
        assertTrue("새로운 아이디로 회원가입은 성공해야 합니다.", result);
    }

    @Test
    public void test2_아이디_중복체크_성공_존재하는경우() {
        // 2. 방금 가입한 아이디로 중복 체크
        boolean result = dao.idCheck(testId);
        assertTrue("방금 가입한 아이디는 중복으로 나와야 합니다.", result);
    }

    @Test
    public void test3_로그인_성공() {
        // 3. 방금 가입한 아이디와 비밀번호로 로그인
        Map<String, Object> memberInfo = dao.login(testId, testPassword);
        assertNotNull("로그인 성공 시 회원정보 Map은 null이 아니어야 합니다.", memberInfo);
        assertFalse("로그인 성공 시 회원정보 Map은 비어있지 않아야 합니다.", memberInfo.isEmpty());
        assertEquals("로그인 성공 시 이름이 일치해야 합니다.", testName, memberInfo.get("NAME")); // MyBatis는 대문자로 반환
    }
    
    @Test
    public void test4_회원가입_실패_중복아이디() {
        // 4. 이미 존재하는 아이디로 다시 회원가입 시도 (DB 제약조건에 의해 실패해야 함)
        boolean result = dao.addMember(testId, "anotherPass", "다른이름", 2, "2", 2);
        assertFalse("중복된 아이디로 회원가입은 실패해야 합니다.", result);
    }

    @Test
    public void test5_로그인_실패_틀린비밀번호() {
        // 5. 존재하는 아이디, 틀린 비밀번호로 로그인
        Map<String, Object> memberInfo = dao.login(testId, "wrong_password");
        // 로그인 실패 시 MyBatis selectOne은 null을 반환
        assertNull("로그인 실패 시 결과는 null이어야 합니다.", memberInfo);
    }

    @Test
    public void test6_로그인_실패_없는아이디() {
        // 6. 존재하지 않는 아이디로 로그인
        Map<String, Object> memberInfo = dao.login("non_existent_id_12345", testPassword);
        assertNull("존재하지 않는 아이디로 로그인 시 결과는 null이어야 합니다.", memberInfo);
    }

    @Test
    public void test7_아이디_중복체크_실패_존재하지않는경우() {
        // 7. 존재하지 않는 아이디로 중복 체크
        boolean result = dao.idCheck("non_existent_id_12345");
        assertFalse("존재하지 않는 아이디는 중복이 아니어야 합니다.", result);
    }
}
