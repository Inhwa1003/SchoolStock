package com.school.stockGame.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.MyAssetDAOInterface;

public class MyAssetDAOMybatis implements MyAssetDAOInterface {

	@Override
	public int getMyValue(int stockNo, String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
	    param.put("stockNo", stockNo);
	    param.put("studentId", studentId);
	    
	    int result = session.selectOne("myAssetMapper.getMyValue", param);
		
	    session.close();
		return result;
	}

	@Override
	public int getPointValue(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
			int result = session.selectOne("myAssetMapper.getPointValue", studentId);
			session.close();
		return result;
	}

	@Override
	public int getTotalProfit(int stockNo, String studentId, String state) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
	    param.put("stockId", studentId);
	    param.put("state", state);
	    
	    int result = session.selectOne("myAssetMapper.getTotalProfit", param);
		
	    session.close();
		return result;
	}

	@Override
	public int getTotalCoupon(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
			int result = session.selectOne("myAssetMapper.getTotalCoupon", studentId);
			session.close();
		return result;
	}

	@Override
	public String getStockName(int stockNo) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
			String result = session.selectOne("myAssetMapper.getStockName", stockNo);
			session.close();
		return result;
	}

	@Override
	public int getStockAmount(String studentId, int stockNo, String state) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("studentId", studentId);
	    param.put("stockNo", stockNo);
	    param.put("state", state);
	    
	    int result = session.selectOne("myAssetMapper.getStockAmount", param);
		
	    session.close();
		return result;
	}

	@Override
	public int getAveragePrice(String studentId, int stockNo, String state, String content) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
	 
		param.put("studentId", studentId);
		param.put("stockNo", stockNo);
	    param.put("state", state);
	    param.put("content", content);
	    
	    int result = session.selectOne("myAssetMapper.getAveragePrice", param);
		
	    session.close();
		return result;
	}

	@Override
	public int getPurchasePrice(String studentId, int stockNo, String state, String content) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
	 
		param.put("studentId", studentId);
		param.put("stockNo", stockNo);
	    param.put("state", state);
	    param.put("content", content);
	    
	    int result = session.selectOne("myAssetMapper.getPurchasePrice", param);
		
	    session.close();
		return result;
	}

	@Override
	public int getStockProfit(String studentId, int stockNo, String state) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
	 
		param.put("studentId", studentId);
		param.put("stockNo", stockNo);
	    param.put("state", state);
	    
	    int result = session.selectOne("myAssetMapper.getStockProfit", param);
		
	    session.close();
		return result;
	}

	@Override
	public List<Integer> getMyStockNos(String studentId, String state) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> param = new HashMap<String, Object>();
	 
		param.put("studentId", studentId);
	    param.put("state", state);
	    
	    List<Integer> result = session.selectList("myAssetMapper.getMyStockNos", param);
		
	    session.close();
		return result;
	}

}