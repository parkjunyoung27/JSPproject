<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>galleryDetail</title>
<style>
<%@ include file="./css/style.jsp" %>
</style>

<script src="https://code.jquery.com/jquery-3.6.0.js" 
integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" 
crossorigin="anonymous"></script>

<script type="text/javascript">
function gallery(gno, code){
	//alert("클릭" + gno + code);
	if(code == 'm'){
		if(confirm("해당 글을 수정하시겠습니까?")){
			location.href="./galleryModify?gno=" + gno;
		}
	}else if(code == 'd'){
		if(confirm("해당 글을 삭제하겠습니까?")){
			location.href="./galleryDelete?gno=" + gno;
		}
	}
}

</script>


</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
		<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		<div id = "board">
			<div id="dtitle">
			${list.gno }.${list.gtitle }
			</div>

			<div id="dmember">
			아이디(이름): ${list.id }(${list.name })
			<c:if test="${sessionScope.id eq list.id }">
				<button onclick="gallery(${list.gno},'m');">
					<img alt="modify" src="./img/update.png" height="10px">수정
				</button>
				<button onclick="gallery(${list.gno},'d');">
					<img alt="delete" src="./img/delete.png" height="10px">삭제
				</button>				
			</c:if>
			</div>	
						
			<div id = "ddate">
			업로드: ${list.gdate } &emsp; 조회수:${list.gcount }
			</div>			
	

			<div id="dcontent">
			내용<br>
			${list.gcontent }<br>
				<c:if test= "${list.gfilename ne null }">
				<img alt="그림" src="./upload/${list.gfilename }" width="100%">
				</c:if>
			</div>		
		
		</div>
		
		<div id="bbutton">
			<button onclick="location.href='./gallery'" id="gallerybutton">갤러리로</button>
		</div>
	</div>
	
	<%@ include file="./css/footer.jsp" %>

</body>
</html>