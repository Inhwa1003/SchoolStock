package com.school.stockGame.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public class MyPointHistoryDAOMybatis implements MyPointHistoryDAOInterface {

    @Override
    public List<Map<String, Object>> getMyPointHistoryList(String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            return session.selectList("stockGameMapper.getMyPointHistory", studentId);
        }
    }
}