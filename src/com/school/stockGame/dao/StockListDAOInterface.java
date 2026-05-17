package com.school.stockGame.dao;

import java.util.List;

import com.school.stockGame.vo.StockVO;

public interface StockListDAOInterface {
	// 현재 기존 파일들을 최대한 그대로 사용가능하게 작성하는 중입니다
	// 따라서 추후 스프링 부트로 리팩토링 할 때 부족한 메서드들을 선언하는 것이 맞다고 판단했습니다.
	// 1. 주식명 조회
	public List<StockVO> getStockNameList();
	// 2. 주식 현재가격 조회  
    // 3. 주식 이전가격, 전장마감가 조회
    // 4. 주식 이전가 대비 조회
    // 5. 주식 등락률 조회
    // 6. 주식 목록 화면용 전체 정보 조회
}
