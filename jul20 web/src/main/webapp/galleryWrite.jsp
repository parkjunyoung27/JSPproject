<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>gallery Write</title>
<link href="./css/main.css" rel="stylesheet">
<style type="text/css">

#write{
	margin:0 auto;
	margin-top: 30px;
	width: 90%;
	height: auto;
}

#write input, #write textarea, #write button{
	border: 0px;
	width: 100%;
	min-height: 40px;
	padding: 10px;
	box-sizing: border-box;
}

#write textarea{
	margin: 10px 0px;
	height: 200px;
}
</style>

<script type="text/javascript">

function check(){
	var	test = confirm("글을 올리시겠습니까?");
	if(test == true){
		alert("글이 게시됐습니다.");
		location.href="./gallery";
	}else{
		alert("게시가 취소됐습니다.");
		location.href="./galleryWrite";		
		return false;
	}
}
</script>

</head>
<body>
	<%@ include file="./navbar.jsp" %>
	
	<div id="mainWrapper">
		<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id="board">
			<div id ="write">
				<form action="./galleryWrite" method="post" enctype="multipart/form-data" > <!-- multipart/form-data중요 -->
					<input tye="text" name ="title"
							required = "required" placeholder="제목을 적어주세요">
					<textarea name="content" required="required" 
					placeholder="글을 적어주세요"></textarea>					
					<!-- 파일 업로드 -->
            		<input type="file" name="file1" accept=".gif, .png, .jpg, .jpeg"> <!-- 이미지파일만 올리기 -->
					<button type="submit" onclick="return check()">글쓰기</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>