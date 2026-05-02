<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${loginOK==null}">
	<script>
		alert('로그인이 필요합니다.');
		location.href = "controller?cmd=LoginAction";
	</script>
</c:if>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/SideBar.css">
</head>
<body>
	<aside class="right-sidebar">
		<div class="user-info">
			<a href="controller?cmd=logoutAction" class="logout-btn">로그아웃</a>
			<h2 class="user-name" id="studentName">${info.name}</h2>
			<p id="studentClassInfo">${info.grade}학년
				${info.className}반
				${info.classNumber}번</p>
			<p class="point-info" id="studentPoints">보유 포인트 :${info.totalPoint}P</p>
		</div>

		<div class="round-box">
			<p>장 운영중</p>
		</div>

		<c:set var="currentCmd" value="${param.cmd}" />
		<nav class="sidebar-menu">
			<button class="menu-button ${currentCmd == 'MyAssetsAction' ? 'active' : ''}"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=MyAssetsAction'">내
				자산</button>
			<button class="menu-button ${currentCmd == 'StockListUI' ? 'active' : ''}"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=StockListUI'">주식
				목록</button>
			<button class="menu-button ${currentCmd == 'NewsUI' ? 'active' : ''}"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=NewsUI'">뉴스
				목록</button>
			<button class="menu-button ${currentCmd == 'MyPointHistoryUI' ? 'active' : ''}"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=MyPointHistoryUI'">내
				포인트 내역</button>
			<button class="menu-button ${currentCmd == 'CouponMarketUI' ? 'active' : ''}"
				onclick="location.href='${pageContext.request.contextPath}/controller?cmd=CouponMarketUI'">쿠폰
				상점</button>
		</nav>
	</aside>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/my-asset-script.js"></script>
</body>
</html>