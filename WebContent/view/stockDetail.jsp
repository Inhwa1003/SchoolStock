<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주식 상세</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="css/stock_style.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/order-status.js" type="text/javascript"></script>
</head>
<body>
	<div class="app">

		<!-- 왼쪽 컨텐츠 구역 -->
		<div class="content">

			<header class="hd">
				<input class="back-btn" type="button" value="뒤로" />
				<div class="hd-name">주식 상세정보</div>
			</header>

			<div class="stock">
				<div class="stock-info">
					<h2>${stockName}</h2>
					<p>${stockContent}</p>
				</div>
				<div class="stock-price">
					<div class="price-now">${nowPrice}P</div>
					<div class="price-change">${changePrice}P (${percent}%)</div>
					<div class="price-base">${prevPrice}</div>
				</div>
			</div>
			<!-- 메인 구역 -->
			<div class="main">
			<!-- 주문 현황 -->
				<div class="panel">
					<div class="ptitle">등록된 주문 현황
					    <select id="orderTypeSelect">
							<option value="sell" selected>매도</option>
							<option value="buy">매수</option>
						</select>
						<input type="button" id="refreshBtn" value="새로고침"/>
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
					<div class="ptitle">내 요청 주문</div>
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
						<tbody>
							<tr>
								<th class="buy">매수</th>
								<th>1000</th>
								<th>2</th>
								<th>04.25 09:50</th>
								<th><button class="xbtn">x</button></th>
							</tr>
							<tr>
								<th class="sell">매도</th>
								<th>2000</th>
								<th>2</th>
								<th>04.25 09:51</th>
								<th><button class="xbtn">x</button></th>
							</tr>
							<tr>
								<th class="sell">매도</th>
								<th>1900</th>
								<th>3</th>
								<th>04.25 10:52</th>
								<th><button class="xbtn">x</button></th>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 매도 매수 -->
				<div class="panel">
					<div class="ptitle">주문 요청하기</div>
					<div class="sell-request">
						<div>가격(P)</div>
						<input type="number" min="100" step="100" Value="100" />
						<div>수량</div>
						<input type="number" min="1" Value="1" />
						<div><button class="sell-btn">매도</button></div>
					</div>
					
					<div class="buy-request">
						<div>가격(P)</div>
						<input type="number" min="100" step="100" Value="100" />
						<div>수량</div>
						<input type="number" min="1" Value="1" />
						<div><button class="buy-btn">매수</button></div>
					</div>
				</div>
			</div>

		</div>
		<!-- 오른쪽 네비게이션 바 -->
		<nav class="nav">
			<div class="nav-user">
				<div class="user-name">홍길동</div>
				<div class="class-info">
					<span>5학년</span> <span>4반</span> <span>19번</span>
				</div>
				<div class="point">
					보유포인트 : <span>3,900P</span>
				</div>
			</div>

			<div class="state">장 운영중</div>
			<button class="btn btn-primary">내 자산</button>
			<button class="btn btn-primary" href="controller?cmd=StockListAction">주식 목록</button>
			<button class="btn btn-primary">뉴스 목록</button>
			<button class="btn btn-primary">포인트 내역</button>
			<button class="btn btn-primary">쿠폰 상점</button>
		</nav>
	</div>
</body>
</html>