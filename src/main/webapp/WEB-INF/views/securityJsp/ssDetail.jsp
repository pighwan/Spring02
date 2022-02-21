<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Security MemberDetail **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Spring Security MemberDetail **</h2>
<h2><pre>
<s:authentication var="user" property="principal"/>
* I     D : ${user.username}
* Password: ${user.member.password}
* N a m e : ${user.member.name}
* Level   : ${user.member.lev}
* BirthDay: ${user.member.birthd}
* Point   : ${user.member.point}
* Weight  : ${user.member.weight}
* 추천인   : ${user.member.rid}
* Image  : <img src="${user.member.uploadfile}" width="70" height="70" />       
</pre></h2>
<c:if test="${message!=null}">
<hr>
=> ${message}
</c:if>
<hr>
<a href="mdetail?id=${user.username}&jcode=U">Updatef</a>&nbsp;
<a href="mdelete">Delete</a>&nbsp;
<a href="mlogout">Logout</a>&nbsp;
 
<br><hr>
<a href="mlist">mList</a>&nbsp;
<a href="home">[Home]</a>&nbsp;
</body>
</html>