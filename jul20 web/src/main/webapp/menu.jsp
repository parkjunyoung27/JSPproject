<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<style type="text/css">

#menu {
    width: 150px;
    float: left;
    background-color: #0064CD;
    padding: 0px;
	min-height: inherit;
	border-radius: 10px;
}

#logo_img {
    text-align: center;
    margin-top: 10px;
}

#menu_title{
	width: 140px;
	height: 50px;
	text-align: center;
	vertical-align: middle;
	line-height: 50px;
}

#menu_title a{
	color: white;
	text-decoration: none;	
}

#loginbox{
	width: 140px;
	height: 200px;
	text-align: center;
	color: white;
	background-color: cornflowerblue;
	margin: 0 auto;
	border-radius: 10px;
	line-height: 50px;
}

</style>

<div id= "menu">
	<div id="logo_img">
		<img alt="logo" src="./img/logo.png">
	</div>
		
	<div id= "menu_title">
		<a href="./index">홈</a>
	</div>
			
	<div id= "menu_title">
		<a href="./index">게시판</a>
	</div>
			
	<div id= "menu_title">
		<a href="./gallery">갤러리</a>
	</div>
	
	<!-- 9듭급 (세션)인 분만 볼 수 있는 메뉴입니다. -->
	<c:if test="${sessionScope.grade eq 9 }">
	<div id= "menu_title">
		<a href="./admin">관리자</a>
	</div>
	</c:if>
			
	<c:choose>
		<c:when test="${sessionScope.id ne null }">
			<div id="loginbox">
				<!--  세션이 있다면 ~~님 반갑습니다라고 출력 -->
				${sessionScope.name }님 <br>반갑습니다.<br>
				<button onclick="return myinfo()" style= "border-radius: 5px; cursor: pointer;">내정보</button><br>
				<button onclick="return logout()" style= "border-radius: 5px; cursor: pointer;" >로그아웃</button>
			</div>
		</c:when>
		<c:otherwise>
			<div id= "menu_title">
				<a href="./login">로그인</a>	
			</div>
		</c:otherwise>	
	</c:choose>
</div>
	
<script type="text/javascript">
function logout(){ //중요
	if(confirm("로그아웃하시겠습니까?")){
		//alert("확인을 눌러습니다.");
		location.href="logout";
	}else{
		alert("취소를 눌렀습니다.")
	}
}

</script>
	