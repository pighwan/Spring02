<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>** Custom Login Test **</title>
</head>
<body>
  <h2>Custom Login Test</h2>
  <h3>=> ${message}</h3>
  <pre>
  	-> 요청명은 login , method='POST' 
  	-> Tag name 은 username, password 로 함
  	-> 스프링 시큐리티가 적용된 사이트의 Post 방식에는 CSRF 토큰을 사용함
  </pre>
  <form action='/green/login' method='POST'>
  <table>
	<tr><td>UserID:</td><td><input type='text' name='username' value=''></td></tr>
	<tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
	<tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
	<tr><td colspan='2'>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<!-- ** 실행 후 => name="_csrf" value="9cd80e12-3d1b-4eb6-b4a2-686dce16aa81"  
			 ** jstl 적용시 <s:csrfInput /> 로 처리가능 --> 
	</td></tr>
  </table>
  </form>
  <hr>
  <a href="home">[Home]</a>&nbsp;
</body>
</html>
