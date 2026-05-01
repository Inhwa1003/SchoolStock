<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 뉴스</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/NewsStyle.css">
</head>
<body>
	<div class="main-layout">

		<main class="news-page-wrapper">
		<div class="header-banner">
			<h1 class="main-title">뉴스</h1>
		</div>

		<div class="news-grid-container" id="newsGrid">
			<c:forEach var="news" items="${newsList}" varStatus="status">
				<div class="news-card-item">
					<div class="card-side-deco color-${status.index % 5}"></div>
					<div class="card-body-content">
						<p class="news-text-main">${news}</p>
					</div>
				</div>
			</c:forEach>
			<c:if test="${empty newsList}">
				<p style="text-align: center; color: #999;">현재 등록된 새로운 소식이 없습니다.</p>
			</c:if>
		</div>
		</main>
		<jsp:include page="SideBar.jsp" />
	</div>

	<%-- <script src="${pageContext.request.contextPath}/js/news-script.js"></script> --%>
</body>
</html>