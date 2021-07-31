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
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
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

#adminSearch {
    text-align: center;
    margin: 10px;
}

#searchform{
	text-align:center;
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
				&ensp; 회원관리  |  게시글 관리  |  로그관리
			</div>
			
			<div id="adminMain">
				<h1>로그관리</h1>		
				<c:choose>
					<c:when test="${fn:length(list) > 0}">
						<table>
							<tr>
								<th>번호</th>							
								<th>
								<select onchange="select()" id="ip">
									<option value="" >ip 선택</option>
								<c:forEach items="${ipList }" var="i">
									<c:if test="${i eq ip }"> <!-- ip가 같으면 선택됨  -->
										<option value="${i }" selected>${i }</option>
									</c:if>
									<c:if test="${i ne ip }">
										<option value="${i }">${i }</option>
									</c:if>
									</c:forEach>
								</select>
								</th>
							<th>날짜</th>
								<th>
								<select onchange="select()" id="target">
									<option value="" >target 선택</option>
									<c:forEach items="${targetList }" var="t">
										<c:if test="${target eq t }">
											<option value="${t }" selected>${t }</option>
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
						<c:forEach items="${list }" var="l">
						<tr>						
							<td>${l.log_no }</td>
							<td>${l.log_ip }</td>
							<td>${l.log_date }</td>
							<td>${l.log_target }</td>
							<td>${l.log_id }</td>
							<td><c:out value="${fn:substring(l.log_etc, 0, 40) }"/> </td>
						</tr>
						</c:forEach>
					</table>
				</div>	
				&emsp;&emsp;전체 글 수 : ${totalCount } 개 / 현재 페이지 : ${page }
				<form action="admin" method="post" id ="searchform">
					<select name ="searchname">
						<option value='' selected>전체</option>
						<option value="ip">ip</option>
						<option value="target" >target</option>
						<option value="etc">etc</option>
					</select>
					<input type="text" id="search" name="search">
					<button type="submit">검색</button>
				</form>
			</div>
			
			<div id="Paging">
				<!-- 페이징 설정 /변수생성구문을 다 여기로 이동 -->
		
				<c:set var="pageName" value="admin" scope="request"/>
				<c:set var="PAGENUMBER" value="20" scope="request"/> <!-- 한 쪽당 10개씩 나열  -->
				<c:set var="LIMIT" value="10" scope="request"/>
				<c:import url="paging.jsp"/>

		</div>			
			</c:when>
			<c:otherwise>
				<h2>출력할 데이터가 없습니다.</h2>
			</c:otherwise>
		</c:choose>			
									
		</div>
	</div>
	<%@ include file="./css/footer.jsp" %>

<script type="text/javascript">

function select(){
	//value값을 가져오고 싶다면 ?
	//alert("!");
	var ip = document.getElementById("ip").value;
	var target = document.getElementById("target").value;
	//값 오는 것이 확인된다면 서블릿을 보내서 해당 ip것만 받도록 합니다.
	//location.href='admin?ip=' + ip + '&page=' + ${page };
	location.href='admin?ip='+ip+'&target='+ target + '&page=' + 1;
	}
</script>



</body>
</html>