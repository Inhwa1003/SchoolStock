package com.school.stockGame.dao.mybatis;

import java.util.Map;

import com.school.stockGame.dao.MemberDAOInterface;

public class MemberDAOMybatis implements MemberDAOInterface {

	@Override
	public boolean setMember(String studentId, String password, String name, int grade, String className, int classNumber) {
		
		return false;
	}

	@Override
	public Map<String, Object> login(String studentId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getIdCheck(String studentId) {
		// TODO Auto-generated method stub
		return false;
	}

}
