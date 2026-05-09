package com.school.stockGame.dao;

import java.util.List;
import com.school.stockGame.vo.StockVO;

public interface StockListDAOInterface {
    List<StockVO> getStockNameList();
}