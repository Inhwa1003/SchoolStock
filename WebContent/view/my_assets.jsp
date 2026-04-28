<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 자산 관리</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/view/my_assets_style.css">
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
                    <tr>
                        <td>${stockName}</td>
                        <td>${stockAmount}</td>

                        <!-- 현재가격은 현재 Action에서 따로 넘긴 값이 없어서 일단 '-' 처리 -->
                        <td>-</td>

                        <td>${averagePrice}P</td>
                        <td>${purchasePrice}P</td>
                        <td>${stockProfit}P</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <script type="text/javascript" src="<%=contextPath%>/js/my_assets_script.js"></script>
</body>
</html>