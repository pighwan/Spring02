<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head profile="http://www.w3.org/2005/10/profile">
<meta charset="UTF-8">
<title>Spring_Mybatis MemberList</title>
<link rel="icon" type="image/png" href="http://example.com/myicon.png">
</head>
<body>
<h3>** Spring_Mybatis MemberList **</h3>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<table width=100%>
<tr height="30" bgcolor="pink">
	<th>I D</th><th>Password</th><th>Name</th><th>Level</th>
	<th>Birthday</th><th>Point</th><th>Weight</th><th>추천인</th><th>Image</th>
</tr>	
<c:forEach var="list" items="${banana}">
<tr  height="30" align="center">
	<td> <!-- 관리자 기능 추가하기 -->
	<c:if test="${loginID=='admin'}">
		<a href="mdetail?id=${list.id}">${list.id}</a>
	</c:if>
	<c:if test="${loginID!='admin'}">
		${list.id} 
	</c:if>
	</td>
	<td>${list.password}</td><td>${list.name}</td><td>${list.lev}</td>
	<td>${list.birthd}</td><td>${list.point}</td>
	<td>${list.weight}</td><td>${list.rid}</td>
	<td><img src="${list.uploadfile}" width="50" height="60"></td>
</tr>
</c:forEach>
</table>
<hr>
<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;&nbsp;
<a href="home" >[Home]</a>

</body>
</html>