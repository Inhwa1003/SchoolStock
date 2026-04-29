<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>내 포인트 내역</title>
  <link rel="stylesheet" href="<%=contextPath%>/css/MyPointHistory.css" />
</head>

<body>
  <div class="page-container">

    <main class="history-main">
      <h1 class="page-title">내 포인트 내역</h1>

      <table class="history-table">
        <thead>
          <tr>
            <th>날짜</th>
            <th>유형</th>
            <th>내용</th>
            <th>포인트 변화</th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td class="date">04/20</td>
            <td>매도</td>
            <td>마라탕</td>
            <td class="plus">+500P</td>
          </tr>

          <tr>
            <td class="date">04/20</td>
            <td>매수</td>
            <td>마라탕</td>
            <td class="minus">-400P</td>
          </tr>

          <tr>
            <td class="date">04/20</td>
            <td>쿠폰</td>
            <td>쿠폰 구매</td>
            <td class="minus">-7,000P</td>
          </tr>

          <tr>
            <td class="date">04/18</td>
            <td>매수</td>
            <td>편의점</td>
            <td class="minus">-70P</td>
          </tr>

          <tr>
            <td class="date">04/17</td>
            <td>지급</td>
            <td>분리수거</td>
            <td class="plus">+500P</td>
          </tr>
        </tbody>
      </table>
    </main>

    <aside class="right-sidebar">
      <div class="user-info">
        <h2 class="user-name">홍길동</h2>
        <p>5학년 4반 63번</p>

        <p class="asset-info">잔여 자산 : 3,900P</p>
      </div>

      <div class="round-box">
        <p>장 운영중</p>
      </div>

      <nav class="sidebar-menu">
        <button class="menu-button">내 자산</button>
        <button class="menu-button">주식 목록</button>
        <button class="menu-button">뉴스 목록</button>
        <button class="menu-button active">내 포인트 거래 내역</button>
        <button class="menu-button">쿠폰 상점</button>
      </nav>
    </aside>

  </div>
</body>
</html>