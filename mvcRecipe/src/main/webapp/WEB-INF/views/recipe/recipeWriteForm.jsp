<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
 * 작성일 : 2021. 9. 6.
 * 작성자 : 이현지
 * 설명 : 레시피 글작성 폼
 * 수정일 : 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>레시피 글작성</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// 이벤트 연결
		$('#write_form').submit(function() {
			if($('#category').val() == '') {
				alert('카테고리를 선택하세요!');
				$('#category').val('').focus();
				return false;
			}
			
			if($('#title').val().trim() == '') {
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			
			if($('#content').val().trim() == '') {
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
			// 이미지 미리보기..... 할까 말까
		});
	});
</script>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
</head>
<body>
<!-- container 시작 -->
<div class="container" style="width:100%">
	<h2>레시피 작성</h2><%-- 임시(나중에 삭제) --%>
	<form id="write_form" action="recipeWrite.do" method="post" enctype="multipart/form-data">
		<ul>
			<li>
				<label for="category">카테고리</label>
				<select name="category" id="category">
					<option value="">선택하세요</option>
					<option value="식사">식사</option>
					<option value="간식">간식</option>
					<option value="음료">음료</option>
				</select>
			</li>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" maxlength="50">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="50" name="content" id="content"></textarea>
			</li>
			<li>
				<label for="filename">파일</label>
				<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
			</li>
		</ul>
		<div>
			<input type="submit" value="등록">
			<input type="button" value="목록" onclick="location.href='recipeList.do'">
		</div>
	</form>
</div>
<!-- container 끝 -->
</body>
<jsp:include page = "/WEB-INF/views/common/footer.jsp" />
</html>