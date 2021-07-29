<%@page import="com.hpaaycim2.dao.LogDAO"%>
<%@page import="com.hpaaycim2.util.Util"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${dto eq null }"> <!-- dto가 안불려지면 다시 서블릿으로 되돌려보낸다. -->
	<c:redirect url="./index"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>

<style type ="text/css">
<%@ include file="./css/style.jsp" %>
</style>

</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<!--  jstl 사용 -->
	<!-- response에서 받음 -->
	<div id="mainWrapper">
	<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
	
		<div id = "board">
	
			<li class="title">게시판</li>		
				<!-- 게시판 목록 -->

				<ul id ="ulTable">
					<li>
						<ul>
							<li>번호</li>
							<li>제목</li>
							<li>작성일</li>
							<li>작성자</li>
							<li>조회수</li>
						</ul>
					</li>
				<!-- 게시물이 출력될 영역 -->
				<c:forEach items="${dto }" var="dto">
				<!-- 변수생성 -->
					<li>
						<ul>
							<li>${dto.fno }</li>
							<li>
								<a href="./detail?fno=${dto.fno }" id="detail_link">
								${dto.ftitle }
								<c:if test="${dto.commentcount > 0}">
								<small style="color:red; font-weight:bold;">(${dto.commentcount })</small>
								</c:if>
								</a>
							</li>
							<li>${dto.fdate }</li>
							<li>${dto.name }</li>
							<li>${dto.fcount }</li>
						</ul>
					</li>
				</c:forEach>
				</ul>
		
			<div style="text-align:center; margin-top:20px;">
			
				<c:choose>
					<c:when test="${sessionScope.id ne null }">
					<a href="./freeWrite" style="text-decoration:none; color:black;" id="freewrite">글쓰기</a>
					</c:when>
				<c:otherwise>
					<br>
				</c:otherwise>	
				</c:choose>
				
			</div>
				<!-- 게시판 페이징 영역 -->
			<div id="Paging">
				<!-- 페이징 설정 /변수생성구문을 다 여기로 이동 -->
				<c:set var="pageName" value="index" scope="request"/>
				<c:set var="PAGENUMBER" value="10" scope="request"/> <!-- 한 쪽당 10개씩 나열  -->
				<c:import url="paging.jsp"/>
			</div>
			
			<div id="liSearchOption">
				<select id="selSearchOption" style="height:25px;">
					<option value='A'>제목+내용</option>
					<option value='T'>제목</option>
					<option value='C'>내용</option>
				</select>
				<input id='txtKeyWord' style="height:25px; padding:0px;"/>
				<input type='button' style="height:25px;" value='검색'/>
			</div>
			
		</div>

	</div>
	
	<%@ include file="./css/footer.jsp" %>
	
</body>
</html>