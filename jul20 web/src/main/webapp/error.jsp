<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error</title>
<style>
<%@ include file="./css/style.jsp" %>

</style>

</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
		<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id = "board">
		
		<div id="error">
			${param.code }로 인하여 에러가 발생<br>
			<c:choose>
				<c:when test="${code eq 404 }">
					<h1>페이지를 찾을 수 없습니다.</h1>
					<h2>올바른 경로로 접근하시기 바랍니다.</h2>
				</c:when>
				<c:when test="${code eq 500 }">
					<h1>내부 오류가 있습니다.</h1>
					<h2>관리자에게 문의 바랍니다.</h2>
				</c:when>				
				<c:otherwise></c:otherwise>
			</c:choose>
		</div>

		</div>
	</div>
	
		<%@ include file="./css/footer.jsp" %>
	
</body>
</html>