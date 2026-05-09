package com.school.stockGame.dao;

import java.util.List;
import java.util.Map;
import com.school.stockGame.vo.OrderVO;

public interface StockDetailDAOInterface {
    String setSellOrder(String studentId, int sellPrice, int sellAmount, int stockNo);
    String setBuyOrder(String studentId, int buyPrice, int buyAmount, int stockNo);
    Map<String, Object> getStockInfo(int stockNo);
    int getStockPrice(int stockNo);
    int getStockPriceChange(int stockNo);
    double getChangeRate(int stockNo);
    int getPervPrice(int stockNo);
    int getStudentPoint(String studentId);
    int getStudentStockAmount(int stockNo, String studentId);
    boolean setStudentPointDown(int totalPrice, String studentId);
    boolean setStudentPointUp(int totalPrice, String studentId);
    boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo);
    List<OrderVO> getTotalOrder(int stockNo);
    List<OrderVO> getTotalSellOrder(int stockNo);
    List<OrderVO> getTotalBuyOrder(int stockNo);
    List<OrderVO> getTotalMyOrder(int stockNo, String studentId);
    int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price);
    Map<String, Object> getStockPubInfo(int stockNo);
    boolean setStockPubBalance(int buyAmount, int stockNo);
    boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo);
    boolean setOrderStatePending(int orderNo);
    boolean setOrderStateMatched(int orderNo);
    boolean setOrderStateCancel(int orderNo);
    Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId, String content);
}