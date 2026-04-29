<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/SideBar.css">
</head>
<body>
	<aside class="right-sidebar">
	<div class="user-info">
		<h2 class="user-name" id="studentName">${name}</h2>
		<p id="studentClassInfo">${grade}학년 ${className}반 ${classNumber}번</p>
		<p class="point-info" id="studentPoints">보유 포인트 : ${totalPoint}P</p>
	</div>

	<div class="round-box">
		<p>장 운영중</p>
	</div>

	<nav class="sidebar-menu">
	<button class="menu-button active">내 자산</button>
	<button class="menu-button">주식 목록</button>
	<button class="menu-button">뉴스 목록</button>
	<button class="menu-button">내 포인트 내역</button>
	<button class="menu-button">쿠폰 상점</button>
	</nav> </aside>
	<script type="text/javascript" src="../js/MyAssetScript.js"></script>
</body>
</html>