<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis Reply_Insert Form **</title>
</head>
<body>
<h3>** Spring Mybatis Reply_Insert Form **</h3>
<form action="rinsert" method="post">
<table>
	<tr height="40"><td bgcolor="SeaShell">I D</td>
		<td><input type="text" name="id" value="${loginID}" size="20" readonly></td></tr>
	<tr height="40"><td bgcolor="SeaShell">Title</td>
		<td><input type="text" name="title"></td></tr>	
	<tr height="40"><td bgcolor="SeaShell">Content</td>
		<td><textarea name="content" rows="5" cols="50"></textarea></td></tr>
	
	<!-- 답글등록시 필요한 부모글의 root, step, indent 전달 위함 -->
	<tr height="40"><td></td>
		<td><input type="text" name="root" value="${boardVO.root}" hidden>
			<input type="text" name="step" value="${boardVO.step}" hidden>
			<input type="text" name="indent" value="${boardVO.indent}" hidden>
		</td>
	</tr>
	
	<tr height="40"><td></td>
		<td><input type="submit" value="등록">&nbsp;&nbsp;
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