<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis BoardUpdate Form **</title>
</head>
<body>
<h3>** Spring Mybatis BoardUpdate Form **</h3>
<form action="bupdate" method="post">
<table>
	<tr height="40"><td bgcolor="HoneyDew ">Seq</td>
		<td><input type="text" name="seq" value="${apple.seq}" size="20" readonly></td></tr>
	<tr height="40"><td bgcolor="HoneyDew ">I D</td>
		<td>${apple.id}</td></tr>
	<tr height="40"><td bgcolor="HoneyDew ">Title</td>
		<td><input type="text" name="title" value="${apple.title}"></td></tr>	
	<tr height="40"><td bgcolor="HoneyDew ">Content</td>
		<td>
		<textarea name="content" rows="5" cols="50">${apple.content}</textarea>
		</td></tr>
	<tr height="40"><td bgcolor="HoneyDew ">RegDate</td>
		<td>${apple.regdate}</td></tr>
	<tr height="40"><td bgcolor="HoneyDew ">조회수</td>
		<td>${apple.cnt}</td></tr>						
	<tr height="40"><td></td>
		<td><input type="submit" value="수정">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td></tr>			
</table>
</form>
<c:if test="${not empty message}">
<br>=> ${message}<br><br> 
</c:if>
<hr>
<a href="blist">bList</a>&nbsp;&nbsp;
<a href="home" >[Home]</a>
</body>
</html>