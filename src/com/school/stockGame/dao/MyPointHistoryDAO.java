package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.school.stockGame.query.MyPointHistoryQuery;

public class MyPointHistoryDAO {

    public MyPointHistoryDAO() {}

    public List<Map<String, Object>> getMyPointHistoryList(String studentId) {
        List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBCP.getConnection();

            pstmt = conn.prepareStatement(MyPointHistoryQuery.MY_POINT_HISTORY_SQL);

            // 쿼리문에 ?가 4개 있으므로 studentId를 4번 넣어야 함
            pstmt.setString(1, studentId); // 쿠폰 구매
            pstmt.setString(2, studentId); // 포인트 지급
            pstmt.setString(3, studentId); // 매수 거래
            pstmt.setString(4, studentId); // 매도 거래

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> history = new HashMap<String, Object>();

                history.put("historyDate", rs.getDate("history_date"));
                history.put("historyType", rs.getString("history_type"));
                history.put("historyContent", rs.getString("history_content"));
                history.put("pointChange", rs.getInt("point_change"));

                historyList.add(history);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return historyList;
    }
}