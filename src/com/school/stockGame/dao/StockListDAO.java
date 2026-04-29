package com.school.stockGame.dao;

import java.sql.Connection;

public class StockListDAO {
	public StockListDAO(){}
	
	public void getStockList(){
		try{
			Connection conn=DBCP.getConnection();
			conn.prepareStatement(StockListQuery.STOCK_NAME_LIST_SQL);
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}