<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 자산 관리</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/Common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyAssetStyle.css">
</head>
<body>
	<div class="app">
		<div class="content">
			<header class="hd">
				<div class="hd-name">내 자산</div>
			</header>
			<div class="main">
				<div class="summary-container">
					<div class="card">
						<span class="label">총 자산</span>
						<div class="value" id="total-assets">
							<fmt:formatNumber value="${totalAssets}" pattern="#,###" />
							P
						</div>
					</div>
					<div class="card">
						<span class="label">보유 포인트</span>
						<div class="value" id="total-points">
							<fmt:formatNumber value="${totalPoint}" pattern="#,###" />
							P
						</div>
					</div>
					<div class="card">
						<span class="label">총 손익</span>
						<div class="value" id="total-profit"
							class="${totalProfit >= 0 ? 'plus' : 'minus'}">
							<fmt:formatNumber value="${totalProfit}" pattern="#,###" />
							P
						</div>
					</div>
					<div class="card"
						onclick="location.href='${pageContext.request.contextPath}/controller?cmd=CouponPersonalUI'"
						style="cursor: pointer;">
						<span class="label">보유 쿠폰</span>
						<div class="value" id="coupon-count">${totalCoupon}개</div>
					</div>
				</div>

				<div class="panel">
					<div class="table-header">
						<h3>보유 주식 상세</h3>
						<button type="button" class="refresh-all-btn"
							onclick="updateAllStocks()">🔄</button>
					</div>
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
							<c:forEach var="stock" items="${stockList}">
								<tr id="row-${stock.stockNo}" class="stock-row-item">
									<td>${stock.name}</td>
									<td>${stock.amount}개</td>
									<td id="price-${stock.stockNo}" class="stock-price"><fmt:formatNumber
											value="${stock.price}" pattern="#,###" />P</td>
									<td><fmt:formatNumber value="${stock.average}"
											pattern="#,###" />P</td>
									<td><fmt:formatNumber value="${stock.totalPrice}"
											pattern="#,###" />P</td>
									<td id="profit-${stock.stockNo}"
										class="${stock.profit >= 0 ? 'plus' : 'minus'}">
										${stock.profit > 0 ? '+' : ''}<fmt:formatNumber
											value="${stock.profit}" pattern="#,###" />P
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			  </div>
			</div>
			<jsp:include page="SideBar.jsp" />
		</div>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/my-asset-script.js">
	</script>
</body>
</html>
