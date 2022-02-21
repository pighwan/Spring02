<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>    
<html>
<head>
<meta charset="UTF-8">
<title>**  Admin page  **</title>
</head>
<body>
<h1>** Spring Security Admin page **</h1>

<a href="/green/ssLogout">aTag_Logout_Controller로</a> <br>
<pre>
=> a Tag 로 처리 안됨
=> 반드시 form 을 통한 Post 요청으로 처리해야함 
</pre>
<hr>
<form action="/green/ssLogout" method='post'>
	<button>form_Logout_바로로그아웃</button><br>
	=> 요청명이 security-context2.xml logout Tag의 logout-url="/ssLogout" 로 바로 전달됨<br> 
	<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
		=> taglib security 의 아래 Tag 적용하면 생략가능 --%>
	<s:csrfInput/>
</form>
<pre>
=> form Tag 내에서 버튼Tag 는 inputTag type submit 과 동일하게 작동.
=> 로그아웃은 스프링 시큐리티 내부에서 처리되고 성공시 로그인 창이 뜬다.
=> 별도의 작업을 하려면 logoutSuccessHandler 를 정의해서 처리함.
</pre>
<hr>
<a href="home">[Home]</a>&nbsp;
</body>
</html>
