<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_Mybatis Member PageList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** Spring_Mybatis Member PageList **</h2>
<br>
<c:if test="${message!=null}">
 => ${message}<br> 
</c:if>
<hr>
<table  width=100%>
<tr height="30" bgcolor="Azure">
	<th>ID</th><th>Password</th><th>Name</th><th>Level</th>
	<th>BirthDay</th><th>Point</th><th>Weight</th><th>추천인</th><th>Image</th>
</tr>
<c:forEach var="list" items="${banana}">
<tr height="30" align="center">
	<td>${list.id}</td><td>${list.password}</td><td>${list.name}</td>
	<td>${list.lev}</td><td>${list.birthd}</td><td>${list.point}</td>
	<td>${list.weight}</td><td>${list.rid}</td>
	<td><img src="${list.uploadfile}" width="50" height="60"></td>
</tr>
</c:forEach>
</table>
<br><hr>

<div align="center">
	<!-- Paging 2 : PageBlock 적용 
		=> 기호 사용  < &lt;   > &gt; 
	-->
	<c:choose>
		<c:when test="${sPageNo>pageNoCount}">
			<a href="mpagelist?currPage=1">FF</a>&nbsp;
			<a href="mpagelist?currPage=${sPageNo-1}">&lt;</a>&nbsp;&nbsp;
		</c:when>
		<c:otherwise>
			<font color="gray">FF&nbsp;&lt;</font>&nbsp;&nbsp;
		</c:otherwise>
	</c:choose>
	
	<c:forEach var="i" begin="${sPageNo}" end="${ePageNo}">
		<c:if test="${i==currPage}">
			<font size="5" color="Orange">${i}</font>&nbsp;
		</c:if>
		<c:if test="${i!=currPage}">
			<a href="mpagelist?currPage=${i}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>
	&nbsp;
	<c:choose>
		<c:when test="${ePageNo<totalPageNo}">
			<a href="mpagelist?currPage=${ePageNo+1}">&gt;</a>&nbsp;
			<a href="mpagelist?currPage=${totalPageNo}">LL</a>
		</c:when>
		<c:otherwise>
			<font color="gray">&gt;&nbsp;LL</font>
		</c:otherwise>
	</c:choose>
	
</div>

<br><hr>
<c:if test="${loginID!=null}"> 	
	<a href="binsertf">새글등록</a>&nbsp;&nbsp;
	<a href="logout">Logout</a>&nbsp;&nbsp;
</c:if>  
<c:if test="${loginID==null}"> 
	<a href="loginf">로그인</a>&nbsp;&nbsp;
	<a href="joinf">회원가입</a>&nbsp;&nbsp;
</c:if>
<a href="home">HOME</a>
</body>
</html>