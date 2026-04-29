<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 자산 관리</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/MyAssetStyle.css">
</head>
<body>
	<div class="main-layout">

		<main class="asset-page-wrapper">
		<h2>내 자산</h2>

		<div class="summary-container">
			<div class="card">
				<span class="label">총 자산</span>
				<div class="value" id="total-assets">${totalAssets}P</div>
			</div>
			<div class="card">
				<span class="label">보유 포인트</span>
				<div class="value" id="total-points">${totalPoints}P</div>
			</div>
			<div class="card">
				<span class="label">총 손익</span>
				<div class="value" id="total-profit">${totalProfit}P</div>
			</div>
			<div class="card"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=CouponPersonalUI'"
				style="cursor: pointer;">
				<span class="label">보유 쿠폰</span>
				<div class="value" id="coupon-count">${couponCount}개</div>
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
					<c:forEach var="stock" items="${stockList}">
						<tr>
							<td>${stock.name}</td>
							<td>${stock.qty}개</td>
							<td>${stock.current}P</td>
							<td>${stock.avg}P</td>
							<td>${stock.totalCost}P</td>
							<td class="${stock.profit >= 0 ? 'plus' : 'minus'}">
								${stock.profit > 0 ? '+' : ''}${stock.profit}P</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</main>
		<jsp:include page="SideBar.jsp" />
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/MyAssetScript.js"></script>
</body>