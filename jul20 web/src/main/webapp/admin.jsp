<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
<style type="text/css">
<%@ include file="./css/style.jsp" %>
#adminMenu{
	width: 100%;
	height: 50px;
	background-color: midnightblue;
	color: white;
	line-height:50px;
	overflow:hidden;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
	font-weight: bold;
}

#adminMain{
	width: 100%;
	background-color: gainsboro;
	height: 580px;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
}

h1{ /* 제목*/
	color: black;
	text-align: center;
	font-size: 180%;
	margin-top: 0px;
	margin-bottom: 20px;
}

table{ /* 테이블*/ 
	margin: 0 auto;
	height: 500px;
	width : 90%;
	background-color: #ffffff;
	border-collapse: collapse;
	border-color: #2B55B1;
	border-width: 3px;
	text-align: center;
	overflow: scroll;
}

table th{ /*메뉴 제목*/
	font-size: 110%;
	font-weight: bold;
	border-bottom: 4px #2B55B1 solid;
	height: 50px;
}

table td{ /* 제목*/ 
	border-bottom : 1px #2B55B1 solid;
}


tr:hover{
	transition: 2s;
	transition-delay: 0.5s;
	background-color: #D0E5E0;
}

</style>
</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
		<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id = "board">
			<div id="admin">
				<div id="adminMenu">
					<!-- 꾸며주세요 -->
					&ensp; 회원관리  |  게시글 관리  |  로그관리
				</div>
				<div id="adminMain">
					<h1>admin</h1>
					<table>
						<tr>
							<th>번호</th>							
							<th>
								<select onchange="select()" id="ip"> <!-- select가 변경된다면 -->
									<option value="">ip를 선택</option>
									<c:forEach items="${ipList }" var="i">
										<c:if test="${i eq ip }">
										<!-- 서버가 보내준 ip와 값이 같다면 활성화 -->
										<option value="${i }" selected="selected">${i }</option> 
										<!-- value는 ip값, 자바스크립트 값 넘겨주는건  id를 줘야됨  -->
										</c:if>
										<c:if test="${i ne ip }">
										<option value="${i }">${i }</option> 
										<!-- value는 ip값, 자바스크립트 값 넘겨주는건  id를 줘야됨  -->
										</c:if>
									</c:forEach>
								</select>	
							</th>
							<th>날짜</th>
							<th>
								<select onchange="select()" id="target">
									<option disabled="diabled">target을 선택</option>
									<c:forEach items="${targetList }" var="t">
										 <c:if test="${target eq t }">
										 	<option value="${t }" selected="selected">${t }</option>
										 </c:if>
										 <c:if test="${target ne t }">
										 	<option value="${t }">${t }</option>
										 </c:if>
									</c:forEach>
								</select>	
							</th>
							<th>id</th>
							<th>기타</th>
						</tr>
							<c:forEach items="${list }" var="list">
						<tr>
							<td>${list.log_no} </td>
							<td>${list.log_ip} </td>
							<td>${list.log_date }  </td>
							<td>${list.log_target }  </td>
							<td>${list.log_id }  </td>
							<td>${list.log_etc }  </td> <!-- 출력조절해서 -->
						</tr>
							</c:forEach>
					</table>
				</div>	
			</div>
			<div id="Paging">
				<!-- 페이징 설정 /변수생성구문을 다 여기로 이동 -->
				<c:set var="pageName" value="admin" scope="request"/>
				<c:set var="PAGENUMBER" value="20" scope="request"/> <!-- 한 쪽당 10개씩 나열  -->
				<c:import url="paging.jsp"/>
			</div>
		</div>
	</div>
	<%@ include file="./css/footer.jsp" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script type="text/javascript">
function select(){
	//alert("!");
	//value값을 가져오고 싶다면 ?
	var target = document.getElementById("target").value;
	var ip = document.getElementById("ip").value;	
	//alert(ip + "!");
	location.href='admin?ip=' + ip + "&target=" + target; <!-- 동시에 2개다, 하나만 해도 나옴, 페이징 분여도 됨-->
}

function selectTarget(){
	var target = document.getElementById("target").value;
	//location.href='admin?target=' + target + '&page=' + ${page};
	location.href='admin?target=' + target;
}

</script>


</body>
</html>