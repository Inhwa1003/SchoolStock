<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>내 포인트 내역</title>
  <link rel="stylesheet" href="<%=contextPath%>/css/MyPointHistory.css" />
</head>

<body>
  <div class="page-container">

    <main class="history-main">
      <h1 class="page-title">내 포인트 내역</h1>

      <table class="history-table">
        <thead>
          <tr>
            <th>날짜</th>
            <th>유형</th>
            <th>내용</th>
            <th>포인트 변화</th>
          </tr>
        </thead>

        <tbody>

          <c:choose>
            <c:when test="${empty historyList}">
              <tr>
                <td colspan="4">포인트 내역이 없습니다.</td>
              </tr>
            </c:when>

            <c:otherwise>
              <c:forEach var="history" items="${historyList}">
                <tr>
                  <td class="date">${history.historyDate}</td>
                  <td>${history.historyType}</td>
                  <td>${history.historyContent}</td>

                  <c:choose>
                    <c:when test="${history.pointChange >= 0}">
                      <td class="plus">+${history.pointChange}P</td>
                    </c:when>

                    <c:otherwise>
                      <td class="minus">${history.pointChange}P</td>
                    </c:otherwise>
                  </c:choose>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>

        </tbody>
      </table>
    </main>

    <jsp:include page="SideBar.jsp" />

  </div>
</body>
</html>