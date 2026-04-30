<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 자산 관리</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/MyAssetStyle.css">
</head>
<body>
    <div class="container">
        <h2>내 자산</h2>

        <div class="summary-container">
            <div class="card">
                <span class="label">총 자산</span>
                <div class="value" id="total-assets">${myValue}P</div>
            </div>

            <div class="card">
                <span class="label">보유 포인트</span>
                <div class="value" id="total-points">${pointValue}P</div>
            </div>

            <div class="card">
                <span class="label">평가 손익</span>
                <div class="value" id="total-profit">${totalProfit}P</div>
            </div>

            <div class="card">
                <span class="label">보유 쿠폰</span>
                <div class="value" id="coupon-count">${totalCoupon}개</div>
            </div>
        </div>

        <div class="table-container">
            <h3>보유 주식 상세</h3>

            <table id="stock-table">
                <thead>
                    <tr>
                        <th>주식명</th>
                        <th>보유수량</th>
                        <th>현재가격</th>
                        <th>평균단가</th>
                        <th>총 구매 비용</th>
                        <th>수익금</th>
                    </tr>
                </thead>

                <tbody id="stock-list">
                    <c:choose>
                        <c:when test="${empty stockName}">
                            <tr>
                                <td colspan="6">보유 주식이 없습니다.</td>
                            </tr>
                        </c:when>

                        <c:otherwise>
                            <tr>
                                <td>${stockName}</td>
                                <td>${stockAmount}</td>
                                <td>-</td>
                                <td>${averagePrice}P</td>
                                <td>${purchasePrice}P</td>

                                <c:choose>
                                    <c:when test="${stockProfit >= 0}">
                                        <td class="plus">+${stockProfit}P</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="minus">${stockProfit}P</td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>