package com.school.stockGame.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.StockDetailDAOInterface;
import com.school.stockGame.vo.OrderVO;
import com.school.stockGame.vo.TransactionVO;

public class StockDetailDAOMybatis implements StockDetailDAOInterface {

	@Override
	public String setSellOrder(String studentId, int sellPrice, int sellAmount, int stockNo) {
		SqlSession session = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();

			//발행 잔량 확인 있으면 학생간 거래x 매도 요청x
			Map<String, Object> publicationInfo = session.selectOne("stockDetailMapper.getStockPubInfo", stockNo);
			if (publicationInfo != null && ((Number) publicationInfo.get("PUBLICATION_BALANCE")).intValue() > 0) {
				return "발행 잔량이 남아 매도요청 할 수 없습니다.";
			};
			//(보유한 주식 수량 < 매도요청 수량 )체크
			Map<String, Object> amountParams = new HashMap<String, Object>();
			amountParams.put("studentId", studentId);
			amountParams.put("stockNo", stockNo);
			Integer currentAmount = session.selectOne("stockDetailMapper.getStudentStockAmount", amountParams);
			if (currentAmount == null || currentAmount < sellAmount) {
				return "보유 주식량보다 많은 매도 요청은 할 수 없습니다.";
			}
			//매수 주문 매칭 시도
			Map<String, Object> matchParams = new HashMap<String, Object>();
			matchParams.put("stockNo", stockNo);
			matchParams.put("content", "매수");
			matchParams.put("price", sellPrice);
			matchParams.put("amount", sellAmount);
			matchParams.put("studentId", studentId);
			Map<String, Object> matchOrder = session.selectOne("stockDetailMapper.getMatchOrder", matchParams);

			if (matchOrder != null && !matchOrder.isEmpty()) {
				//매칭O
				//매수자 주문 '체결'로 업데이트 
				Integer buyOrderNo =((Number) matchOrder.get("ORDER_NO")).intValue();

				session.update("stockDetailMapper.setOrderStateMatched", buyOrderNo);
				//매도자 주문 '체결'로 insert
				Map<String, Object> sellOrder = new HashMap<String, Object>();
				sellOrder.put("content", "매도");
				sellOrder.put("price", sellPrice);
				sellOrder.put("amount", sellAmount);
				sellOrder.put("state", "체결");
				sellOrder.put("studentId", studentId);
				sellOrder.put("stockNo", stockNo);
				session.insert("stockDetailMapper.setOrderRequest", sellOrder);

				//매도요청한 주문 번호로 매도 완료
				Integer sellOrderNo = session.selectOne("stockDetailMapper.getMyOrderNo", sellOrder);

				TransactionVO transactionVO = new TransactionVO();
				transactionVO.setBuyOrderNo(buyOrderNo);
				transactionVO.setSellOrderNo(sellOrderNo);
				session.insert("stockDetailMapper.setMatchedOrder", transactionVO);

				//매도자 포인트 증가
				OrderVO pointUp = new OrderVO();
				pointUp.setStudentId(studentId);
				pointUp.setTotalPoint(sellPrice * sellAmount);
				session.update("stockDetailMapper.setStudentPointUp", pointUp);

				//커밋
				session.commit();
				return "매도가 완료되었습니다.";
			} else {
				//매칭X
				//주문 대기로 요청
				Map<String, Object> sellOrder = new HashMap<String, Object>();
				sellOrder.put("content", "매도");
				sellOrder.put("price", sellPrice);
				sellOrder.put("amount", sellAmount);
				sellOrder.put("state", "대기");
				sellOrder.put("studentId", studentId);
				sellOrder.put("stockNo", stockNo);
				session.insert("stockDetailMapper.setOrderRequest", sellOrder);

				//커밋
				session.commit();
				return "매도주문이 대기처리 되었습니다.";
			}
		} catch (Exception e) {
			if (session != null) session.rollback();
			return "매도 요청 중 오류가 발생했습니다";
		} finally {
			if (session != null) session.close();
		}
	}

	@Override
	public String setBuyOrder(String studentId, int buyPrice, int buyAmount, int stockNo) {
		SqlSession session = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();

			//학생이 주문 요청한 가격보다 보유포인트가 적을때 실행
			Integer point = session.selectOne("stockDetailMapper.getStudentPoint", studentId);
			if (point == null || point < (buyPrice * buyAmount)) {
				return "보유포인트가 부족합니다.";
			}

			Map<String, Object> publicationInfo = session.selectOne("stockDetailMapper.getStudentPoint", stockNo);
			Integer pubAmount = ((Number) publicationInfo.get("PUBLICATION_BALANCE")).intValue();	//publicatonBalance
			Integer pubPrice = ((Number) publicationInfo.get("PUBLICATION_PRICE")).intValue();	//publicatonPrice

			//발행 잔량 확인
			if (pubAmount > 0) {
				//입력한 값이 발행가격과 같거나 높을때 실행
				if (pubAmount >= buyAmount && pubPrice <= buyPrice) {
					Map<String, Object> buyOrder = new HashMap<String, Object>();
					buyOrder.put("content", "매수");
					buyOrder.put("price", buyPrice);
					buyOrder.put("amount", buyAmount);
					buyOrder.put("state", "체결");
					buyOrder.put("studentId", studentId);
					buyOrder.put("stockNo", stockNo);
					session.insert("stockDetailMapper.setOrderRequest", buyOrder);

					Integer buyOrderNo = session.selectOne("stockDetailMapper.getMyOrderNo", buyOrder);

					TransactionVO transactionVO = new TransactionVO();
					transactionVO.setBuyOrderNo(buyOrderNo);
					transactionVO.setSellOrderNo(0);
					session.insert("stockDetailMapper.setMatchedOrder", transactionVO);

					session.update("stockDetailMapper.setStockPubBalance", buyOrder);
					session.update("stockDetailMapper.setStudentPointDown", buyOrder);

					session.commit();
					return "발행 가격 " + pubPrice + "P 매수가 완료 되었습니다. 남은 발행잔량은 " + (pubAmount - buyAmount) + "주 입니다.";
				} else if (pubAmount < buyAmount) {
					// 발행 잔량보다 높은 수량을 적을시
					return "남은 발행잔량은 " + pubAmount + "주, 발행가격은" + pubPrice + "P 입니다. " + pubAmount + "주 이하로만 매수 가능합니다.";
				} else {
					return "발행 잔량이 남아 매수요청 할 수 없습니다.";
				}
			}
			
			//매수 요청에 따른 매도 요청이 있을경우 실행
			Map<String, Object> matchParams = new HashMap<String, Object>();
			matchParams.put("stockNo", stockNo);
			matchParams.put("content", "매도");
			matchParams.put("price", buyPrice);
			matchParams.put("amount", buyAmount);
			matchParams.put("studentId", studentId);
			Map<String, Object> matchOrder = session.selectOne("stockDetailMapper.getMatchOrder", matchParams);
			
			if (matchOrder != null && !matchOrder.isEmpty()) {
				Integer sellOrderNo = ((Number) matchOrder.get("ORDER_NO")).intValue();
				String sellerId = ((String) matchOrder.get("STUDENT_ID"));
				
				session.update("stockDetailMapper.setOrderStateMatched", sellOrderNo);
				
				Map<String, Object> buyRequest = new HashMap<String, Object>();
				buyRequest.put("content", "매수");
				buyRequest.put("price", buyPrice);
				buyRequest.put("amount", buyAmount);
				buyRequest.put("state", "체결");
				buyRequest.put("studentId", studentId);
				buyRequest.put("stockNo", stockNo);
				session.insert("stockDetailMapper.setOrderRequest", buyRequest);
				
				Integer buyOrderNo = session.selectOne("stockDetailMapper.getMyOrderNo", buyRequest);
				
				TransactionVO transactionVO = new TransactionVO();
				transactionVO.setBuyOrderNo(buyOrderNo);
				transactionVO.setSellOrderNo(sellOrderNo);
				session.insert("stockDetailMapper.setMatchedOrder", transactionVO);
				
				session.update("stockDetailMapper.setStudentPointDown", buyRequest);
				
				OrderVO pointUp = new OrderVO();
				pointUp.setStudentId(sellerId);
				pointUp.setTotalPoint(buyPrice * buyAmount);
				session.update("stockDetailMapper.setStudentPointUp", pointUp);
				
				session.commit();
				return "매수가 완료되었습니다.";
			} else {
				//발행 잔량 다 팔리고 학생간 거래 매칭도 없다면 실행
				Map<String, Object> buyRequest = new HashMap<String, Object>();
				buyRequest.put("content", "매수");
				buyRequest.put("price", buyPrice);
				buyRequest.put("amount", buyAmount);
				buyRequest.put("state", "대기");
				buyRequest.put("studentId", studentId);
				buyRequest.put("stockNo", stockNo);
				session.insert("stockDetailMapper.setOrderRequest", buyRequest);
				
				session.update("stockDetailMapper.setStudentPointDown", buyRequest);
				
				session.commit();
				return "매수주문이 대기처리 되었습니다.";
			}
		} catch (Exception e) {
			if (session != null) session.rollback();
			return "매도 요청 중 오류가 발생했습니다";
		} finally {
			if (session != null) session.close();
		}
	}

	// 주식 기본정보 조회
	@Override
	public Map<String, Object> getStockInfo(int stockNo) {
		SqlSession session = null;
		Map<String, Object> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectOne("stockDetailMapper.getStockInfo", stockNo);
		} finally {
			if (session != null) session.close(); 
		}
		return result;
	}

	// 주식 현재 가격 조회
	@Override
	public int getStockPrice(int stockNo) {
		SqlSession session = null;
		int result = 0;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectOne("stockDetailMapper.getStockPrice", stockNo);
		} finally {
			if (session != null) session.close();
		}
		return result;
	}

	// 주식 이전 가격 대비 조회
	@Override
	public int getStockPriceChange(int stockNo) {
		SqlSession session = null;
		int result = 0;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectOne("stockDetailMapper.getStockPriceChange", stockNo);
		} finally {
			if (session != null) session.close();
		}
		return result;
	}

	// 주식 등락률 조회
	@Override
	public double getChangeRate(int stockNo) {
		SqlSession session = null;
		double result = 0;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectOne("stockDetailMapper.getChangeRate", stockNo);
		} finally {
			if (session != null) session.close();
		}
		return result;
	}

	// 주식 이전가격 조회
	@Override
	public int getPervPrice(int stockNo) {
		SqlSession session = null;
		int result = 0;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectOne("stockDetailMapper.getPervPrice", stockNo);
		} finally {
			if (session != null) session.close();
		}
		return result;
	}

	// 학생의 가용 보유 포인트 조회
	@Override
	public int getStudentPoint(String studentId) {
		SqlSession session = null;
		int result = 0;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectOne("stockDetailMapper.getStudentPoint", studentId);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 학생의 특정 주식 보유수량 조회
	@Override
	public int getStudentStockAmount(int stockNo, String studenId) {
		SqlSession session = null;
		int result = 0;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("stockNo", stockNo);
			map.put("studentId", studenId);
			result = session.selectOne("stockDetailMapper.getStudentStockAmount", map);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 학생의 보유포인트 차감
	@Override
	public boolean setStudentPointDown(int totalPrice, String studentId) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			OrderVO orderVo = new OrderVO();
			orderVo.setStudentId(studentId);
			orderVo.setTotalPoint(totalPrice);
			int savedMap = session.selectOne("stockDetailMapper.setStudentPointDown", orderVo);
			if(savedMap > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 학생의 보유포인트 증가
	@Override
	public boolean setStudentPointUp(int totalPrice, String studentId) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			OrderVO orderVo = new OrderVO();
			orderVo.setStudentId(studentId);
			orderVo.setTotalPoint(totalPrice);
			int savedMap = session.selectOne("stockDetailMapper.setStudentPointUp", orderVo);
			if(savedMap > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 매도, 매수 주문 요청
	@Override
	public boolean setOrderRequest(String content, int price, int amount, String state, String studentId, int stockNo) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", content);
			map.put("price", price);
			map.put("amount", amount);
			map.put("state", state);
			map.put("studentId", studentId);
			map.put("stockNo", stockNo);
			int savedMap = session.insert("stockDetailMapper.setOrderRequest", map);
			if(savedMap > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 특정 주식의 대기중인 주문 모두 조회
	@Override
	public List<OrderVO> getTotalOrder(int stockNo) {
		SqlSession session = null;
		List<OrderVO> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectList("stockDetailMapper.getTotalOrder", stockNo);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 특정 주식의 대기중인 매도 주문 모두 조회
	@Override
	public List<OrderVO> getTotalSellOrder(int stockNo) {
		SqlSession session = null;
		List<OrderVO> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectList("stockDetailMapper.getTotalSellOrder", stockNo);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 특정 주식의 대기중인 매수 주문 모두 조회
	@Override
	public List<OrderVO> getTotalBuyOrder(int stockNo) {
		SqlSession session = null;
		List<OrderVO> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectList("stockDetailMapper.getTotalBuyOrder", stockNo);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 내 주문 요청 조회
	@Override
	public List<OrderVO> getTotalMyOrder(int stockNo, String studentId) {
		SqlSession session = null;
		List<OrderVO> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("stockNo", stockNo);
			map.put("studentId", studentId);
			result = session.selectList("stockDetailMapper.getTotalMyOrder", map);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 직전에 등록한 주문요청번호 조회
	@Override
	public int getMyOrderNo(String content, String studentId, int stockNo, String state, int amount, int price) {
		SqlSession session = null;
		int result = 0; //orderNo
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", content);
			map.put("studentId", studentId);
			map.put("stockNo", stockNo);
			map.put("state", state);
			map.put("amount", amount);
			map.put("price", price);
			result = session.selectOne("stockDetailMapper.getMyOrderNo", map);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 주식 발행 정보 조회
	@Override
	public Map<String, Object> getStockPubInfo(int stockNo) {
		SqlSession session = null;
		Map<String, Object> map = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			map = session.selectOne("stockDetailMapper.getStockPubInfo", stockNo);
		} finally {
			if(session != null) session.close();
		}
		return map;
	}

	// 주식 발행 개수 변경
	@Override
	public boolean setStockPubBalance(int buyAmount, int stockNo) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("amount", buyAmount);
			map.put("stockNo", stockNo);
			int savedMap = session.update("stockDetailMapper.setStockPubBalance", stockNo);
			if(savedMap > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 주문 요청 완료
	@Override
	public boolean setMatchedOrder(int buyOrderNo, Integer sellOrderNo) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			TransactionVO transactionVo = new TransactionVO();
			transactionVo.setBuyOrderNo(buyOrderNo);
			transactionVo.setSellOrderNo(sellOrderNo);
			int savedOrderNo = session.insert("stockDetailMapper.setMatchedOrder", transactionVo);
			if(savedOrderNo > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 주문 요청 상태 '대기'변경
	@Override
	public boolean setOrderStatePending(int orderNo) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			int savedOrderNo = session.update("stockDetailMapper.setOrderStatePending", orderNo);
			if(savedOrderNo > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 주문 요청 상태 '체결'변경
	@Override
	public boolean setOrderStateMatched(int orderNo) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			int savedOrderNo = session.update("stockDetailMapper.setOrderStateMatched", orderNo);
			if(savedOrderNo > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 주문 요청 상태 '취소'변경
	@Override
	public boolean setOrderStateCancel(int orderNo) {
		SqlSession session = null;
		boolean result = false;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			int savedOrderNo = session.update("stockDetailMapper.setOrderStateCancel", orderNo);
			if(savedOrderNo > 0) result = true;
		} finally {
			if(session != null) session.close();
		}
		return result;
	}

	// 매수 주문 요청 매칭
	@Override
	public Map<String, Object> getMatchOrder(int stockNo, int orderPrice, int orderAmount, String studentId,
			String content) {
		SqlSession session = null;
		Map<String, Object> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			Map<String, Object> savedMap = new HashMap<String, Object>();
			savedMap.put("stockNo", stockNo);
			savedMap.put("price", orderPrice);
			savedMap.put("amount", orderAmount);
			savedMap.put("studentId", studentId);
			savedMap.put("content", content);
			result = session.selectOne("stockDetailMapper.getMatchOrder", savedMap);
		} finally {
			if(session != null) session.close();
		}
		return result;
	}
}
