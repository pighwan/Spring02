<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis BoardDetail **</title>
</head>
<body>
<h2>** Spring Mybatis BoardDetail **</h2>
<table>
	<tr height="40">
		<td bgcolor="Lavender">Seq</td><td>${apple.seq}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">I D</td><td>${apple.id}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Title</td><td>${apple.title}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">Content</td>
		<td><textarea rows="5" cols="50" readonly>${apple.content}</textarea></td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">RegDate</td><td>${apple.regdate}</td>
	</tr>
	<tr height="40">
		<td bgcolor="Lavender">조회수</td><td>${apple.cnt}</td>
	</tr>
</table>
<c:if test="${not empty message}">
<hr>
=> ${message}<br>
</c:if>
<!-- 1) 글수정, 글삭제
		-> 내가 쓴글인 경우, 관리자 인경우 에만 가능 
		-> loginID 와 apple.id 가 동일한 경우
 -->
 <hr>
<c:if test="${loginID==apple.id  || loginID=='admin'}">
	<a href="bdetail?jcode=U&seq=${apple.seq}">글수정</a>&nbsp;&nbsp;
	<a href="bdelete?root=${apple.root}&seq=${apple.seq}">글삭제</a>
		<!-- 삭제시 원글삭제 or 답글삭제 확인을 위함 -->
</c:if> 
<!-- 새글, 답글등록 추가하기 -->
&nbsp;&nbsp;<a href="binsertf">새글등록</a>
&nbsp;&nbsp;<a href="rinsertf?root=${apple.root}&step=${apple.step}&indent=${apple.indent}">답글등록</a><br>
<hr>
<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;&nbsp;
<a href="home">HOME</a>
</body>
</html>