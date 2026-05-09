package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.school.stockGame.dao.MyPointHistoryDAOInterface;
import com.school.stockGame.dao.MyPointHistoryDAOMybatis;

public class MyPointHistoryDAOTest {

    private MyPointHistoryDAOInterface dao;
    // 이전 쿠폰 테스트에서 구매 이력이 확실하게 존재하는 아이디를 사용
    private final String validStudentId = "dldlsghk123";
    private final String invalidStudentId = "none";

    @Before
    public void setUp() {
        // 기존 JDBC DAO 대신 MyBatis DAO 구현체 주입
        dao = new MyPointHistoryDAOMybatis();
    }

    @Test
    public void testGetMyPointHistoryList_성공() {
        // 1. 내 포인트 내역 조회
        List<Map<String, Object>> historyList = dao.getMyPointHistoryList(validStudentId);

        // 2. null이 아니어야 함 (MyBatis는 결과가 없어도 빈 리스트 반환)
        assertNotNull(historyList);

        // 3. dldlsghk123 학생은 쿠폰 구매 내역이 최소 1개 이상 존재해야 함
        assertFalse("내역이 최소 1개 이상 존재해야 합니다.", historyList.isEmpty());

        // 4. 조회 결과 출력
        System.out.println("조회된 내 포인트 내역 개수: " + historyList.size());

        for (Map<String, Object> history : historyList) {
            System.out.println("날짜: " + history.get("historyDate"));
            System.out.println("유형: " + history.get("historyType"));
            System.out.println("내용: " + history.get("historyContent"));
            System.out.println("포인트 변화: " + history.get("pointChange"));
            System.out.println("-----------------------------");
        }
    }

    @Test
    public void testGetMyPointHistoryList_데이터없음() {
        // 1. 존재하지 않거나 거래 내역이 없는 학생 ID
        List<Map<String, Object>> historyList = dao.getMyPointHistoryList(invalidStudentId);

        // 2. 결과가 빈 리스트여야 함
        assertNotNull(historyList);
        assertTrue("거래 내역이 없는 학생은 빈 리스트가 반환되어야 합니다.", historyList.isEmpty());
    }
}
