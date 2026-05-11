package test.com.school.stockGame.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.school.stockGame.dao.jdbc.MyPointHistoryDAO;

public class MyPointHistoryDAOTest {

    @Test
    public void testGetMyPointHistoryList() {
        // 1. DAO 객체 생성
        MyPointHistoryDAO dao = new MyPointHistoryDAO();

        // 2. 테스트할 학생 ID
        String studentId = "abc";

        // 3. 내 포인트 내역 조회
        List<Map<String, Object>> historyList = dao.getMyPointHistoryList(studentId);

        // 4. null이 아니어야 함
        assertNotNull(historyList);

        // 5. abc 학생은 포인트 내역이 최소 1개 있다고 했으므로 1개 이상이어야 함
        assertTrue(historyList.size() > 0);

        // 6. 조회 결과 출력
        System.out.println("조회된 내 포인트 내역 개수: " + historyList.size());

        for (Map<String, Object> history : historyList) {
            System.out.println("날짜: " + history.get("historyDate"));
            System.out.println("유형: " + history.get("historyType"));
            System.out.println("내용: " + history.get("historyContent"));
            System.out.println("포인트 변화: " + history.get("pointChange"));
            System.out.println("-----------------------------");
        }
    }
}