<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_Mybatis LoginForm **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
	<script src="resources/myLib/jquery-3.2.1.min.js"></script>
	<script src="resources/myLib/axTest01.js"></script>
</head>
<body>
<h3>** Spring_Mybatis LoginForm **</h3><br>
<form action="login" method="post">
<table>
	<tr><td bgcolor="PaleTurquoise">I D</td>
		<td><input type="text" name="id" id="id" value="banana"></td></tr>
	<tr><td bgcolor="PaleTurquoise ">Password</td>
		<td><input type="password" name="password" id="password" value="12345"></td></tr>
	<tr><td></td>
		<td><input type="submit" value="Login">&nbsp; 
			<input type="reset" value="Reset">&nbsp;
			<span id="axlogin" class="textLink">axLogin</span>&nbsp;
			<span id="jslogin" class="textLink">jsLogin</span>
		</td>
	</tr> 
</table>
</form>
<span id="message"></span><br>
<c:if test="${not empty message}">
	<br>=> ${message}<br><br>
</c:if>
<hr>
<a href="home">[Home]</a>
</body>
</html>