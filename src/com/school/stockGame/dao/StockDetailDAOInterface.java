package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.school.stockGame.vo.OrderVO;

public interface StockDetailDAOInterface {
	// 매도 주문요청
	public String setSellOrder(String studentId, int sellPrice, int sellAmount, int stockNo);
	// 매수 주문요청
	public String setBuyOrder(String studentId, int buyPrice, int buyAmount, int stockNo);
	// 주식 기본정보 조회
	public Map<String, Object> getStockInfo(int stockNo);
	// 주식 기본정보 조회 (트랜잭션 관리용)
	public Map<String, Object> getStockInfo(Connection conn, int stockNo) throws SQLException;
	// 주식 현재 가격 조회
	public int getStockPrice(int stockNo);
	// 주식 이전 가격 대비 조회
	public int getStockPriceChange(int stockNo);
	// 주식 등락률 조회
	public double getChangeRate(int StockNo);
	// 주식 이전가격 조회
	public int getPervPrice(int stockNo);
	// 학생의 가용 보유 포인트 조회
	public int getStudentPoint(String studentId);
	// 학생의 가용 보유 포인트 조회(트랜잭션 관리용)
	public int getStudentPoint(Connection conn, String studenId) throws SQLException;
	// 학생의 특정 주식 보유수량 조회
	public int getStudentStockAmount(int stockNo, String studenId);
	// 학생의 보유포인트 차감
	public boolean setStudentPointDown(int totalPrice, String studentId);
	// 학생의 보유포인트 차감 (트랜잭션 관리용)
	public boolean setStudentPointDown(Connection conn, int totalPrice, String studentId) throws SQLException;
	// 학생의 보유포인트 증가
	public boolean setStudentPointUp(int totalPrice, String studentId);
	// 학생의 보유포인트 증가 (트랜잭션 관리용)
	public boolean setStudentPointUp(Connection conn, int totalPrice, String studentId) throws SQLException;
	// 매도, 매수 주문 요청
	public boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo);
	// 매도, 매수 주문 요청 (트랜잭션 관리용)
	public boolean setOrderRequest(Connection conn, String content, int price, int amount, String state, String studentId, int stockNo) throws SQLException;
	// 특정 주식 대기중인 매도 매수 주문 모두 조회
	public List<OrderVO> getTotalOrder(int stockNo);
	// 특정 주식 대기중인 매도 주문 모두 조회
	public List<OrderVO> getTotalSellOrder(int stockNo);
	// 특정 주식 대기중인 매수 주문 모두 조회
	public List<OrderVO> getTotalBuyOrder(int stockNo);
	// 내 주문 요청 조회
	public List<OrderVO> getTotalMyOrder(int stockNo, String studentId);
	// 내 주문 요청 조회 (트랜잭션 관리용)
	public List<OrderVO> getTotalMyOrder(Connection conn, int stockNo, String studentId) throws SQLException;
	// 직전에 등록한 주문요청 no 조회
	public int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price);
	// 직전에 등록한 주문요청 no 조회 (트랜잭션 관리용)
	public int getMyOrderNo(Connection conn, String content, String studentId, int stockNo, String state, int amount, int price) throws SQLException;
	// 내 주문 요청 취소(트랜잭션 관리용)
	public boolean myOrderCancel(Connection conn, int orderNo) throws SQLException;
	// 주식 발행 정보 조회
	public Map<String, Object> getStockPubInfo(int stockNo);
	// 주식 발행 정보 조회(트랜잭션 관리용)
	public Map<String, Object> getStockPubInfo(Connection conn, int stockNo) throws SQLException;
	// 주식 발행 개수 차감 
	public boolean setStockPubBalance(int buyAmount, int stockNo);
	// 주식 발행 개수 차감 (트랜잭션 관리용)
	public boolean setStockPubBalance(Connection conn, int buyAmount, int stockNo) throws SQLException;
	// 주문 요청 완료
	public boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo);
	// 주문 요청 완료 (트랜잭션 관리용)
	public boolean setMatchedOrder(Connection conn, int buyOrderNo, Integer sellOrderNo) throws SQLException;
	// 주문 요청 상태 '대기'변경
	public boolean setOrderStatePending(int orderNo);
	// 주문 요청 상태 '체결'변경
	public boolean setOrderStateMatched(int orderNo);
	// 주문 요청 상태 '체결'변경 (트랜잭션 관리용)
	public boolean setOrderStateMatched(Connection conn, int orderNo) throws SQLException;
	// 주문 요청 상태 '취소'변경
	public boolean setOrderStateCancel(int orderNo);
	// 매수 주문 요청 매칭
	public Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId, String content);
	// 매수 주문 요청 매칭 (트랜잭션 관리용)
	public Map<String, Object> getMatchOrder(Connection conn, int stockNo, int orderPrice, int orderAmount, String studentId, String content)throws SQLException;
}
