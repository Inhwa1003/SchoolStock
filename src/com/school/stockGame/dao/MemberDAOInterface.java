package com.school.stockGame.dao;

import java.util.Map;

public interface MemberDAOInterface {
    boolean addMember(String studentId, String password, String name, int grade, String className, int classNumber);
    Map<String, Object> login(String studentId, String password);
    boolean idCheck(String studentId);
}
