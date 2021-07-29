<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freedetail</title>
<script src="https://code.jquery.com/jquery-3.6.0.js" 
integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" 
crossorigin="anonymous"></script>
<style type="text/css">
<%@ include file="./css/style.jsp" %>

</style>
<script type="text/javascript">

//제이쿼리 화면변경 사용

$(document).ready(function(){
	//수정하기 기능
	$(".modifyInput").click(function(){
		//var fcontent = $(this).children().first().text();
		//var fcno = $(this).children().last().text();
		//변경
		var fcontent = $(this).children(".fcontent").text();
		var fno = $(this).children(".fno").text();
		var fcno = $(this).children(".fcno").text();
		$(this).parent().html(
				"<form action='./commentModify' method='post'>"
				+"<textarea name='content'>"+fcontent+"</textarea>"
				+"<input type='hidden' name='fcno' value='"+fcno+"'>"
				+"<input type='hidden' name='fno' value='"+fno+"'>"
				+"<button>수정하기</button>"
				+"</form>"
				+"<div class='clear1'>수정취소</div>");
				//content변경 + 댓글번호
	$(".clear1").click(function(){
		//alert(htmlB);
		$(this).parent().html(
				'<div class="modifyInput">수정하기'
		 		+'<div class="fcontent">'+fcontent+'</div>'
		 		+'<div class="fno" style="display: none;">'+fno+'</div>'
		 		+'<div class="fcno" style="display: none;">'+fcno+'</div>'
			 	+'</div>');
	});
	});
	
	
	/*
	//alert("!");
	$("#commentInput").hide(); // 사라지게함
	$(".commentWrite").click(function(){
		var offset = $(".commentWrite").offset();
	$("html,body").animate({scrollTop:offset.top},500);
	
		$(this).text("숨기려면 클릭하세요");
		$(this).hide(); //나를 숨깁니다.		
		$("#commentInput").show("slow"); // 슬로우로 보여준다.
	}); //클릭하면 이 기능이 동작하겠습니다.	
}); //문서가 준비되어있다면
*/

//댓글쓰는 기능
var now = 0;
	$("#commentInput").hide(); //입력창 숨기기
	$(".commentWrite").bind("click focus", function(){ //클릭하면 나타나는 기능 
		var offset = $(".commentWrite").offset();
		$("html, body").animate({scrollTop:offset.top},900); 
		if(now == 0){
			$("#commentInput").slideDown(1000); //아래로 내려감 
			$("#commentInput").html('<form action="./commentInput" method="post"><textarea name="content"></textarea><input type="hidden" name="fno" value="${dto.fno}"><button>댓글쓰기</button></form>');
			//html에 ''안에글 다 집어넣는다는 뜻
			//input 태그는 댓글을 쓰는데  있어서 필수 값
			//fno와 content값이 감 
			$(this).text("닫으려면 클릭하세요.▲");
			now = 1;
		}else{
			$("#commentInput").slideUp(1000); //위로 올라감
			$(this).text("댓글을 쓰려면 클릭하세요.▼");
			now = 0;
		}
	});
});
</script>

</head>
<body>
	<%@ include file="./navbar.jsp" %>
	<div id="mainWrapper">
		<%@ include file="./menu.jsp" %> <!-- 위에 menu.jsp파일이 추가된 것 **중요**-->
		
		<div id = "board">
			<div id="dtitle">
			${dto.fno }.${dto.ftitle }
			</div>

			<div id="dmember">
			아이디(이름): ${dto.id }(${dto.name })
			</div>	
						
			<div id = "ddate">
			업로드: ${dto.fdate } &emsp; 조회수:${dto.fcount }
			</div>			
	

			<div id="dcontent">
			내용<br>
			${dto.fcontent }<br>
				<c:if test= "${dto.file ne null }">
				<img alt="그림" src="./upload/${dto.file }" width="100%">
				</c:if>
			</div>
			
			<div id="comment">
				<!-- 댓글 유무를  없으면 달아달라고 표시-->
				<c:choose> 
					<c:when test="${dto.commentcount > 0 }">
						${dto.commentcount }개의 댓글이 있습니다.
					</c:when>
					<c:otherwise> 
						댓글이 없습니다. 댓글을 달아주세요.
					</c:otherwise>
				</c:choose>
				<br>
				<hr style="color:white;">
				<c:choose>
					<c:when test="${fn:length(commentList) > 0 }">
						<c:forEach items="${commentList}" var="i">
							<button>${i.fcno }. ${i.name }(<small>${i.id }</small>)</button>
								${i.fcdate }
							<button>${i.fclike }</button>
								
							<div class="modifyBox">
								<div class="modifyInput">
									수정하기
									<div class="fcontent">${i.fccontent }</div>
									<div class="fno" style="display:none;">${i.fno } </div>
									<div class="fcno" style="display:none;">${i.fcno } </div>
								</div>
							</div>
														
							<hr style="color:white">
						</c:forEach>					
					</c:when>
						
					<c:otherwise>
							댓글이 없습니다.
					</c:otherwise>
				</c:choose>
				<!-- 여기에 댓글 쓰기 창을 연결합니다. -->
				<!-- Jquery로 할겁니다. 준비물이 뭔지 확인해주세요. -->
				<c:if test="${sessionScope.id ne null }">
				<div class="commentWrite">댓글을 쓰려면 클릭하세요.▼</div>
				<div id="commentInput"></div>
				</c:if>
			</div>
		
		</div>	
		
			<div id="bbutton">
			<button onclick="location.href='./index'" id="boardbutton">게시판으로</button>
			</div>
		
	</div>		
	
	<%@ include file="./css/footer.jsp" %>

</body>
</html>