<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Oracle Board PageList **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** Spring Oracle Board PageList **</h2>
<br>
<c:if test="${message!=null}">
 => ${message}<br> 
</c:if>
<hr>
<table  width=100%>
<tr height="40" bgcolor="PaleTurquoise">
	<th>Seq</th><th>Title</th><th>ID</th><th>RegDate</th><th>조회수</th>
</tr>
<c:forEach var="list" items="${banana}"><tr height="40">
	<td>${list.seq}</td>
	<td>
		<!-- 답글 등록후 indent 에 따른 들여쓰기 
			=> 답글인 경우에만 적용  -->
		<c:if test="${list.indent>0}">
			<c:forEach begin="1" end="${list.indent}">
				<span>&nbsp;&nbsp;</span>
			</c:forEach>
			<span style="color:Purple">re..</span>
		</c:if>
	
		<!-- 로그인 했을때만 글내용을 볼 수 있도록 -->
		<c:if test="${loginID!=null}">
			<a href="bdetail?seq=${list.seq}&id=${list.id}">${list.title}</a>
		</c:if>
		<c:if test="${loginID==null}">
			${list.title}
		</c:if>
	</td>
	<td>${list.id}</td><td>${list.regdate}</td><td align="center">${list.cnt}</td>
</tr></c:forEach>
</table>
<br><hr>

<div align="center">
	<!-- Paging 1 
	=> 현재Page (currPage) : 강조 / 아니면 : Link 적용 
	<c:forEach var="i" begin="1" end="${totalPageNo}">
		<c:if test="${i==currPage}">
			<font size="5" color="Orange">${i}</font>&nbsp;
		</c:if>
		<c:if test="${i!=currPage}">
			<a href="bpagelist?currPage=${i}">${i}</a>&nbsp;
		</c:if>
	</c:forEach> 
	-->
	<!-- Paging 2 : PageBlock 적용 
		=> 기호 사용  < &lt;   > &gt; 
	-->
	<c:choose>
		<c:when test="${sPageNo>pageNoCount}">
			<a href="bpagelist?currPage=1">FF</a>&nbsp;
			<a href="bpagelist?currPage=${sPageNo-1}">&lt;</a>&nbsp;&nbsp;
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
			<a href="bpagelist?currPage=${i}">${i}</a>&nbsp;
		</c:if>
	</c:forEach>
	&nbsp;
	<c:choose>
		<c:when test="${ePageNo<totalPageNo}">
			<a href="bpagelist?currPage=${ePageNo+1}">&gt;</a>&nbsp;
			<a href="bpagelist?currPage=${totalPageNo}">LL</a>
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