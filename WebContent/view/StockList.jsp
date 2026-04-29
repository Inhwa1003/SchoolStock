<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>주식 목록</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/StockList.css" />
</head>

<body>
  <div class="page-container">

    <main class="stock-main">
      <h1 class="page-title">주식 목록</h1>

      <table class="stock-table">
        <thead>
          <tr>
            <th>주식명</th>
            <th>현재가격</th>
            <th>이전가격</th>
            <th>(현재가격-이전가격)</th>
            <th>등락률(%)</th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>SM</td>
            <td>4,000P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>YG</td>
            <td>5,000P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>JYP</td>
            <td>4,500P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>PC방</td>
            <td>1,200P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>가챠샵</td>
            <td>2,200P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>다이소</td>
            <td>1,100P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>

          <tr>
            <td>탕후루</td>
            <td>800P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>요아정</td>
            <td>700P</td>
            <td>220P</td>
            <td>-20P</td>
            <td class="down">-10%</td>
          </tr>

          <tr>
            <td>마라탕</td>
            <td>800P</td>
            <td>600P</td>
            <td>+200P</td>
            <td class="up">+25%</td>
          </tr>

          <tr>
            <td>마크정식</td>
            <td>400P</td>
            <td>180P</td>
            <td>+20P</td>
            <td class="up">+10%</td>
          </tr>
        </tbody>
      </table>
    </main>
    <jsp:include page="SideBar.jsp" />
  </div>
</body>
</html>