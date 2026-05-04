<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 페이지</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/AddMember.css" />
</head>
<body>

	<div class="addmember-container">
		<h1>회원가입</h1>
		<form method="post" action="controller?cmd=AddMemberAction">

			<div class="input-row">
				<label>아이디 :</label>
				<div class="input-field-group">
					<input type="text" name="studentId"> <span
						id="id-check-msg"></span>
				</div>
			</div>

			<div class="input-row">
				<label>비밀번호 :</label>
				<div class="input-field-group">
					<input type="password" name="password" id="password" /> 
					<span class="message info">비밀번호는 8자리 이상</span>
				</div>	
			</div>

			<div class="input-row">
				<label>비밀번호 확인 :</label>
				<div class="input-field-group">
					<input type="password" id="passwordCheck">
					<span id="pwd-check-msg" class="message"></span> 
				</div>
			</div>
			<div class="input-row">
				<label>이름 :</label>
				<div class="input-field-group">
					<input type="text" name="name" />
				</div>

			</div>

			<div class="info-row">
				<span>년도 : <span id="current-year" style="font-weight: bold;"></span></span>
				<span>학년 :</span> <select name="grade">
					<option>5</option>
					<option>6</option>
				</select> <span>반 :</span> <input type="text" class="small" name="className">
				<span>번호 :</span> <input type="number" class="small"
					name="classNumber" min="1" max="99">
			</div>

			<button class="submit-button">회원가입</button>
		</form>
		<div class="login-link-container">
			이미 아이디가 있나요? <a
				href="${pageContext.request.contextPath}/controller?cmd=LoginUI"
				class="back-to-login"> 로그인하러 가기 </a>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/add-member.js"></script>
</body>
</html>