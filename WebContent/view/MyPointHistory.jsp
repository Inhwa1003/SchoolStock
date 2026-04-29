<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>내 포인트 내역</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MyPointHistory.css" />
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
          <tr>
            <td class="date">04/20</td>
            <td>매도</td>
            <td>마라탕</td>
            <td class="plus">+500P</td>
          </tr>

          <tr>
            <td class="date">04/20</td>
            <td>매수</td>
            <td>마라탕</td>
            <td class="minus">-400P</td>
          </tr>

          <tr>
            <td class="date">04/20</td>
            <td>쿠폰</td>
            <td>쿠폰 구매</td>
            <td class="minus">-7,000P</td>
          </tr>

          <tr>
            <td class="date">04/18</td>
            <td>매수</td>
            <td>편의점</td>
            <td class="minus">-70P</td>
          </tr>

          <tr>
            <td class="date">04/17</td>
            <td>지급</td>
            <td>분리수거</td>
            <td class="plus">+500P</td>
          </tr>
        </tbody>
      </table>
    </main>
<jsp:include page="SideBar.jsp" />
  </div>
</body>
</html>