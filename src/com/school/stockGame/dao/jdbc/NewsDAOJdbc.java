package com.school.stockGame.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.school.stockGame.query.NewsQuery;

public class NewsDAOJdbc {
	public NewsDAOJdbc() {}
	
	public List<String> getNewsList() {
        List<String> newsList = new ArrayList<String>();
        try {
        	Connection conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(NewsQuery.NEWS_DATA_SQL);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				newsList.add(rs.getString("content"));	
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return newsList;
	}
}
