<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모의 투자 시스템</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/Login.css">
</head>
<body>
	<div class="login-container">
		<h1 class="project-name">SchoolStock</h1>
		<c:if test="${not empty errorMessage}">
			<div class="error-msg"> ${errorMessage}</div>
		</c:if>
		<form method="post" action="controller?cmd=LoginAction">
			<div class="login-form">
				<div class="input-group">
					<div class="input-row">
						<label>아이디 :</label> <input type="text" name="studentId" />
					</div>
					<div class="input-row">
						<label>비밀번호 :</label> <input type="password" name="password" />
					</div>
				</div>
				<button type="submit" class="login-button">로그인</button>
			</div>
		</form>
		<div class="html-button">
			<a href="controller?cmd=AddMemberUI">회원가입 </a>
		</div>
	</div>
</body>
</html>