<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 뉴스</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/Common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/NewsStyle.css">
</head>
<body>
	<div class="app">
		<div class="content">
		
			<header class="hd">
				<div class="hd-name">오늘의 뉴스</div>
			</header>
			
			<div class="main">
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
			</div>
			
		</div>
		<jsp:include page="SideBar.jsp" />
	</div>

</body>
</html>