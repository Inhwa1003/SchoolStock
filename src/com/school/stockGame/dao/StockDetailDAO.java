package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.school.stockGame.query.StockDetailQuery;
import com.school.stockGame.vo.OrderVO;

/**
 * @author 최동석 
 * 주식 등락률 폭 +- 30% 제한 어떻게 둘지 고민 해봐야함
 * 	- 이전가격 불러와서 자바 스크립트로 이전가격 +-30% 값 못적게 UI상 처리 가능? 한지 확인
 * 주식 매칭 되고 체결 될때 보유주식량 차감 sql문 만들어야함
 * 주식 매칭 되고 체결 될때 매도한 포인트 증가 sql문만 있음 기능은 없음
 * 04.29 발행 잔량이 남아있다면 이라는 조건을 주기위해 발행 잔량 체크 하는 SQL 추가해야함 
 */
public class StockDetailDAO {
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public StockDetailDAO(){}
	// 트랜잭션 관리 때문에 필요
	public StockDetailDAO(Connection conn){
		this.conn = conn;
	}
	public Connection getConnection(){
		return conn;
	}
	
	// 닫아주는 기능
	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 주식 기본정보 조회
	public Map<String, Object> getStockInfo(int stockNo) {
		Map<String, Object> tmp = new HashMap<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_INFO_SQL);
			stmt.setInt(1, stockNo);
			rs = stmt.executeQuery();

			if (rs.next()) {
				tmp.put("name", rs.getString(1));
				tmp.put("content", rs.getString(2));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return tmp;
	}

	// 주식 현재 가격 조회
	public int getStockPrice(int stockNo) {
		int price = 0;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_PRICE_SQL);
			stmt.setInt(1, stockNo);
			rs = stmt.executeQuery();

			if (rs.next())
				price = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return price;
	}

	// 주식 이전 가격 대비 조회
	public int getStockPriceChange(int stockNo) {
		int price = 0;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_PRICE_CHANGE_SQL);
			stmt.setInt(1, stockNo);
			stmt.setInt(2, stockNo);
			rs = stmt.executeQuery();

			if (rs.next())
				price = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return price;
	}

	// 주식 등락률 조회
	public int getChangeRate(int StockNo) {
		int percent = 0;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_CHANGE_RATE_SQL);
			stmt.setInt(1, StockNo);
			stmt.setInt(2, StockNo);
			rs = stmt.executeQuery();

			if (rs.next())
				percent = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return percent;
	}

	// 주식 이전가격 조회
	public int getPervPrice(int stockNo) {
		int prevPrice = 0;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_PREV_PRICE_SQL);
			stmt.setInt(1, stockNo);
			rs = stmt.executeQuery();
			if (rs.next())
				prevPrice = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return prevPrice;
	}
	
	// 학생의 가용 보유 포인트 조회
	public int getStudentPoint(String studenId){
		int totalPoint = 0;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_POINT_SQL);
			stmt.setString(1, studenId);
			rs = stmt.executeQuery();
			if(rs.next())
				totalPoint = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return totalPoint;
	}

	// 매도 기능
	public boolean setSellOrderxxx(int stockNo, String studentId, int sellQty, int sellPrice) {
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_QTY_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			rs = stmt.executeQuery();
			if (rs.next()) {
				// 보유수량 주문수량 비교 UI에서 할지 고민하기
				// 주문한 수량이 보유 수량보다 작거나 같다면 실행
				if (sellQty <= rs.getInt(1)) {
					// 매도 주문
					stmt = conn.prepareStatement(StockDetailQuery.ORDER_REQUEST);
					stmt.setString(1, "매도");
					stmt.setInt(2, sellPrice);
					stmt.setInt(3, sellQty);
					stmt.setString(4, studentId);
					stmt.setInt(5, stockNo);
					flag = (stmt.executeUpdate() == 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}
	public boolean setBuyOrder(){
		boolean flag = false;
		
		
		return flag;
	}

	// 매수 기능
	public boolean setBuyOrderxxx(int stockNo, String studentId, int buyQty, int buyPrice) {
		boolean flag = false;
		try {
			conn = DBCP.getConnection();

			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_POINT_SQL);
			stmt.setString(1, studentId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				// 보유 포인트가 사기로 한 포인트보다 여유가 있으면 실행
				if (rs.getInt(1) >= (buyQty * buyPrice)) {
					stmt = conn.prepareStatement(StockDetailQuery.ORDER_REQUEST);
					stmt.setString(1, "매수");
					stmt.setInt(2, buyPrice);
					stmt.setInt(3, buyQty);
					stmt.setString(4, studentId);
					stmt.setInt(5, stockNo);
					// 주문 완료 되면 포인트 차감
					if (stmt.executeUpdate() == 1) {
						stmt = conn.prepareStatement(StockDetailQuery.POINT_DOWN_SQL);
						stmt.setInt(1, buyQty * buyPrice);
						stmt.setString(2, studentId);
						flag = (stmt.executeUpdate() == 1);
					}
				}
			}
			// 모든 쿼리문 실행 되면 커밋 아니면 롤백
			if (flag) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	// 특정 주식 대기중인 매도 매수 주문 모두 조회
	public List<OrderVO> getTotalOrder(int stockNo) {
		List<OrderVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_ORDER_REQUEST);
			stmt.setInt(1, stockNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 특정 주식 대기중인 매도 주문 모두 조회
	public List<OrderVO> getTotalSellOrder(int stockNo) {
		List<OrderVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_SELL_ORDER_REQUEST);
			stmt.setInt(1, stockNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 특정 주식 대기중인 매수 주문 모두 조회
	public List<OrderVO> getTotalBuyOrder(int stockNo) {
		List<OrderVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_BUY_ORDER_REQUEST);
			stmt.setInt(1, stockNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	// 내 주문 요청 조회
	public List<OrderVO> getTotalMyOrder(int stockNo, String studentId) {
		List<OrderVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.MY_ORDER_REQUEST);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	// 내 주문 요청 취소
	public boolean myOrderCancel(int orderNo){
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.MY_ORDER_CANCEL);
			stmt.setInt(1, orderNo);
			flag = (stmt.executeUpdate() == 1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}
	
	// 주식 발행 정보 조회
	public Map<String, Object> getStockPubInfo(int stockNo){
		Map<String, Object> map = new HashMap<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.PUBLICATION_DATA_SELECT_SQL);
			stmt.setInt(1, stockNo);
			
			rs =  stmt.executeQuery();
			if(rs.next()){
				map.put("pubAmount", rs.getInt(1));
				map.put("pubPrice", rs.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return map;		
	}
	
	// 주식 발행 개수 차감 
	public boolean setStockPubBalance(int buyAmount, int stockNo) {
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.PUBLICATION_DATA_UPDATE_SQL);
			stmt.setInt(1, buyAmount);
			stmt.setInt(2, stockNo);
			if (stmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	// 주문 요청 완료
	public boolean setMatchedOrder(int buyOrderNo, int sellOrderNo){
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.MATCH_COMPLETE_INSERT_SQL);
			stmt.setInt(1, buyOrderNo);
			stmt.setInt(2, sellOrderNo);
			if(stmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		return flag;
	}
	
	// 주문 요청 상태 '대기'변경
	public boolean setOrderStatePending(int orderNo){
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.ORDER_STATE_PENDING_UPDATE_SQL);
			stmt.setInt(1, orderNo);
			if(stmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		return flag;
	}
	
	// 주문 요청 상태 '체결'변경
	public boolean setOrderStateMatched(int orderNo){
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.ORDER_STATE_MATCHED_UPDATE_SQL);
			stmt.setInt(1, orderNo);
			if(stmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		return flag;
	}
	
	// 주문 요청 상태 '취소'변경
	public boolean setOrderStateCancel(int orderNo){
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.ORDER_STATE_CANCEL_UPDATE_SQL);
			stmt.setInt(1, orderNo);
			if(stmt.executeUpdate() == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		return flag;
	}
	// 매수 주문 요청 매칭
	public Map<String, Object> getMatchBuyOrder(int stockNo, int buyPrice, int buyAmount){
		Map<String, Object> map = new HashMap<>();
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(StockDetailQuery.MATCH_BUY_ORDER_SQL);
			stmt.setInt(1, stockNo);
			stmt.setInt(2, buyPrice);
			stmt.setInt(3, buyAmount);
			rs = stmt.executeQuery();
			if(rs.next()){
				map.put("orderNo", rs.getInt(1));
				map.put("content", rs.getString(2));
				map.put("price", rs.getInt(3));
				map.put("state", rs.getString(4));
				map.put("orderDate", rs.getString(5));
				map.put("studentId", rs.getInt(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return map;
	}
}
