<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<link href="./css/main.css" rel="stylesheet">
<style type="text/css">

</style>

</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
	<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
	
		<div id="login-form">
			<form action="./login" method="post">
				<input type="text" id="id" name="id" class="text-field" placeholder="아이디를 입력하세요" required="required">
				<input type="password" id="pw" name="pw" class="text-field" placeholder="암호를 입력하세요" required="required">
				<button type ="로그인" value="로그인" class="submit-btn">LOGIN</button>
				<div class="links">
					<a href="./join">회원 가입</a> &emsp;|&emsp;
					<a href="./idpw">ID/PW 찾기</a>
				</div>
			</form>
		</div>
	
	</div>
	
</body>
</html>
<!-- 
1.servelt -> login(Login)
	get 화면보여주기
		login.jsp보여주기
		
	post  데이터베이스로 값 보내기
		  진짜 있는 사람? 
		  세션 만들어주기  -->
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  