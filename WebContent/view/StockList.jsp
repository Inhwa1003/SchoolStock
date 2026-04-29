<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>주식 목록</title>
  <link rel="stylesheet" href="<%=contextPath%>/css/StockList.css" />
</head>

<body>
  <div class="page-container">

    <main class="stock-main">
      <h1 class="page-title">주식 목록</h1>

      <table class="stock-table">
        <thead>
          <tr>
            <th>주식명</th>
            <th>현재가격</th>
            <th>이전가격</th>
            <th>(현재가격-이전가격)</th>
            <th>등락률(%)</th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>SM</td>
            <td>4,000P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>YG</td>
            <td>5,000P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>JYP</td>
            <td>4,500P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>PC방</td>
            <td>1,200P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>가챠샵</td>
            <td>2,200P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>다이소</td>
            <td>1,100P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>탕후루</td>
            <td>800P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>요아정</td>
            <td>700P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>마라탕</td>
            <td>800P</td>
            <td>600P</td>
            <td>+200P</td>
            <td class="up">+25%</td>
          </tr>

          <tr>
            <td>마크정식</td>
            <td>400P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>
        </tbody>
      </table>
    </main>

    <aside class="right-sidebar">
      <div class="user-info">
        <h2 class="user-name">홍길동</h2>
        <p>5학년 4반 63번</p>
        <p class="point-info">보유 포인트 : 3,900P</p>
      </div>

      <div class="round-box">
        <p>장 운영중</p>
      </div>

      <nav class="sidebar-menu">
        <button class="menu-button">내 자산</button>
        <button class="menu-button active">주식 목록</button>
        <button class="menu-button">뉴스 목록</button>
        <button class="menu-button">내 포인트 내역</button>
        <button class="menu-button">쿠폰 상점</button>
      </nav>
    </aside>

  </div>
</body>
</html>