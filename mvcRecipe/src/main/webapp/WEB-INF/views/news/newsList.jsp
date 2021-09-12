<%--
/**
 * 1. 프로젝트명 : 잇마이레시피
 * 2. 작성일 : 2021. 9. 6. 오후 4:04:33
 * 3. 작성자 : ASUS
 * 4. 설명 : 공지사항의 목록입니다.
 */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>공지사항</title>
<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
<script src="../js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
	<!-- 공지사항 상단 -->
	<div class="container text-center mt-5">
		<div class="row">
			<div class="col">
				<h3>공지사항</h3>
			</div>
			<hr>
		</div>
		<div class="row mt-1">
			<div class="col">
				<h6>게시물 : ${count } 개</h6>
			</div>
		</div>
		<hr>
	</div>
	<!-- 관리자일 경우 공지사항 글 작성 버튼 보이게 -->
	<div class="container text-right mt-3">
		<form action="newsList.do" method="get">
			<select name="division">
				<option value="제목">제목</option>
				<option value="내용">내용</option>
			</select>
			<input type="hidden" value="${mem_num}" name="mem_num"> <input
				type="search" id="search" name="search">
			<button type="submit" class="btn btn-primary mb-2">검색</button>
			<c:if test="${auth==3}">
				<a href="${pageContext.request.contextPath}/news/newsForm.do"
					class="btn btn-info" role="button">글작성</a>
			</c:if>
		</form>
	</div>
	<!-- 공지사항에 게시글이 없을 경우 없다는 표시 -->
	<c:if test="${count == 0}">
		<div class="container mt-5">
			<h3>등록된 게시물이 없습니다.</h3>
		</div>
	</c:if>
	<!-- 공지사항에 게시글이 있을 경우 리스트를 출력 -->
	<c:if test="${count > 0}">
		<div class="container-fluid mt-3 mb-5">
			<table class="table">
				<thead>
					<tr>
						<td>no</td>
						<td>카테고리</td>
						<td>제목</td>
						<td>작성자</td>
						<td>조회수</td>
						<td>작성일</td>
					</tr>
					<c:forEach var="news" items="${list}">
						<tr>
							<td>${news.news_num}</td>
							<td>${news.news_category}</td>
							<td><a href="newsDetail.do?news_num=${news.news_num}">${news.news_title}</a></td>
							<td>${news.id}</td>
							<td>${news.news_hits}</td>
							<td>${news.news_modi}</td>
						</tr>
					</c:forEach>
				</thead>
			</table>
			<div class="row">
				<div class="col text-center mt-4 mb-n1">${pagingHtml}</div>
			</div>
		</div>
	</c:if>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</html>