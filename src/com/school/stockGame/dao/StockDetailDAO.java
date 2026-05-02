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
 * 주식 등락률 폭 +- 30% 제한 어떻게 둘지 고민 해봐야함 (미완료)
 * 	- 이전가격 불러와서 자바 스크립트로 이전가격 +-30% 값 못적게 UI상 처리 가능? 한지 확인 (미완료)
 * 주식 매칭 되고 체결 될때 보유주식량 차감 sql문 만들어야함 (완료)
 * 주식 매칭 되고 체결 될때 매도한 포인트 증가 sql문만 있음 기능은 없음 (완료)
 * 04.29 발행 잔량이 남아있다면 이라는 조건을 주기위해 발행 잔량 체크 하는 SQL 추가해야함 (완료)
 * 05.02 발행 잔량 다 팔리기 전엔 학생간 거래 막아야함(완료)
 */
public class StockDetailDAO {
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public StockDetailDAO(){
		try {
			conn = DBCP.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 닫아주는 기능
	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 매도 주문요청
	public String setSellOrder(String studentId, int sellPrice, int sellAmount, int stockNo){
		Connection conn = null;
		Map<String, Object> pubInfo = null;
		Map<String, Object> matchOrder = null;
		try {
			conn = DBCP.getConnection();
			conn.setAutoCommit(false);
			
			MyAssetDAO myAssetDAO = new MyAssetDAO();
			pubInfo = getStockPubInfo(conn, stockNo);
			
			//1. 발행 잔량 확인 있으면 학생간 거래x 매도 요청x return false;
			if(((Number) pubInfo.get("pubAmount")).intValue() > 0)
				return "발행 잔량이 남아 매도요청 할 수 없습니다.";
			//2. (보유한 주식 수량 < 매도요청 수량 )체크 return false;
			if(myAssetDAO.getStockAmount(conn, studentId, stockNo, "체결") < sellAmount)
				return "보유 주식량보다 많은 매도 요청은 할 수 없습니다."; 
			matchOrder = getMatchOrder(conn, stockNo, sellPrice, sellAmount, studentId, "매수");
			//3. 매수 주문 매칭 시도 (가격 수량 다맞는 조건)
			if(!matchOrder.isEmpty()){
				//4. 매칭O
				//4-1. 매수자 주문 '체결'로 업데이트 
				setOrderStateMatched(conn, ((Number) matchOrder.get("orderNo")).intValue());
				//4-2. 매도자 주문 '체결'로 insert
				setOrderRequest(conn, "매도", sellPrice, sellAmount, "체결", studentId, stockNo);
				//4-3. 매도요청한 주문 번호로 매도 완료 (매도 요청 주문번호 업무, 매칭 완료 업무 2개)
				setMatchedOrder(conn, ((Number) matchOrder.get("orderNo")).intValue(), getMyOrderNo(conn, "매도", studentId, stockNo, "체결", sellAmount, sellPrice));
				//4-4. 매도자 포인트 증가(매수자는 등록할때 포인트 감소)
				setStudentPointUp(conn, (sellPrice * sellAmount), studentId);
				//4-5. 커밋
				conn.commit();
				return "매도가 완료되었습니다.";
			}else {
				//5. 매칭X
				// 5-1. 주문 대기로 요청
				setOrderRequest(conn, "매도", sellPrice, sellAmount, "대기", studentId, stockNo);
				// 5-2. 커밋
				conn.commit();
				return "매도주문이 대기처리 되었습니다.";
			}
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
					conn.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "매도 불가";
	}
	
	// 매수 주문요청
	public String setBuyOrder(String studentId, int buyPrice, int buyAmount, int stockNo) {
		Connection conn = null;
		Map<String, Object> matchOrder = null;

		try {
			conn = DBCP.getConnection();
			conn.setAutoCommit(false);

			// 학생이 주문 요청한 가격보다 보유포인트가 적을때 실행
			if (getStudentPoint(conn, studentId) < (buyPrice * buyAmount)) {
				return "보유포인트가 부족합니다.";
			}
			Map<String, Object> pubInfo = getStockPubInfo(conn, stockNo);
			int pubAmount = ((Number) pubInfo.get("pubAmount")).intValue();
			int pubPrice = ((Number) pubInfo.get("pubPrice")).intValue();
			
			// 1. 발행 개수가 남았는지 체크 있으면 실행
			if (pubAmount >= buyAmount) {
				// 1-1. 발행 개수 차감
				setStockPubBalance(conn, buyAmount, stockNo);
				// 1-2. 주문 체결로 바로 요청
				setOrderRequest(conn, "매수", pubPrice, buyAmount, "체결", studentId, stockNo);
				// 1-3. 매수 요청한 주문번호로 주문 완료 등록
				setMatchedOrder(conn, getMyOrderNo(conn, "매수", studentId, stockNo, "체결", buyAmount, pubPrice),null);
				// 1-4. 보유 포인트 차감
				setStudentPointDown(conn, (pubPrice * buyAmount), studentId);
				// 1-5. 커밋
				conn.commit();
				return "발행 잔량 매수가 완료 되었습니다.";
			}
			// 발행 잔량이 남아있으면 학생간 거래x
			if (pubAmount > 0) {
				return "발행 잔량이 남아 매수요청 할 수 없습니다.";
			}

			matchOrder = getMatchOrder(conn, stockNo, buyPrice, buyAmount, studentId, "매도");
			// 2. 매수 요청에 따른 매도 요청이 있을경우 실행
			if (!matchOrder.isEmpty()) {
				// 2-1 매도 주문 '체결'로 업데이트
				setOrderStateMatched(conn, ((Number) matchOrder.get("orderNo")).intValue());
				// 2-2. 주문 체결로 바로 요청
				setOrderRequest(conn, "매수", buyPrice, buyAmount, "체결", studentId, stockNo);
				// 2-3. 매수 요청한 주문번호로 주문 완료 등록
				setMatchedOrder(conn, getMyOrderNo(conn, "매수", studentId, stockNo, "체결", buyAmount, buyPrice),((Number) matchOrder.get("orderNo")).intValue());
				// 2-4. 매수 학생 보유 포인트 차감
				setStudentPointDown(conn, (buyPrice * buyAmount), studentId);
				// 2-5. 매도 학생 보유 포인트 증가
				setStudentPointUp(conn, (buyPrice * buyAmount), (String) matchOrder.get("studentId"));
				// 2-6. 커밋
				conn.commit();
				return "매수가 완료되었습니다.";

				// 3. 발행 잔량 다 팔리고 학생간 거래 매칭도 없다면 실행
			} else {
				// 3-1. 주문 대기로 요청
				setOrderRequest(conn, "매수", buyPrice, buyAmount, "대기", studentId, stockNo);
				// 3-2. 매수 학생 보유 포인트 차감
				setStudentPointDown(conn, (buyPrice * buyAmount), studentId);
				// 3-3. 커밋
				conn.commit();
				return "매수주문이 대기처리 되었습니다.";
			}
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
					conn.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "매수 불가";
	}

	// 주식 기본정보 조회
	public Map<String, Object> getStockInfo(int stockNo) {
		Map<String, Object> tmp = new HashMap<>();
		try {
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

	// 주식 기본정보 조회 (트랜잭션 관리용)
	public Map<String, Object> getStockInfo(Connection conn, int stockNo) throws SQLException {
		Map<String, Object> tmp = new HashMap<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
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
			 if (rs != null) {
			        try { rs.close(); } catch (SQLException ignore) {}
			    }
			    if (stmt != null) {
			        try { stmt.close(); } catch (SQLException ignore) {}
			    }
		}

		return tmp;
	}

	// 주식 현재 가격 조회
	public int getStockPrice(int stockNo) {
		int price = 0;
		try {
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
	public int getStudentPoint(String studentId){
		int totalPoint = 0;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_POINT_SQL);
			stmt.setString(1, studentId);
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

	// 학생의 가용 보유 포인트 조회(트랜잭션 관리용)
	public int getStudentPoint(Connection conn, String studenId) throws SQLException {
		int totalPoint = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.TOTAL_POINT_SQL);
			stmt.setString(1, studenId);
			rs = stmt.executeQuery();
			if (rs.next())
				totalPoint = rs.getInt(1);
		} finally {
			 if (rs != null) {
			        try { rs.close(); } catch (SQLException ignore) {}
			    }
			    if (stmt != null) {
			        try { stmt.close(); } catch (SQLException ignore) {}
			    }
		}
		return totalPoint;
	}
	// 학생의 특정 주식 보유수량 조회
	public int getStudentStockAmount(int stockNo, String studenId){
		int amount = 0;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.STOCK_AMOUNT_SQL);
			stmt.setString(1, studenId);
			stmt.setInt(2, stockNo);
			rs = stmt.executeQuery();
			if(rs.next())
				amount = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return amount;
	} 

	// 학생의 보유포인트 차감
	public boolean setStudentPointDown(int totalPrice, String studentId) {
		boolean flag = false;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.POINT_DOWN_SQL);
			stmt.setInt(1, totalPrice);
			stmt.setString(2, studentId);
			
			if(stmt.executeUpdate() == 1)
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}

	// 학생의 보유포인트 차감 (트랜잭션 관리용)
	public boolean setStudentPointDown(Connection conn, int totalPrice, String studentId) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.POINT_DOWN_SQL);
			stmt.setInt(1, totalPrice);
			stmt.setString(2, studentId);

			if (stmt.executeUpdate() == 1)
				flag = true;
		} finally {
			 if (stmt != null) {
			        try { stmt.close(); } catch (SQLException ignore) {}
			    }
		}
		return flag;
	}

	// 학생의 보유포인트 증가
	public boolean setStudentPointUp(int totalPrice, String studentId) {
		boolean flag = false;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.POINT_UP_SQL);
			stmt.setInt(1, totalPrice);
			stmt.setString(2, studentId);
			
			if(stmt.executeUpdate() == 1)
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}
	
	// 학생의 보유포인트 증가 (트랜잭션 관리용)
	public boolean setStudentPointUp(Connection conn, int totalPrice, String studentId) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.POINT_UP_SQL);
			stmt.setInt(1, totalPrice);
			stmt.setString(2, studentId);

			if (stmt.executeUpdate() == 1)
				flag = true;
		} finally {
			 if (stmt != null) {
			        try { stmt.close(); } catch (SQLException ignore) {}
			    }
		}
		return flag;
	}

	// 매도, 매수 주문 요청
	public boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo) {
		boolean flag = false;
		try {			
			stmt = conn.prepareStatement(StockDetailQuery.ORDER_REQUEST);
			stmt.setString(1, content);
			stmt.setInt(2, price);
			stmt.setInt(3, amount);
			stmt.setString(4, state);
			stmt.setString(5, studentId);
			stmt.setInt(6, stockNo);
			
			if(stmt.executeUpdate() == 1)
				flag = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}
	
	// 매도, 매수 주문 요청 (트랜잭션 관리용)
	public boolean setOrderRequest(Connection conn, String content, int price, int amount, String state, String studentId, int stockNo) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.ORDER_REQUEST);
			stmt.setString(1, content);
			stmt.setInt(2, price);
			stmt.setInt(3, amount);
			stmt.setString(4, state);
			stmt.setString(5, studentId);
			stmt.setInt(6, stockNo);

			if (stmt.executeUpdate() == 1)
				flag = true;
		} finally {
			 if (stmt != null) {
			        try { stmt.close(); } catch (SQLException ignore) {}
			    }
		}
		return flag;
	}
	
	// 특정 주식 대기중인 매도 매수 주문 모두 조회
	public List<OrderVO> getTotalOrder(int stockNo) {
		List<OrderVO> list = new ArrayList<>();
		try {
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
	 
	// 내 주문 요청 조회 (트랜잭션 관리용)
	public List<OrderVO> getTotalMyOrder(Connection conn, int stockNo, String studentId) throws SQLException {
		List<OrderVO> list = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.MY_ORDER_REQUEST);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new OrderVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getString(6)));
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return list;
	}
	
	// 직전에 등록한 주문요청 no 조회
	public int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price) {
		int orderNo = 0;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.FIND_MY_ORDER_SQL);
			stmt.setString(1, content);
			stmt.setString(2, studentId);
			stmt.setInt(3, stockNo);
			stmt.setString(4, state);
			stmt.setInt(5, amount);
			stmt.setInt(6, price);
			rs = stmt.executeQuery();
			
			if(rs.next())
				orderNo = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return orderNo;
	}
	
	// 직전에 등록한 주문요청 no 조회 (트랜잭션 관리용)
	public int getMyOrderNo(Connection conn, String content, String studentId, int stockNo, String state, int amount, int price) throws SQLException {
		int orderNo = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.FIND_MY_ORDER_SQL);
			stmt.setString(1, content);
			stmt.setString(2, studentId);
			stmt.setInt(3, stockNo);
			stmt.setString(4, state);
			stmt.setInt(5, amount);
			stmt.setInt(6, price);
			rs = stmt.executeQuery();

			if (rs.next())
				orderNo = rs.getInt(1);
		} finally {
		    if (rs != null) {
		        try { rs.close(); } catch (SQLException ignore) {}
		    }
		    if (stmt != null) {
		        try { stmt.close(); } catch (SQLException ignore) {}
		    }
		}
		return orderNo;
	}

	// 내 주문 요청 취소(트랜잭션 관리용)
	public boolean myOrderCancel(Connection conn, int orderNo) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.MY_ORDER_CANCEL);
			stmt.setInt(1, orderNo);
			flag = (stmt.executeUpdate() == 1);

		}finally{
		    if (stmt != null) {
		        try { stmt.close(); } catch (SQLException ignore) {}
		    }
		}
		return flag;
	}

	// 주식 발행 정보 조회
	public Map<String, Object> getStockPubInfo(int stockNo){
		Map<String, Object> map = new HashMap<>();
		try {
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
	
	// 주식 발행 정보 조회(트랜잭션 관리용)
	public Map<String, Object> getStockPubInfo(Connection conn, int stockNo) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.PUBLICATION_DATA_SELECT_SQL);
			stmt.setInt(1, stockNo);

			rs = stmt.executeQuery();
			if (rs.next()) {
				map.put("pubAmount", rs.getInt(1));
				map.put("pubPrice", rs.getInt(2));
			}
		} finally {
		    if (rs != null) {
		        try { rs.close(); } catch (SQLException ignore) {}
		    }
		    if (stmt != null) {
		        try { stmt.close(); } catch (SQLException ignore) {}
		    }
		}
		return map;
	}

	// 주식 발행 개수 차감 
	public boolean setStockPubBalance(int buyAmount, int stockNo) {
		boolean flag = false;
		try {
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
	
	// 주식 발행 개수 차감 (트랜잭션 관리용)
	public boolean setStockPubBalance(Connection conn, int buyAmount, int stockNo) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.PUBLICATION_DATA_UPDATE_SQL);
			stmt.setInt(1, buyAmount);
			stmt.setInt(2, stockNo);
			if (stmt.executeUpdate() == 1)
				flag = true;
		} finally {
			if (stmt != null) {
		        try { stmt.close(); } catch (SQLException ignore) {}
		    }
		}
		return flag;
	}

	// 주문 요청 완료
	public boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo){
		boolean flag = false;
		try {
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

	// 주문 요청 완료 (트랜잭션 관리용)
	public boolean setMatchedOrder(Connection conn, int buyOrderNo, Integer sellOrderNo) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.MATCH_COMPLETE_INSERT_SQL);
			stmt.setInt(1, buyOrderNo);
			if(sellOrderNo != null){
				stmt.setInt(2, sellOrderNo);
			}else{
				stmt.setNull(2, java.sql.Types.INTEGER);
			}
			
			if (stmt.executeUpdate() == 1)
				flag = true;
		} finally {
			if (stmt != null) {
		        try { stmt.close(); } catch (SQLException ignore) {}
		    }
		}
		return flag;
	}
	
	// 주문 요청 상태 '대기'변경
	public boolean setOrderStatePending(int orderNo){
		boolean flag = false;
		try {
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
	
	// 주문 요청 상태 '체결'변경 (트랜잭션 관리용)
	public boolean setOrderStateMatched(Connection conn, int orderNo) throws SQLException {
		boolean flag = false;
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(StockDetailQuery.ORDER_STATE_MATCHED_UPDATE_SQL);
			stmt.setInt(1, orderNo);
			if (stmt.executeUpdate() == 1)
				flag = true;
		} finally {
			if (stmt != null) {
		        try { stmt.close(); } catch (SQLException ignore) {}
		    }
		}
		return flag;
	}

	// 주문 요청 상태 '취소'변경
	public boolean setOrderStateCancel(int orderNo){
		boolean flag = false;
		try {
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
	public Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId, String content){
		Map<String, Object> map = new HashMap<>();
		try {
			stmt = conn.prepareStatement(StockDetailQuery.MATCH_ORDER_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, content);
			stmt.setInt(3, orderPrice);
			stmt.setInt(4, orderAmount);
			stmt.setString(5, studentId);
			rs = stmt.executeQuery();
			if(rs.next()){
				map.put("orderNo", rs.getInt(1));
				map.put("content", rs.getString(2));
				map.put("price", rs.getInt(3));
				map.put("amount",rs.getInt(4));
				map.put("state", rs.getString(5));
				map.put("orderDate", rs.getString(6));
				map.put("studentId", rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return map;
	}
	
	// 매수 주문 요청 매칭 (트랜잭션 관리용)
	public Map<String, Object> getMatchOrder(Connection conn, int stockNo, int orderPrice, int orderAmount, String studentId, String content)throws SQLException {
		Map<String, Object> map = new HashMap<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(StockDetailQuery.MATCH_ORDER_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, content);
			stmt.setInt(3, orderPrice);
			stmt.setInt(4, orderAmount);
			stmt.setString(5, studentId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				map.put("orderNo", rs.getInt(1));
				map.put("content", rs.getString(2));
				map.put("price", rs.getInt(3));
				map.put("amount",rs.getInt(4));
				map.put("state", rs.getString(5));
				map.put("orderDate", rs.getString(6));
				map.put("studentId", rs.getString(7));
			}
		} finally {
			if (rs != null) {
	            try { rs.close(); } catch (SQLException ignore) {}
	        }
	        if (stmt != null) {
	            try { stmt.close(); } catch (SQLException ignore) {}
	        }
		}
		return map;
	}
}
