package com.school.stockGame.dao;

import java.util.Map;

public interface MemberDAOInterface {

	// 회원가입
	public boolean setMember(String studentId, String password, String name, int grade, String className, int classNumber);

	// 로그인
	public Map<String, Object> login(String studentId, String password);

	// 아이디 중복체크
	public boolean getIdCheck(String studentId);
}
