package com.school.stockGame.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class MemberDAOMybatis implements MemberDAOInterface {

    @Override
    public boolean addMember(String studentId, String password, String name, int grade, String className, int classNumber) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            try {
                // Map으로 파라미터 전달 (Java 1.8 호환성)
                Map<String, Object> parameterMap = new HashMap<>();
                parameterMap.put("studentId", studentId);
                parameterMap.put("password", password);
                parameterMap.put("name", name);
                parameterMap.put("grade", grade);
                parameterMap.put("className", className);
                parameterMap.put("classNumber", classNumber);
                
                int result = session.insert("stockGameMapper.addMember", parameterMap);
                session.commit();
                return result == 1;
            } catch (Exception e) {
                // 중복 아이디(PK 제약조건 위반) 등의 예외 발생 시 롤백하고 false 반환
                session.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public Map<String, Object> login(String studentId, String password) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("studentId", studentId);
            parameterMap.put("password", password);

            return session.selectOne("stockGameMapper.login", parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean idCheck(String studentId) {
        try (SqlSession session = DBCPMybatis.getSqlSession()) {
            Integer count = session.selectOne("stockGameMapper.idCheck", studentId);
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
