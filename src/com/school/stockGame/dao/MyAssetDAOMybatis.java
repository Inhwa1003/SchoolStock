package com.school.stockGame.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class MyAssetDAOMybatis implements MyAssetDAOInterface {

	@Override
	public int getMyValue(int stockNo, String studentId) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("stockNo", stockNo);
			param.put("studentId", studentId);
			Integer result = session.selectOne("stockGameMapper.getTotalPrice", param);
			return result == null ? 0 : result;
		}
	}

	@Override
	public int getPointValue(String studentId) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Integer result = session.selectOne("stockGameMapper.getTotalPoint", studentId);
			return result == null ? 0 : result;
		}
	}

	@Override
	public int getTotalProfit(int stockNo, String studentId, String state) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("stockNo", stockNo);
			param.put("studentId", studentId);
			param.put("state", state);
			Integer result = session.selectOne("stockGameMapper.getTotalProfit", param);
			return result == null ? 0 : result;
		}
	}

	@Override
	public int getTotalCoupon(String studentId) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Integer result = session.selectOne("stockGameMapper.getTotalCoupon", studentId);
			return result == null ? 0 : result;
		}
	}

	@Override
	public String getStockName(int stockNo) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			return session.selectOne("stockGameMapper.getStockName", stockNo);
		}
	}

	@Override
	public int getStockAmount(String studentId, int stockNo, String state) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("studentId", studentId);
			param.put("stockNo", stockNo);
			param.put("state", state);
			Integer result = session.selectOne("stockGameMapper.getStockAmount", param);
			return result == null ? 0 : result;
		}
	}

	@Override
	public int getAveragePrice(String studentId, int stockNo, String state, String content) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("studentId", studentId);
			param.put("stockNo", stockNo);
			param.put("state", state);
			param.put("content", content);
			Integer result = session.selectOne("stockGameMapper.getAverragePrice", param); // Mapper xml의 오타 유지 또는 xml 수정 필요
			return result == null ? 0 : result;
		}
	}

	@Override
	public int getPurchasePrice(String studentId, int stockNo, String state, String content) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("studentId", studentId);
			param.put("stockNo", stockNo);
			param.put("state", state);
			param.put("content", content);
			Integer result = session.selectOne("stockGameMapper.getPurchasePrice", param);
			return result == null ? 0 : result;
		}
	}

	@Override
	public int getStockProfit(String studentId, int stockNo, String state) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("studentId", studentId);
			param.put("stockNo", stockNo);
			param.put("state", state);
			Integer result = session.selectOne("stockGameMapper.getStockProfit", param);
			return result == null ? 0 : result;
		}
	}

	@Override
	public List<Integer> getMyStockNos(String studentId, String state) {
		try (SqlSession session = DBCPMybatis.getSqlSession()) {
			Map<String, Object> param = new HashMap<>();
			param.put("studentId", studentId);
			param.put("state", state);
			return session.selectList("stockGameMapper.getStockNo", param);
		}
	}

}
