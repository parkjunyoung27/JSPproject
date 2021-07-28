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

			 <fmt:parseNumber integerOnly="true" var="totalPage" 
			 value="${totalCount / PAGENUMBER }" scope="request"/> 
			 <!-- 남은 개수는 ? 추가로 처리해주는 명령을 넣겠습니다. -->
			 <c:if test= "${totalCount % PAGENUMBER ne 0 }">
			 	<c:set var="totalPage" value="${totalPage + 1 }" scope="request"/>
			 	<!--  아까 남은 .1개를 보여주기 위한 또 다른 페이지 추가 -->
			 </c:if>
			  
			 <c:if test = "${page % PAGENUMBER ne 0 }" > <!--  나누기 10 했는데 0 아니면  크게보는 1페이지-->  
				 <fmt:parseNumber integerOnly="true" var="startPage" value="${page / PAGENUMBER }" scope="request"/> 
			 	<c:set value="${startPage * PAGENUMBER + 1 }" var="startPage" scope="request"/> 
			 	<!--  11,12 일때 -->
			 </c:if>
			 
			 <c:if test="${page % PAGENUMBER eq 0 }">   <!-- 나머지값이 0 일때  10%10=0 -->
			 	<c:set value="${page - (PAGENUMBER-1) }" var="startPage" scope="request"/> 시작페이지 설정 <!-- 10쪽 설정 -->
			 </c:if>
			 
			 <c:set value="${startPage + (PAGENUMBER-1) }" var="endPage" scope="request"/>
			 <c:if test="${startPage + PAGENUMBER  gt totalPage}">
			 		<c:set var ="endPage" value="${totalPage }" scope="request"/> <!-- 마지막 쪽 -->
			 </c:if>
			 
			 <button onclick="location.href='./index?page=1'">맨앞으로</button>
			
			<c:if test="${page lt PAGENUMBER+1}"><!-- > -->
				<button disabled ="disabled">이전</button>
			</c:if>
			<c:if test="${page gt PAGENUMBER}">
				<button onclick="location.href='./${pageName}?page=${page-PAGENUMBER}'">
				이전</button>
			</c:if>
			
			<c:if test="${page eq 1 }">
				<button disabled="disabled">◀ </button>
			</c:if>
			<c:if test="${page ne 1 }">
				<button onclick="location.href='./index?page=${page-1}'">◀</button>
			</c:if>
				
				<!-- 실제 값을 찍어주는 곳 -->
				<c:forEach begin="${startPage }" end="${endPage }" var="i"> 
					<c:if test= "${i eq page }">
						<a href="./${pageName }?page=${i}"><button>${i }</button></a>
					</c:if>
					<c:if test= "${i ne page }">
						<a href="./${pageName }?page=${i}">${i }</a>
					</c:if>
				</c:forEach>
				
			<c:if test="${page eq totalPage }"> <!-- 마지막 페이지일 경우 -->
				<button disabled ="disabled">▶</button>
			</c:if>
			<c:if test="${page ne totalPage }">
				<button onclick="location.href='./index?page=${page+1 }'">
				▶</button>
			</c:if>
			<!-- 10개씩 뛰어넘게 -->
			<c:if test="${page lt PAGENUMBER -1}">
				<button onclick="location.href='./${pageName }?page=${page+PAGENUMBER}'">다음 </button>
			</c:if>
			<c:if test="${page gt totalpage - PAGENUMBER }">
				<button disabled="disabled">다음</button>
			</c:if>
			<button onclick="location.href='./${pageName }?page=${totalPage}'">끝으로</button>	
			 
