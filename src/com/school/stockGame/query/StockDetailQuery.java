package com.school.stockGame.query;

public interface StockDetailQuery {
	// 주식 기본 정보 조회
	String STOCK_INFO_SQL = "SELECT name, content FROM stocks WHERE stock_no = ?";
	
	// 주식 현재가격 조회
	String STOCK_PRICE_SQL = "SELECT price "
			+ "FROM (SELECT o.price "
			+ "FROM TRANSACTION t JOIN ORDERS o ON t.buy_order_no = o.order_no "
			+ "WHERE o.stock_no = ? "
			+ "ORDER BY t.TRANSACTION_DATE DESC)"
			+ "WHERE ROWNUM = 1";
	
	// 주식 이전가격  대비 조회
	String STOCK_PRICE_CHANGE_SQL = "SELECT price-s.prev_price "
			+ "FROM (SELECT o.price "
			+ "FROM TRANSACTION t JOIN ORDERS o ON t.buy_order_no = o.order_no "
			+ "WHERE o.stock_no = ? ORDER BY t.TRANSACTION_DATE DESC), stocks s "
			+ "WHERE ROWNUM = 1 AND s.stock_no = ? ";
	
	// 주식 등락률 조회
	String STOCK_CHANGE_RATE_SQL = "SELECT ROUND((price-s.prev_price)/s.prev_price*100) as Range "
			+ "FROM (SELECT o.price "
			+ "FROM TRANSACTION t JOIN ORDERS o ON t.buy_order_no = o.order_no "
			+ "WHERE o.stock_no = ? ORDER BY t.TRANSACTION_DATE DESC), stocks s "
			+ "WHERE ROWNUM = 1 AND s.stock_no = ? ";
	
	// 주식 이전가격 조회
	String STOCK_PREV_PRICE_SQL = "SELECT prev_price FROM stocks WHERE stock_no = ?";
	
	// 보유 주식 수량 조회 쿼리
	String STOCK_AMOUNT_SQL = "SELECT SUM(CASE WHEN content='매수' THEN amount ELSE -amount END) AS amount "
			+ "FROM orders "
			+ "WHERE student_id = ? "
			+ "AND stock_no = ? "
			+ "AND state='체결'";
	
	// 학생 보유 잔액 조회(주문 가능 잔여금액)
	String TOTAL_POINT_SQL ="SELECT total_point "
			+ "FROM students "
			+ "WHERE student_id = ?";
	
	// 주문 요청 (매도, 매수) 둘다 가능
	String ORDER_REQUEST = "INSERT INTO ORDERS (order_no, content, price, amount, state, order_date, student_id, stock_no) "
			+ "VALUES (order_no_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";
	
	// 대기중인 총 매도 매수 요청 리스트
	String TOTAL_ORDER_REQUEST = "SELECT price, amount, content "
			+ "FROM orders "
			+ "WHERE stock_no = ? "
			+ "AND STATE = '대기' "
			+ "ORDER BY order_date ASC";
	
	// 대기중인 총 매도 요청 리스트
	String TOTAL_SELL_ORDER_REQUEST = "SELECT price, amount, content "
			+ "FROM orders "
			+ "WHERE stock_no = ? "
			+ "AND STATE = '대기' "
			+ "AND content = '매도' "
			+ "ORDER BY order_date ASC";
	
	// 대기중인 총 매수 요청 리스트
	String TOTAL_BUY_ORDER_REQUEST = "SELECT price, amount, content "
			+ "FROM orders "
			+ "WHERE stock_no = ? "
			+ "AND STATE = '대기' "
			+ "AND content = '매수' "
			+ "ORDER BY order_date ASC";
	
	// 내 주문 요청 조회 먼저 등록된 순
	String MY_ORDER_REQUEST = "SELECT order_no, CONTENT, PRICE, AMOUNT, ORDER_DATE, STATE "
			+ "FROM ORDERS "
			+ "WHERE STUDENT_ID = ? "
			+ "AND STOCK_NO = ? "
			+ "AND state ='대기' "
			+ "ORDER BY ORDER_DATE ASC";
	
	// 내 주문 요청 취소
	String MY_ORDER_CANCEL = "";
	
	// 주문 완료시 포인트 up (주식 개수 * 주문가격, 학생아이디)
	String POINT_UP_SQL = "UPDATE students SET total_point = total_point + (?) "
			+ "WHERE student_id = ?";
	
	// 주문 완료시 포인트 down (주식 개수 * 주문가격, 학생아이디)
	String POINT_DOWN_SQL = "UPDATE students SET total_point = total_point - (?) "
			+ "WHERE student_id = ?";
	
	// 주문 요청 체결 완료 (매수no, 매도no)
	String MATCH_COMPLETE_INSERT_SQL = "INSERT INTO transaction (transaction_no, transaction_date, buy_order_no, sell_order_no) "
			+ "VALUES (transaction_no_seq.NEXTVAL, SYSDATE, ?, ?)";
	
	// 주문요청 상태 '대기' 변경 (주문no)
	String ORDER_STATE_PENDING_UPDATE_SQL = "UPDATE orders SET state = '대기' "
			+ "WHERE order_no = ?";

	// 주문요청 상태 '체결' 변경 (주문no)
	String ORDER_STATE_MATCHED_UPDATE_SQL = "UPDATE orders SET state = '체결' "
			+ "WHERE order_no = ?";

	// 주문요청 상태 '취소' 변경 (주문no)
	String ORDER_STATE_CANCEL_UPDATE_SQL = "UPDATE orders SET state = '취소' "
			+ "WHERE order_no = ?";

	// 주식 발행 정보 조회 (주식no)
	String PUBLICATION_DATA_SELECT_SQL = "SELECT publication_balance, publication_price "
			+ "FROM stocks "
			+ "WHERE stock_no = ?";
	
	// 주식 발행 개수 변경 (구입개수, 주식no)
	String PUBLICATION_DATA_UPDATE_SQL = "UPDATE stocks SET publication_balance = publication_balance - ? "
			+ "WHERE stock_no = ?";
	
	// 매수 주문 요청 매칭 
	String MATCH_BUY_ORDER_SQL = "SELECT order_no, content, price, amount, state, order_date, student_id, stock_no "
			+ "FROM orders "
			+ "WHERE order_no = "
			+ "(SELECT order_no "
			+ "FROM (SELECT order_no "
			+ "FROM orders "
			+ "WHERE stock_no = ? "
			+ "AND state = '대기' "
			+ "AND content = '매도' "
			+ "AND price = ? "
			+ "AND amount = ? "
			+ "AND student_id != ? "
			+ "ORDER BY order_date ASC) "
			+ "WHERE ROWNUM = 1) "
			+ "FOR UPDATE";
	// 직전에 내가 주문 요청한건 조회
	String FIND_MY_ORDER_SQL = "SELECT order_no "
			+ "FROM orders "
			+ "WHERE content = ? "
			+ "AND student_id = ? "
			+ "AND stock_no = ? "
			+ "AND state = ? "
			+ "AND amount = ? "
			+ "AND price = ? ORDER BY order_date DESC";
}