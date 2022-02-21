<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_Mybatis BoardList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h3>** Spring_Mybatis BoardList **</h3>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<table width=100%>
<tr height="30" bgcolor="GreenYellow">
	<th>Seq</th><th>Title</th><th>Id</th><th>RegDate</th><th>조회수</th> 
</tr>	
<c:forEach var="list" items="${banana}">
<tr  height="30" align="center">
	<td>${list.seq}</td>
	<td align="left">
	 <!-- 답글 등록후 indent 에 따른 들여쓰기 
			=> 답글인 경우에만 적용  -->
	<c:if test="${list.indent>0}">
		<c:forEach begin="1" end="${list.indent}">
			<span>&nbsp;&nbsp;</span>
		</c:forEach>
		<span style="color:purple">re..</span>
	</c:if>
	
	 <!-- 글내용보기 기능 추가하기 -> login한 경우에만 허용 -->
	<c:if test="${not empty loginID}">
		<a href="bdetail?seq=${list.seq}">${list.title}</a>
	</c:if>
	<c:if test="${empty loginID}">
		${list.title} 
	</c:if>
	</td>
	
	<td align="left">${list.id}</td><td>${list.regdate}</td><td>${list.cnt}</td>
</tr>
</c:forEach>
</table>
<hr>
<c:if test="${not empty loginID}">
	<a href="binsertf">새글등록</a><br>
</c:if>
<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;&nbsp;
<a href="home" >[Home]</a>
</body>
</html>