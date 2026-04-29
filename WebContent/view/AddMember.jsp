<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 페이지</title>
<link rel="stylesheet" type="text/css" href="/css/addMember.css" />
</head>
<body>

	<div class="addmember-container">
		<h1>회원가입</h1>

		<div class="input-row">
			<label>아이디 :</label>
			<div class="input-field-group">
				<input type="text"> <span class="message success">*
					사용가능한 아이디 입니다!</span>
			</div>
		</div>

		<div class="input-row">
			<label>비밀번호 :</label>
			<div class="input-field-group">
				<input type="password"> <span class="message info">비밀번호는
					특수기호를 포함한 8자리 이상</span>
			</div>
		</div>

		<div class="input-row">
			<label>비밀번호 확인 :</label>
			<div class="input-field-group">
				<input type="password"> <span class="message error">비밀번호를
					확인 해주세요.</span>
			</div>
		</div>

		<div class="info-row">
			<span>년도 : <span id="current-year" style="font-weight: bold;"></span></span>
			<span>학년 :</span> <select>
				<option>5</option>
				<option>6</option>
			</select> 
			<span>반 :</span> <input type="text" class="small" value="4">
			<span>번호 :</span> <input type="number" class="small" value="63"
				min="1" max="99">
		</div>

		<button class="submit-button">로그인</button>
	</div>

	<script>
    const today = new Date();
    document.getElementById('current-year').innerText = today.getFullYear();
</script>

</body>
</html>