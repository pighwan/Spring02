<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>    
<html>
<head>
<meta charset="UTF-8">
<title>**  Authentication Success **</title>
</head>
<body>
<h3>** Spring Security Authentication Success **</h3>

<pre>
** principal
=> 접근주체 (리소스에 접근하는 대상, 즉 User) 
=> 결론적으로 아래 property 속성에서 "principal" 은  
   CustomUserDetailsService 의 loadUserByUsername() 메서드에서
   반환된 CustomUser 객체를 의미하며, CustomUser의 맴버변수 member를 통해 접근  
</pre>
<p>** principal Test </p>
<p>principal : <s:authentication property="principal"/></p>
<p>MemberVO : <s:authentication property="principal.member"/></p>
<p>사용자이름 : <s:authentication property="principal.member.name"/></p>
<p>사용자ID1 (id) : <s:authentication property="principal.member.id"/></p>
<p>사용자ID2 (username) : <s:authentication property="principal.username"/></p>
<p>사용자 권한리스트 : <s:authentication property="principal.member.authList"/></p>
<hr>
<pre>
** SpEL(스프링 표현식)  
=> hasRole('ROLE_USER'), hasAuthority('ROLE_ADMIN') : 해당권한 있으면 true
=> hasAnyRole('~,~,..'), hasAnyAuthority('~,~,..') : 해당권한이 하나라도 있으면 true
=> principal : 현재 User 정보를 의미
=> permitAll : 모든 사용자에게 허용
=> denyAll : 모든 사용자에게 거부
=> isAnonymous() : 익명사용자의 경우(로그인 하지않은 경우도 해당)
=> isAuthenticated() : 인증된 사용자면 true
=> isFullyAuthenticated() : Remember-me로 인증된것이 아닌 인증된 사용자의 경우 

** principal, SpEL 적용하기 => home.jsp, ssDetail.jsp 

** remember-me (자동로그인, 로그인 기억하기)
=> 로그인 정보를 일정시간동안 보관하여 재로그인 하지 않도록 해주는기능.
=> 주로 쿠키를 활용하며, 스프링시큐리티에서도 제공 (교재 676P)
</pre>
<hr>
<form action="/green/ssLogout" method='post'>
<button>form_Logout_바로로그아웃</button>
<s:csrfInput />
</form>
<hr>
<a href="ssdetail?id=apple">ssMyInfo</a>&nbsp;&nbsp;
<!--  요청명만 있어도 Principal 객체에 저장된 값을 이용 하여 Detail 출력됨.
      그러나 다른 id 로 요청을 보내면 @PreAuthorize("principal.username == #vo.id") 에 의해 거부됨 -->  
<a href="home">[Home]</a>
</body>
</html>
