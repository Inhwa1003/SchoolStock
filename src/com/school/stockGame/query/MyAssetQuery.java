package com.school.stockGame.query;

public interface MyAssetQuery {
	// 총 자산
	String MY_VALUE_SQL = "SELECT s.total_point + COALESCE(SUM(MY_VALUE.value), 0) "
            + "FROM students s LEFT JOIN (SELECT o.student_id, "
            + "SUM(CASE WHEN o.content = '매수' THEN o.amount ELSE -o.amount END) * "
            + "COALESCE((SELECT price FROM (SELECT price FROM orders WHERE stock_no = ? "
            + "AND state = '체결' ORDER BY order_date DESC) WHERE ROWNUM = 1), "
            + "st.publication_price) AS value FROM orders o "
            + "JOIN stocks st ON o.stock_no = st.stock_no WHERE o.state = '체결' "
            + "GROUP BY o.student_id, o.stock_no, st.publication_price) MY_VALUE "
            + "ON s.student_id = MY_VALUE.student_id WHERE s.student_id = ? "
            + "GROUP BY s.total_point";
	// 총 보유 포인트
	String TOTAL_POINT_SQL = "SELECT total_point FROM students WHERE student_id = ?";
	// 총 손익
	String TOTAL_PROFIT_SQL = "SELECT COALESCE(SUM(stock_profit), 0) AS totalProfit "
			+ "FROM (SELECT (SUM(CASE WHEN o.content = '매수' THEN o.amount "
			+ "ELSE -o.amount END) * COALESCE((SELECT price FROM (SELECT price FROM orders "
			+ "WHERE stock_no = ? AND state = '체결' ORDER BY order_date DESC) "
			+ "WHERE ROWNUM = 1), st.publication_price)) - SUM(CASE WHEN o.content = '매수' "
			+ "THEN o.price * o.amount ELSE 0 END) AS stock_profit FROM orders o "
			+ "JOIN stocks st ON o.stock_no = st.stock_no WHERE o.student_id = ? "
			+ "AND o.state = ? GROUP BY o.stock_no, st.publication_price)";
	// 보유 쿠폰 수량
	String TOTAL_COUPON_SQL = "SELECT total_coupon FROM students WHERE student_id = ?";
	// 보유 주식명
	String STOCK_NAME_SQL = "SELECT DISTINCT sto.name FROM students s JOIN orders o "
			+ "ON s.student_id = o.student_id JOIN stocks sto "
			+ "ON sto.stock_no = o.stock_no WHERE s.student_id = ?";
	// 보유 주식 수량
	String STOCK_AMOUNT_SQL = "SELECT SUM(CASE WHEN content = '매수' THEN amount ELSE -amount END) "
            + "FROM orders WHERE student_id = ? AND stock_no = ? AND state = ?";
	// 보유 주식 평균 단가
	String AVERAGE_PRICE_SQL = "SELECT SUM(price * amount) / SUM(amount) FROM orders "
            + "WHERE student_id = ? AND stock_no = ? AND state = ? AND content = ?";
	// 총 구매 비용
	String PURCHASE_PRICE_SQL = "SELECT SUM(price * amount) FROM orders "
			+ "WHERE student_id = ? AND stock_no = ? AND state = ? "
			+ "AND content = ?";
	// 수익금
	String STOCK_PROFIT_SQL = "SELECT ((SUM(CASE WHEN content = '매수' "
			+ "THEN amount ELSE -amount END) * (SELECT COALESCE((SELECT price FROM "
			+ "(SELECT price FROM orders WHERE stock_no = ? AND state = '체결' "
			+ "ORDER BY order_date DESC) WHERE ROWNUM = 1), st.publication_price) "
			+ "FROM stocks st WHERE st.stock_no = o.stock_no)) - "
			+ "SUM(CASE WHEN content = '매수' THEN price * amount ELSE 0 END)) AS profit "
			+ "FROM orders o WHERE student_id = ? AND stock_no = ? AND state = ? "
			+ "GROUP BY stock_no";
}