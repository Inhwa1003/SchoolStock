<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 상점</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/Common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/StockStyle.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="app">

		<!-- 왼쪽 컨텐츠 구역 -->
		<div class="content">

			<header class="hd">
				<div class="hd-name">쿠폰 상점</div>
			</header>

			<div class="main">
				<div class="cp-panel">
					<input type="button" class="buy-cp-btn" value="구매" />
					<div class="cp-name">간식교환권</div>
					<div class="cp-price">3,000 P</div>
				</div>
				<div class="cp-panel">
					<input type="button" class="buy-cp-btn" value="구매" />
					<div class="cp-name">청소 당번 면제권</div>
					<div class="cp-price">10,000 P</div>
				</div>
				<div class="cp-panel">
					<input type="button" class="buy-cp-btn" value="구매" />
					<div class="cp-name">자리 선택권</div>
					<div class="cp-price">100,000 P</div>
				</div>
				<div class="cp-panel">
					<input type="button" class="buy-cp-btn" value="구매" />
					<div class="cp-name">분리수거 면제권</div>
					<div class="cp-price">8,000 P</div>
				</div>

			</div>


		</div>
		<jsp:include page="SideBar.jsp" />
	</div>

</body>
</html>