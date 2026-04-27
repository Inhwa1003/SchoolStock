<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 자산 관리</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/my_assets_style.css">
</head>
<body>
    <div class="container">
        <h2>내 자산</h2>

        <div class="summary-container">
            <div class="card">
                <span class="label">총 자산</span>
                <div class="value" id="total-assets">0P</div>
            </div>
            <div class="card">
                <span class="label">보유 포인트</span>
                <div class="value" id="total-points">0P</div>
            </div>
            <div class="card">
                <span class="label">평가 손익</span>
                <div class="value" id="total-profit">0P</div>
            </div>
            <div class="card">
                <span class="label">보유 쿠폰</span>
                <div class="value" id="coupon-count">0개</div>
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
                    </tbody>
            </table>
        </div>
    </div>

    <script type="text/javascript" src="<%=contextPath%>/js/my_assets_script.js"></script>
</body>
</html>