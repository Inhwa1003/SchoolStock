package com.school.stockGame.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.StockListDAOInterface;
import com.school.stockGame.vo.StockVO;

public class StockListDAOMybatis implements StockListDAOInterface {

	@Override
	public List<StockVO> getStockNameList() {
		SqlSession session = null;
		List<StockVO> result = null;
		try {
			session = DBCPMybatis.getSqlSessionFactory().openSession();
			result = session.selectList("stockListMapper.getStockNameList");
		} finally {
			if (session != null) session.close();
		}
		return result;
	}
}
