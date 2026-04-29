package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.school.stockGame.query.StockListQuery;

public class StockListDAO {

    public StockListDAO() {}
    
    // 1. 주식명 조회
    public List<String> getStockNameList() {
        List<String> stockNameList = new ArrayList<String>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        
        try {
            conn = DBCP.getConnection();

            pstmt = conn.prepareStatement(StockListQuery.STOCK_NAME_LIST_SQL);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String stockName = rs.getString("NAME");
                stockNameList.add(stockName);
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
        return stockNameList;
    }
    
    // 2. 주식 현재가격 조회  
    // 3. 주식 이전가격, 전장마감가 조회
    // 4. 주식 이전가 대비 조회
    // 5. 주식 등락률 조회
    // 6. 주식 목록 화면용 전체 정보 조회
    
    
}