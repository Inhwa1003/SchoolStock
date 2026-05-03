<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보유 쿠폰</title>

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
				<input id="backBtn" class="back-btn" type="button" value="뒤로" />
				<div class="hd-name">${info.name}의 보유 쿠폰</div>
			</header>

			<div class="main">
				<c:forEach var="vo" items="${couponlist}">
					<div class="cp-panel">
						<div class="cp-name">${vo.name}</div>
						<div class="cp-price">${vo.price}P</div>
					</div>
				</c:forEach>

				<c:if test="${empty couponlist}">
					<div class="text-center">보유하신 쿠폰이 없습니다.</div>
				</c:if>
			</div>


		</div>
		<jsp:include page="SideBar.jsp" />
	</div>
	<script type="text/javascript">
		document.querySelector("#backBtn").onclick = function(){history.back();};
	</script>
</body>
</html>