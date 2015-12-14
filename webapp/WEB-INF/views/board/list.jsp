<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
				
				<c:choose>
					<c:when test="${empty authUser }">
						<c:set var="loginMember_no" value="-1"/>
					</c:when>
					<c:otherwise>
						<c:set var="loginMember_no" value="${authUser.no }"/>
					</c:otherwise>
				</c:choose>
				
				<c:set var="countTotal" value="${fn:length(list) }"/>
				<c:forEach items="${list }" var="list" varStatus="i">
						<tr>
							<td>${countTotal-i.index}</td> <!-- 글번호 -->
							<td><a href="${pageContext.request.contextPath}/board/view/${list.no}">${list.title}</a></td> <!-- 글제목 -->
							<td>${list.member_name}</td> <!-- 글쓴이 -->
							<td>${list.view_cnt}</td> <!-- 조회수 -->
							<td>${list.reg_date}</td> <!-- 등록일 -->
							<td><a href="${pageContext.request.contextPath}/board/delete/${list.no }&${list.member_no }" class="del">삭제</a></td>
						</tr>
				</c:forEach>
								
				</table>
				<div class="pager">
					<ul>
						<li class="pg-prev"><a href="#">◀ 이전</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li class="disable">4</li>
						<li class="disable">5</li>
						<li class="pg-next"><a href="#">다음 ▶</a></li>
					</ul>	
				</div>
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board/writeform" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>

</body>
</html>