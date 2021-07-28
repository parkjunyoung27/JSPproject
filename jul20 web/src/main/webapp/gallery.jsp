<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${dtomm eq null }">
	<c:redirect url="./galley"/>  <!-- 값이 없으면 다시 서블릿으로 돌림 -->
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>gallery</title>
<link href="./css/main.css" rel="stylesheet">
<style type="text/css">

#gallery_form {
    background-color: white;
    width: 250px;
    min-height: 300px;
    margin: 25px;
    border-radius: 20px;
    float: left;
}


#gallery_form  div{
	text-align: center;
	margin: 10px;
	font-size: 120%;
}

#gallery_form  div img{
	width: 90%;
	max-height: 200px;
}

#gallery_form p{
	margin: 5px;
	padding: 5px;
	text-align: center;
}

#Paging {
	text-align: center;
	margin: 10px;
	display: inline-block;
	width:100%
}


#galleryWrite:hover{font-size: 150%; font-weight: bold;}
#gallery_form:hover{cursor:pointer; background-color: lightsteelblue;}

</style>
</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
	<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id = "board">
			<li class="title">갤러리</li>		

	
			<c:forEach items="${dtomm }" var="dto">
				<!--  오는 값이 있습니다. -->
					
				<div id="gallery_form"  onclick="location.href='./galleryDetail?gno=${dto.gno }'">
					<div>${dto.gno }. ${dto.gtitle }</div>					
					<div>
						<c:choose>
							<c:when test="${dto.gthumbnail eq null }">
								<img alt="no image" src="img/noimage.jpg" style="height:200px;">
							</c:when>
							<c:otherwise>
								<img alt="thumb" src="./thumbnail/${dto.gthumbnail }">
							</c:otherwise>
						</c:choose>
					<p>${dto.id }(${dto.name })</p>
					<p>조회: ${dto.gcount }</p>
					<p>${dto.date }</p>
					</div>
			</div>
							
			</c:forEach>
				
			<div style="text-align:center; margin-top:20px;">
				
				<c:choose>
					<c:when test="${sessionScope.id ne null }">
					<a href="./galleryWrite" 
					style="text-decoration:none; color:black; width: 100%;
						float: inherit; display: inline-block;" id="galleryWrite">
						사진올리기</a>
					</c:when>
				<c:otherwise>
				</c:otherwise>	
				</c:choose>		
					
			</div>
				<!-- 게시판 페이징 영역 -->
				<div id="Paging">
				<!-- 이동할 페이지명 변수처리 -->
					<c:set var="pageName" value="gallery" scope="request"/>
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
	
</body>
</html>		


</body>
</html>