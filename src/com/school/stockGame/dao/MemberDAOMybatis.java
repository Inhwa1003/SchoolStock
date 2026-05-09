package com.school.stockGame.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class MemberDAOMybatis implements MemberDAOInterface {

    @Override
    public boolean addMember(String studentId, String password, String name, int grade, String className, int classNumber) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            // Map으로 파라미터 전달
            Map<String, Object> parameterMap = Map.of(
                "studentId", studentId,
                "password", password,
                "name", name,
                "grade", grade,
                "className", className,
                "classNumber", classNumber
            );
            int result = session.insert("stockGameMapper.addMember", parameterMap);
            session.commit();
            return result == 1;
        }
    }

    @Override
    public Map<String, Object> login(String studentId, String password) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Map<String, Object> parameterMap = Map.of(
                "studentId", studentId,
                "password", password
            );
            return session.selectOne("stockGameMapper.login", parameterMap);
        }
    }

    @Override
    public boolean idCheck(String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Integer count = session.selectOne("stockGameMapper.idCheck", studentId);
            return count != null && count > 0;
        }
    }
}
