package com.school.stockGame.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.vo.StockVO;

public class StockListDAOMybatis implements StockListDAOInterface {

    @Override
    public List<StockVO> getStockNameList() {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            List<Map<String, Object>> resultList = session.selectList("stockGameMapper.getStockNameList");
            List<StockVO> stockList = new ArrayList<>();
            for (Map<String, Object> map : resultList) {
                int stockNo = ((Number) map.get("STOCK_NO")).intValue();
                String name = (String) map.get("NAME");
                stockList.add(new StockVO(stockNo, name));
            }
            return stockList;
        }
    }
}