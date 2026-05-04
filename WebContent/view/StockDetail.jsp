<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${stockName}주식 상세</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/StockStyle.css">
<link rel="stylesheet" href="css/Common.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		var stockNo = "${param.no}";
	</script>
	<div class="app">

		<!-- 왼쪽 컨텐츠 구역 -->
		<div class="content">

			<header class="hd">
				<input id="backBtn" class="back-btn" type="button" value="뒤로" />
				<div class="hd-name">주식 상세정보</div>
			</header>

			<div class="stock">
				<div class="stock-info">
					<h2>${stockName}</h2>
					<p>${stockContent}</p>
				</div>
				<div class="stock-price" data-prev-price="${prevPrice}" data-stock-no="${param.no}">
					<div class="price-now">${nowPrice}P</div>
					<div class="price-change"></div>
					<div class="price-base">${prevPrice}P</div>
				</div>
			</div>
			<!-- 메인 구역 -->
			<div class="main">
				<!-- 주문 현황 -->
				<div class="panel">
					<div class="ptitle">
						등록된 주문 현황 <select id="orderTypeSelect">
							<option value="sell" selected>매도</option>
							<option value="buy">매수</option>
						</select>
					</div>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>종류</th>
								<th>가격(P)</th>
								<th>수량</th>
							</tr>
						</thead>
						<tbody id="orderListBody">

						</tbody>
					</table>
				</div>
				<!-- 내 주문 -->
				<div class="panel">
					<div class="ptitle">
						<span>내 요청 주문</span>
						<input type="button" id="refreshBtn" value="조회" />
						
					</div>
					
					<table class="table table-hover">
						<thead>
							<tr>
								<th>종류</th>
								<th>가격(P)</th>
								<th>수량</th>
								<th>날짜</th>
								<th>취소</th>
							</tr>
						</thead>
						<tbody id="myOrderListBody">

						</tbody>
					</table>
				</div>
				<!-- 매도 매수 -->
				<div class="panel">
					<div class="ptitle">주문 요청하기</div>
					<div class="sell-request">
						<div>가격(P)</div>
						<input id="sellPrice" type="number" min="100" step="100"
							Value="100" />
						<div>수량</div>
						<input id="sellAmount" type="number" min="1" Value="1" />
						<div>
							<button id="sellBtn" class="sell-btn">매도</button>
						</div>
					</div>

					<div class="buy-request">
						<div>가격(P)</div>
						<input id="buyPrice" type="number" min="100" step="100"
							Value="100" />
						<div>수량</div>
						<input id="buyAmount" type="number" min="1" Value="1" />
						<div>
							<button id="buyBtn" class="buy-btn">매수</button>
						</div>
					</div>
				</div>
			</div>

		</div>
		<jsp:include page="SideBar.jsp" />
	</div>
	<script src="js/order-status.js" type="text/javascript"></script>
	<%-- 매도 결과 메시지 --%>
	<c:if test="${not empty sessionScope.Message}">
		<script>
			alert("${sessionScope.Message}");
		</script>
		<c:remove var="Message" scope="session" />
	</c:if>

</body>
</html>