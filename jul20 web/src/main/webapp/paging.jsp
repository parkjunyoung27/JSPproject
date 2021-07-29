<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 
만드는 변수마다 모두 scope="request" 적어주세요.
이것은 변수의 참조 범위를 지정하는 것 입니다.
scope="page"가 기본
 -->

<!-- totalCount = 총 글수, PAGENUMBER는 나누고 싶은 페이지 쪽수 , 우선 정수값으로 만듬 예) 3.5 = 3 -->
<!--  index의 경우 51개 페이지 넘버 10니깐 totalPage는 6개 -->
<!--  gallery의 경우 총 6개 페이지 넘버 6니깐 total page는 1개 -->
<fmt:parseNumber integerOnly="true" var="totalPage" value="${totalCount / PAGENUMBER }" scope="request"/> 
<!-- 남은 개수는 ? 추가로 처리해주는 명령을 넣겠습니다. -->
<c:if test= "${totalCount % PAGENUMBER ne 0 }"> <!-- 딱 맞아 떨어지지 않는 경우 -->
	<c:set var="totalPage" value="${totalPage + 1 }" scope="request"/> 
	<!--  아까 남은 .1개를 보여주기 위한 또 다른 페이지 추가-->
</c:if>
			  
			 <c:if test = "${page % PAGENUMBER ne 0 }" > <!-- 현재페이지 % 페이지넘버  몫이 0 아닐 때 --> 
			 	<!--  예 2/5 = 0.4 => 0, 7/5 = 1.4 -> 1=> 2 --> 
				 <fmt:parseNumber integerOnly="true" var="startPage" value="${page / PAGENUMBER }" scope="request"/> 
			 	<c:set value="${startPage * PAGENUMBER + 1 }" var="startPage" scope="request"/> 
			 	<!--  11,12 일때 -->
			 </c:if>
			 
			 <c:if test="${page % PAGENUMBER eq 0 }">   <!-- 예  5, 10, 15 => 1로 가게-->
			 	<c:set value="${page - (PAGENUMBER-1) }" var="startPage" scope="request"/>  <!-- 10쪽 설정 -->
			 </c:if>
			 
			 <!-- 마지막 페이지 -->
			 <!-- 1+(5-1) -->
			 <c:set value="${startPage + (PAGENUMBER-1) }" var="endPage" scope="request"/>
			 <!-- 1 + PAGENUMBER > 총 페이지수 -->
			 <!-- 어려움 -->
			 <c:if test="${startPage + PAGENUMBER  lt totalPage}"> <!-- 1쪽 + 5쪽 > 총 페이지수 -->
			 		<c:set var ="endPage" value="${totalPage }" scope="request"/> <!-- 마지막 쪽 -->
			 </c:if>
			 
			 <button onclick="location.href='./index?page=1'">맨앞으로</button>
			
			<c:if test="${page lt PAGENUMBER+1}"><!--현재page < 5(페이지넘버)+1 -->  <!-- 12345 에서 67810넘어갈때  -->
				<button disabled ="disabled">이전</button>
			</c:if>
			<c:if test="${page gt PAGENUMBER}"> <!-- 현재 페이지 > 5(페이지) -->
				<button onclick="location.href='./${pageName}?page=${page-PAGENUMBER}'">
				이전</button>
			</c:if>
			
			<c:if test="${page eq 1 }"> <!--  뒤로가기 -->
				<button disabled="disabled">◀ </button>
			</c:if>
			<c:if test="${page ne 1 }"> <!-- 1이 아닐때  -->
				<button onclick="location.href='./${pageName}?page=${page-1}'">◀</button>
			</c:if>
				
				<!-- 실제 값을 찍어주는 곳 -->
				<c:forEach begin="${startPage }" end="${totalPage }" var="i"> 
					<c:if test= "${i eq page }">
						<a href="./${pageName }?page=${i}"><button>${i }</button></a>
					</c:if>
					<c:if test= "${i ne page }">
						<a href="./${pageName }?page=${i}">${i }</a>
					</c:if>
				</c:forEach>
				
			<c:if test="${page eq totalPage }"> <!-- 마지막 페이지일 경우 -->
				<button disabled ="disabled">▶</button> <!-- 다음 쪽 페이지 이동 못함 -->
			</c:if>
			<c:if test="${page ne totalPage }">
				<button onclick="location.href='./${pageName}?page=${page+1 }'">
				▶</button>
			</c:if>
			<!-- PAGENUMBER 개씩 뛰어넘게 -->
			<c:if test="${page lt totalpage - PAGENUMBER -1}"> <!-- 현재 페이지 < 총 페이지 - 5 - 1  -->
				<button onclick="location.href='./${pageName }?page=${page+PAGENUMBER}'">다음 </button>
			</c:if>
			<c:if test="${page gt totalpage - PAGENUMBER }">
				<button disabled="disabled">다음</button>
			</c:if>
			<button onclick="location.href='./${pageName }?page=${totalPage}'">끝으로</button>	
			 
