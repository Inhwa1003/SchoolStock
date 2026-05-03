<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script src="${pageContext.request.contextPath}/js/coupon-market.js"></script>
</head>
<body>
    <div class="app">
        <!-- 왼쪽 컨텐츠 구역 -->
        <div class="content">
            <header class="hd">
                <div class="hd-name">쿠폰 상점</div>
            </header>

            <div class="main">
                <c:forEach var="coupon" items="${couponList}">
                    <div class="cp-panel">
                        <input type="button" class="buy-cp-btn" value="구매" 
                            onclick="buyCoupon('${coupon.couponNo}', '${coupon.name}', '${coupon.price}')" />
                        <div class="cp-name">${coupon.name}</div>
                        <div class="cp-price">
                            <fmt:formatNumber value="${coupon.price}" pattern="#,###" /> P
                        </div>
                    </div>
                </c:forEach>
                
                <c:if test="${empty couponList}">
                    <div class="empty-msg">현재 판매 중인 쿠폰이 없습니다.</div>
                </c:if>
            </div>
        </div>

        <jsp:include page="SideBar.jsp" />
    </div>

    <c:if test="${not empty sessionScope.buyMessage}">
    <script type="text/javascript">
        alert("${sessionScope.buyMessage}");
        <% session.removeAttribute("buyMessage"); %>
    </script>
</c:if>
</body>
</html>
