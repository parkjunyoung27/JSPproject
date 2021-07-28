<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!-- jstl -->j
<c:if test="${dto.id eq null }"> <!-- dto가 안불려지면 다시 서블릿으로 되돌려보낸다. -->
	<c:redirect url="./index"/>
</c:if>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link href="./css/main.css" rel="stylesheet">

<style type="text/css">

#welcome_form {
    width: 498px;
    float: inherit;
    margin-left: 360px;
    margin-top: 100px;
    border: none;
    padding: 20px;
    border-radius: 5px;
    background-color: #EEEFF1;
}

#join_logo{
	text-align: center;
	margin-bottom: 70px;
}

#welcome_form img{
	width: 150px;
}

</style>
<script src="https://code.jquery.com/jquery-3.6.0.js" 
integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" 
crossorigin="anonymous"></script>
</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
	<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id="welcome_form">
			<div id="join_logo">
				<img src="./img/cloud2.png">
			</div>
			<h1> 환영합니다!</h1>
			<p> ${dto.name }님, 회원가입을 축하합니다.<br>
			 드림로드의 새로운 아이디는 ${dto.id }입니다.</p>
			
			<p>당신의 꿈을 펼칠 수 있게 응원하겠습니다.</p>
			
			<a href="./login" id="login">로그인하러가기</a>						
				
		</div>
	</div>
</body>
</html>