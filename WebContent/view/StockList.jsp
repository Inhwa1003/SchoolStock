<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
          <c:choose>
            <c:when test="${empty stockList}">
              <tr>
                <td colspan="5">등록된 주식이 없습니다.</td>
              </tr>
            </c:when>

            <c:otherwise>
              <c:forEach var="stock" items="${stockList}">
                <tr data-stock-name="${stock.stockName}">
                  <td>${stock.stockName}</td>

                  <td class="current-price">${stock.currentPrice}P</td>
                  <td class="prev-price">${stock.prevPrice}P</td>

                  <c:choose>
                    <c:when test="${stock.priceChange >= 0}">
                      <td class="price-change up">+${stock.priceChange}P</td>
                    </c:when>
                    <c:otherwise>
                      <td class="price-change down">${stock.priceChange}P</td>
                    </c:otherwise>
                  </c:choose>

                  <c:choose>
                    <c:when test="${stock.changeRate >= 0}">
                      <td class="change-rate up">+${stock.changeRate}%</td>
                    </c:when>
                    <c:otherwise>
                      <td class="change-rate down">${stock.changeRate}%</td>
                    </c:otherwise>
                  </c:choose>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
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

  <script>
    const contextPath = "<%=contextPath%>";
  </script>
  <script src="<%=contextPath%>/js/stock-list.js"></script>
</body>
</html>