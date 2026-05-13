package com.school.stockGame.dao.mybatis;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.MemberDAOInterface;
import com.school.stockGame.vo.StudentVO;

public class MemberDAOMybatis implements MemberDAOInterface {

	// 회원가입
	@Override
	public boolean setMember(String studentId, String password, String name, int grade, String className, int classNumber) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			flag = session.insert("memberMapper.setMember", new StudentVO(studentId, password, name, grade, className, classNumber)) == 1;
			if(flag){
				session.commit();
			}else{
				session.rollback();
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return flag;
	}

	// 로그인
	@Override
	public Map<String, Object> login(String studentId, String password) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		Map<String, Object> student = null;
		try {
			student = session.selectOne("memberMapper.login", new StudentVO(studentId, password));
		} finally {
			session.close();
		}
		return student;
	}
	
	// 아이디 중복체크
	@Override
	public boolean getIdCheck(String studentId) {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		boolean flag = false;
		try {
			if(session.selectOne("memberMapper.getIdCheck", studentId) == null)
				flag = true;
		} finally {
			session.close();
		}
		return flag;
	}

}
