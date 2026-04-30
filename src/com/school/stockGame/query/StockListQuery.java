package com.school.stockGame.query;

public interface StockListQuery {
	// 주식 목록 페이지 정보 조회
	
	// 1. 주식명 조회
	String STOCK_NAME_LIST_SQL="SELECT name FROM stocks ORDER BY stock_no";
	
	// 아래 쿼리는 StockDetail에 있는 거 가져오기로 함.
	// 2. 주식 현재가격 조회

	// 3. 주식 이전가격, 전장마감가 조회

	// 4. 주식 이전가 대비 조회

	// 5. 주식 등락률 조회

}
