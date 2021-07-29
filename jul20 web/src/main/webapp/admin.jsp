<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
<style type="text/css">
<%@ include file="./css/style.jsp" %>

</style>
</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
	<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id = "board">
			<div id="admin">
				<h1>admin</h1>
				관리자 메뉴들 
				실제 내용 출력
			
			</div>
		
		</div>

	<%@ include file="./css/footer.jsp" %>

</body>
</html>