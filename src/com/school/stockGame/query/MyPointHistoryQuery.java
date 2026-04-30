package com.school.stockGame.query;

public interface MyPointHistoryQuery {

    String MY_POINT_HISTORY_SQL =
            "SELECT history_date, history_type, history_content, point_change " +
            "FROM ( " +

            "    SELECT " +
            "        cp.purchase_date AS history_date, " +
            "        '쿠폰' AS history_type, " +
            "        cp.name AS history_content, " +
            "        -cp.price AS point_change " +
            "    FROM COUPON_PURCHASE cp " +
            "    WHERE cp.student_id = ? " +

            "    UNION ALL " +

            "    SELECT " +
            "        gp.get_date AS history_date, " +
            "        '지급' AS history_type, " +
            "        gp.content AS history_content, " +
            "        gp.point AS point_change " +
            "    FROM GET_POINT gp " +
            "    WHERE gp.student_id = ? " +

            "    UNION ALL " +

            "    SELECT " +
            "        t.transaction_date AS history_date, " +
            "        '매수' AS history_type, " +
            "        s.name AS history_content, " +
            "        -(o.price * o.amount) AS point_change " +
            "    FROM TRANSACTION t, ORDERS o, STOCKS s " +
            "    WHERE t.buy_order_no = o.order_no " +
            "      AND o.stock_no = s.stock_no " +
            "      AND o.student_id = ? " +

            "    UNION ALL " +

            "    SELECT " +
            "        t.transaction_date AS history_date, " +
            "        '매도' AS history_type, " +
            "        s.name AS history_content, " +
            "        (o.price * o.amount) AS point_change " +
            "    FROM TRANSACTION t, ORDERS o, STOCKS s " +
            "    WHERE t.sell_order_no = o.order_no " +
            "      AND o.stock_no = s.stock_no " +
            "      AND o.student_id = ? " +

            ") " +
            "ORDER BY history_date DESC";
}