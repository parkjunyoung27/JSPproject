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
<!-- totalCount = 총 글수, PAGENUMBE = 한 화면에 나타낼 글 수  , 우선 정수값으로 만듬 예) 3.5 = 3 -->
<!--  index의 경우 51개 페이지 넘버 10니깐 totalPage는 6개 -->
<!--  gallery의 경우 총 6개 페이지 넘버 6니깐 total page는 1개 -->
<%//System.out.println("페이징에 넘어간 ip:" +  request.getParameter("ip")); %>
<%//System.out.println("페이징에 넘어간 target:"+request.getParameter("target")); %>
<c:choose>
	<c:when test="${ip ne null || target ne null}">
		<c:set var = "pageName" value="${pageName }?ip=${ip }&target=${target }&" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var = "pageName" value="${pageName }?" scope="request"/>	
	</c:otherwise>
</c:choose>

<fmt:parseNumber integerOnly="true" var="totalPage" value="${totalCount / PAGENUMBER }" scope="request"/> 
<!-- 남은 개수는 ? 추가로 처리해주는 명령을 넣겠습니다. -->
<!-- 딱 맞아 떨어지지 않는 경우 --> <!-- PAGENUMBER랑 SQL page랑 값 같게 -->
<c:if test="${totalCount % PAGENUMBER ne 0 }"> 
	<c:set var="totalPage" value="${totalPage+1 }" scope="request"/> 
</c:if>
			  
<!-- c:if test = "${page % PAGENUMBER ne 0 }"  현재페이지 % 페이지넘버  나머지가 0 아닐 때 --> 
<c:if test="${page % LIMIT ne 0}">
<!--  예 2/5 = 2 => 0, 7/5 = 2 --> 
	<fmt:parseNumber integerOnly="true" var="startPage" value="${page / LIMIT }" scope="request"/> 
	<c:set value="${startPage * LIMIT+1 }" var="startPage" scope="request"/> <!-- 배수에서 +1 -->
	<!--  11,12 일때 -->
</c:if>
			 
<c:if test="${page % LIMIT eq 0 }">   <!-- 예  5, 10, 15 => 1로 가게-->
	<c:set value="${page-(LIMIT-1) }" var="startPage" scope="request"/>  <!-- 10쪽 설정 -->
</c:if>

<!-- 마지막 페이지 -->
<!-- 1+(5-1) --> <!-- 20 페이지 넘버 수  -->
<c:set value="${startPage+(LIMIT-1) }" var="endPage" scope="request"/> <!-- 10개씩 -->
<!-- 1 + PAGENUMBER > 총 페이지수 -->
<!-- 어려움 -->
<c:if test="${startPage+4 gt totalPage}"> <!-- 마지막 페이지 > 총 페이지수 -->
	<c:set var ="endPage" value="${totalPage }" scope="request"/> <!-- 마지막 쪽 -->
</c:if>
			 
 <button onclick="location.href='./${pageName}page=1'">맨앞으로</button>
			
	<c:if test="${page lt (LIMIT+1)}"><!--현재page < 5(페이지넘버)+1 -->  <!-- 12345 에서 67810넘어갈때  -->
		<button disabled ="disabled">이전</button>
	</c:if>
	<c:if test="${page gt LIMIT}"> <!-- 현재 페이지 > 5(페이지) -->
		<c:if test="${(page % LIMIT) eq 0 }">
		<button onclick="location.href='./${pageName}page=${endPage-LIMIT}'">이전</button>
		</c:if>
		<c:if test="${(page % LIMIT) ne 0 }">
		<button onclick="location.href='./${pageName}page=${page-(page % LIMIT)}'">이전</button>
		</c:if>
	</c:if>
			
	<c:if test="${page eq 1 }"> <!--  뒤로가기 -->
		<button disabled="disabled">◀ </button>
	</c:if>
	
	<c:if test="${page ne 1 }"> <!-- 1이 아닐때  -->
		<button onclick="location.href='./${pageName}page=${page-1}'">◀</button>
	</c:if>
				
		<c:forEach begin="${startPage }" end="${endPage }" var="i"> 
			<c:if test= "${i eq page }">
				<a href="./${pageName }page=${i}"><b>${i }</b></a>
			</c:if>
			<c:if test= "${i ne page }">
				<a href="./${pageName }page=${i}">${i }</a>
			</c:if>
		</c:forEach>
				
	<c:if test="${page eq totalPage }"> <!-- 마지막 페이지일 경우 -->
		<button disabled ="disabled">▶</button> 
	</c:if>
	
	<c:if test="${page ne totalPage }">
		<button onclick="location.href='./${pageName}page=${startPage+5 }'"> ▶ </button>
	</c:if>
	
	<!-- 현재 페이지가 총 페이지수의 startPage 보다 작다면 -->
	<c:if test="${startPage lt totalPage-(LIMIT-1)}"> <!-- 현재 페이지 < 총 페이지 - 5 - 1  -->
		<button onclick="location.href='./${pageName }page=${startPage+LIMIT}'">다음 </button>
	</c:if>
	
	<c:if test="${startPage+(LIMIT-1) gt totalPage }">
		<button disabled="disabled">다음</button>
	</c:if>

	<button onclick="location.href='./${pageName }page=${totalPage}'">끝으로</button>	
			 