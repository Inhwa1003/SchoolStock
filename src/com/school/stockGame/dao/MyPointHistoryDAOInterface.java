package com.school.stockGame.dao;

import java.util.List;
import java.util.Map;

public interface MyPointHistoryDAOInterface {
    List<Map<String, Object>> getMyPointHistoryList(String studentId);
}