package com.school.stockGame.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.MyPointHistoryDAOInterface;
import com.school.stockGame.dao.jdbc.DBCP;

public class MyPointHistoryDAOMybatis implements MyPointHistoryDAOInterface{
	
	// 내 포인트 내역 조회
	@Override
	public List<Map<String, Object>> getMyPointHistoryList(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<Map<String, Object>> list;
		try {
			list = session.selectList("myPointHistoryMapper.getMyPointHistoryList", studentId);
		} finally {
			session.close();
		}
		return list;
	}

}
