package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.school.stockGame.query.StockListQuery;

public class StockListDAO {

    public StockListDAO() {}

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
}