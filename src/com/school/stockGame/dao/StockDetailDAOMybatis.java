package com.school.stockGame.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.vo.OrderVO;

public class StockDetailDAOMybatis implements StockDetailDAOInterface {

    private MyAssetDAOMybatis myAssetDAO = new MyAssetDAOMybatis();

    @Override
    public String setSellOrder(String studentId, int sellPrice, int sellAmount, int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            try {
                Map<String, Object> pubInfo = getStockPubInfo(session, stockNo);
                int pubAmount = pubInfo != null && pubInfo.get("PUBLICATION_BALANCE") != null ? ((Number) pubInfo.get("PUBLICATION_BALANCE")).intValue() : 0;
                
                if (pubAmount > 0) {
                    return "발행 잔량이 남아 매도요청 할 수 없습니다.";
                }

                // myAssetDAO의 getStockAmount는 별도의 session을 사용하므로, 트랜잭션 동기화가 완전히 되진 않지만
                // 현재 상태 확인용으로는 사용 가능 (체결된 수량 조회)
                int myStockAmount = myAssetDAO.getStockAmount(studentId, stockNo, "체결");
                if (myStockAmount < sellAmount) {
                    return "보유 주식량보다 많은 매도 요청은 할 수 없습니다.";
                }

                Map<String, Object> matchOrder = getMatchOrder(session, stockNo, sellPrice, sellAmount, studentId, "매수");

                if (matchOrder != null && !matchOrder.isEmpty()) {
                    int matchOrderNo = ((Number) matchOrder.get("ORDER_NO")).intValue();
                    setOrderStateMatched(session, matchOrderNo);
                    setOrderRequest(session, "매도", sellPrice, sellAmount, "체결", studentId, stockNo);
                    int myOrderNo = getMyOrderNo(session, "매도", studentId, stockNo, "체결", sellAmount, sellPrice);
                    setMatchedOrder(session, matchOrderNo, myOrderNo);
                    setStudentPointUp(session, (sellPrice * sellAmount), studentId);
                    
                    session.commit();
                    return "매도가 완료되었습니다.";
                } else {
                    setOrderRequest(session, "매도", sellPrice, sellAmount, "대기", studentId, stockNo);
                    session.commit();
                    return "매도주문이 대기처리 되었습니다.";
                }
            } catch (Exception e) {
                session.rollback();
                e.printStackTrace();
                return "매도 불가";
            }
        }
    }

    @Override
    public String setBuyOrder(String studentId, int buyPrice, int buyAmount, int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            try {
                if (getStudentPoint(session, studentId) < (buyPrice * buyAmount)) {
                    return "보유포인트가 부족합니다.";
                }

                Map<String, Object> pubInfo = getStockPubInfo(session, stockNo);
                int pubAmount = pubInfo != null && pubInfo.get("PUBLICATION_BALANCE") != null ? ((Number) pubInfo.get("PUBLICATION_BALANCE")).intValue() : 0;
                int pubPrice = pubInfo != null && pubInfo.get("PUBLICATION_PRICE") != null ? ((Number) pubInfo.get("PUBLICATION_PRICE")).intValue() : 0;

                if (pubAmount >= buyAmount) {
                    if (pubPrice <= buyPrice) {
                        setStockPubBalance(session, buyAmount, stockNo);
                        setOrderRequest(session, "매수", pubPrice, buyAmount, "체결", studentId, stockNo);
                        int myOrderNo = getMyOrderNo(session, "매수", studentId, stockNo, "체결", buyAmount, pubPrice);
                        setMatchedOrder(session, myOrderNo, null);
                        setStudentPointDown(session, (pubPrice * buyAmount), studentId);
                        
                        session.commit();
                        return "발행 가격 " + pubPrice + "P 매수가 완료 되었습니다. 남은 발행잔량은 " + (pubAmount - buyAmount) + "주 입니다.";
                    } else if (pubAmount < buyAmount) {
                        return "남은 발행잔량은 " + pubAmount + "주, 발행가격은" + pubPrice + "P 입니다. " + pubAmount + "주 이하로만 매수 가능합니다.";
                    }
                }

                if (pubAmount > 0) {
                    return "발행 잔량이 남아 매수요청 할 수 없습니다.";
                }

                Map<String, Object> matchOrder = getMatchOrder(session, stockNo, buyPrice, buyAmount, studentId, "매도");

                if (matchOrder != null && !matchOrder.isEmpty()) {
                    int matchOrderNo = ((Number) matchOrder.get("ORDER_NO")).intValue();
                    String matchStudentId = (String) matchOrder.get("STUDENT_ID");
                    
                    setOrderStateMatched(session, matchOrderNo);
                    setOrderRequest(session, "매수", buyPrice, buyAmount, "체결", studentId, stockNo);
                    int myOrderNo = getMyOrderNo(session, "매수", studentId, stockNo, "체결", buyAmount, buyPrice);
                    setMatchedOrder(session, myOrderNo, matchOrderNo);
                    setStudentPointDown(session, (buyPrice * buyAmount), studentId);
                    setStudentPointUp(session, (buyPrice * buyAmount), matchStudentId);
                    
                    session.commit();
                    return "매수가 완료되었습니다.";
                } else {
                    setOrderRequest(session, "매수", buyPrice, buyAmount, "대기", studentId, stockNo);
                    setStudentPointDown(session, (buyPrice * buyAmount), studentId);
                    session.commit();
                    return "매수주문이 대기처리 되었습니다.";
                }
            } catch (Exception e) {
                session.rollback();
                e.printStackTrace();
                return "매수 불가";
            }
        }
    }

    // --- Private Helper Methods for Transactions ---

    private Map<String, Object> getStockPubInfo(SqlSession session, int stockNo) {
        return session.selectOne("stockGameMapper.getPublicationData", stockNo);
    }

    private int getStudentPoint(SqlSession session, String studentId) {
        Integer result = session.selectOne("stockGameMapper.getStudentTotalPoint", studentId);
        return result == null ? 0 : result;
    }

    private boolean setStockPubBalance(SqlSession session, int amount, int stockNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("amount", amount);
        param.put("stockNo", stockNo);
        return session.update("stockGameMapper.updatePublicationData", param) > 0;
    }

    private boolean setOrderRequest(SqlSession session, String content, int price, int amount, String state, String studentId, int stockNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("content", content);
        param.put("price", price);
        param.put("amount", amount);
        param.put("state", state);
        param.put("studentId", studentId);
        param.put("stockNo", stockNo);
        return session.insert("stockGameMapper.insertOrderRequest", param) > 0;
    }

    private int getMyOrderNo(SqlSession session, String content, String studentId, int stockNo, String state, int amount, int price) {
        Map<String, Object> param = new HashMap<>();
        param.put("content", content);
        param.put("studentId", studentId);
        param.put("stockNo", stockNo);
        param.put("state", state);
        param.put("amount", amount);
        param.put("price", price);
        Integer result = session.selectOne("stockGameMapper.findMyOrder", param);
        return result == null ? 0 : result;
    }

    private boolean setMatchedOrder(SqlSession session, int buyOrderNo, Integer sellOrderNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("buyOrderNo", buyOrderNo);
        param.put("sellOrderNo", sellOrderNo);
        return session.insert("stockGameMapper.insertMatchComplete", param) > 0;
    }

    private boolean setStudentPointDown(SqlSession session, int point, String studentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("point", point);
        param.put("studentId", studentId);
        return session.update("stockGameMapper.pointDown", param) > 0;
    }

    private boolean setStudentPointUp(SqlSession session, int point, String studentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("point", point);
        param.put("studentId", studentId);
        return session.update("stockGameMapper.pointUp", param) > 0;
    }

    private boolean setOrderStateMatched(SqlSession session, int orderNo) {
        return session.update("stockGameMapper.updateOrderStateMatched", orderNo) > 0;
    }

    private Map<String, Object> getMatchOrder(SqlSession session, int stockNo, int price, int amount, String studentId, String content) {
        Map<String, Object> param = new HashMap<>();
        param.put("stockNo", stockNo);
        param.put("price", price);
        param.put("amount", amount);
        param.put("studentId", studentId);
        param.put("content", content);
        return session.selectOne("stockGameMapper.getMatchOrder", param);
    }

    // --- Public Methods (Non-transactional or self-contained) ---

    @Override
    public Map<String, Object> getStockInfo(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return session.selectOne("stockGameMapper.getStockInfo", stockNo);
        }
    }

    @Override
    public int getStockPrice(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Integer result = session.selectOne("stockGameMapper.getStockPrice", stockNo);
            return result == null ? 0 : result;
        }
    }

    @Override
    public int getStockPriceChange(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Map<String, Object> param = new HashMap<>();
            param.put("stockNo", stockNo);
            Integer result = session.selectOne("stockGameMapper.getStockPriceChange", param);
            return result == null ? 0 : result;
        }
    }

    @Override
    public double getChangeRate(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Map<String, Object> param = new HashMap<>();
            param.put("stockNo", stockNo);
            Integer result = session.selectOne("stockGameMapper.getStockChangeRate", param);
            return result == null ? 0.0 : result;
        }
    }

    @Override
    public int getPervPrice(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Integer result = session.selectOne("stockGameMapper.getStockPrevPrice", stockNo);
            return result == null ? 0 : result;
        }
    }

    @Override
    public int getStudentPoint(String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return getStudentPoint(session, studentId);
        }
    }

    @Override
    public int getStudentStockAmount(int stockNo, String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Map<String, Object> param = new HashMap<>();
            param.put("stockNo", stockNo);
            param.put("studentId", studentId);
            Integer result = session.selectOne("stockGameMapper.getStudentStockAmount", param);
            return result == null ? 0 : result;
        }
    }

    @Override
    public boolean setStudentPointDown(int totalPrice, String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = setStudentPointDown(session, totalPrice, studentId);
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public boolean setStudentPointUp(int totalPrice, String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = setStudentPointUp(session, totalPrice, studentId);
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = setOrderRequest(session, content, price, amount, state, studentId, stockNo);
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public List<OrderVO> getTotalOrder(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            List<Map<String, Object>> list = session.selectList("stockGameMapper.getTotalOrderRequest", stockNo);
            return convertToOrderVOList(list);
        }
    }

    @Override
    public List<OrderVO> getTotalSellOrder(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            List<Map<String, Object>> list = session.selectList("stockGameMapper.getTotalSellOrderRequest", stockNo);
            return convertToOrderVOList(list);
        }
    }

    @Override
    public List<OrderVO> getTotalBuyOrder(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            List<Map<String, Object>> list = session.selectList("stockGameMapper.getTotalBuyOrderRequest", stockNo);
            return convertToOrderVOList(list);
        }
    }

    private List<OrderVO> convertToOrderVOList(List<Map<String, Object>> mapList) {
        List<OrderVO> list = new ArrayList<>();
        if (mapList != null) {
            for (Map<String, Object> map : mapList) {
                int price = map.get("PRICE") != null ? ((Number) map.get("PRICE")).intValue() : 0;
                int amount = map.get("AMOUNT") != null ? ((Number) map.get("AMOUNT")).intValue() : 0;
                String content = (String) map.get("CONTENT");
                list.add(new OrderVO(price, amount, content));
            }
        }
        return list;
    }

    @Override
    public List<OrderVO> getTotalMyOrder(int stockNo, String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Map<String, Object> param = new HashMap<>();
            param.put("stockNo", stockNo);
            param.put("studentId", studentId);
            List<Map<String, Object>> mapList = session.selectList("stockGameMapper.getMyOrderRequest", param);
            
            List<OrderVO> list = new ArrayList<>();
            if (mapList != null) {
                for (Map<String, Object> map : mapList) {
                    int orderNo = map.get("ORDER_NO") != null ? ((Number) map.get("ORDER_NO")).intValue() : 0;
                    String content = (String) map.get("CONTENT");
                    int price = map.get("PRICE") != null ? ((Number) map.get("PRICE")).intValue() : 0;
                    int amount = map.get("AMOUNT") != null ? ((Number) map.get("AMOUNT")).intValue() : 0;
                    String orderDate = map.get("ORDER_DATE") != null ? map.get("ORDER_DATE").toString() : null; // Date object to string approx
                    String state = (String) map.get("STATE");
                    list.add(new OrderVO(orderNo, content, price, amount, orderDate, state));
                }
            }
            return list;
        }
    }

    @Override
    public int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return getMyOrderNo(session, content, studentId, stockNo, state, amount, price);
        }
    }

    @Override
    public Map<String, Object> getStockPubInfo(int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return getStockPubInfo(session, stockNo);
        }
    }

    @Override
    public boolean setStockPubBalance(int buyAmount, int stockNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = setStockPubBalance(session, buyAmount, stockNo);
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = setMatchedOrder(session, buyOrderNo, sellOrderNo);
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public boolean setOrderStatePending(int orderNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = session.update("stockGameMapper.updateOrderStatePending", orderNo) > 0;
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public boolean setOrderStateMatched(int orderNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = setOrderStateMatched(session, orderNo);
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public boolean setOrderStateCancel(int orderNo) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            boolean result = session.update("stockGameMapper.updateOrderStateCancel", orderNo) > 0;
            if (result) session.commit();
            return result;
        }
    }

    @Override
    public Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId, String content) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return getMatchOrder(session, stockNo, orderPrice, orderAmount, studentId, content);
        }
    }
}
