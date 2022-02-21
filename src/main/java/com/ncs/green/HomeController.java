package com.ncs.green;


import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//** 스프링MVC : 스프링 DispatcherServlet 사용 
//=> Ano 기반  

//** @Component (bean 생성 @) 의 세분화 
//=> 스프링 프레임워크에서는 클래스들을 기능별로 분류하기 위해 @ 을 추가함.
//=> @Controller:사용자요청을 제어하는 Controller 클래스
//			     DispatcherServlet이 해당 객체를 Controller객체로 인식하게해줌. 	
//=> @Service :비즈니스로직을 담당하는 Service 클래스
//=> @Repository:DB 연동을 담당하는 DAO 클래스
//			     DB 연동과정에서 발생하는 예외를 변환 해주는 기능 추가 

//*** @Controller 사용함
//=> implements Controller 를 대신함.
//=> 아래와 관련된 import 삭제 해야 함.
//   public class LoginController implements Controller {
//=> 메서드명, 매개변수, 리턴값 에 자유로워짐 
//	-> 메서드명은 handleRequest 사용안해도 됨
//	-> 매개변수 다양한 정의 가능 (메서드내에서 생성할 필요 없어짐)
//	-> 리턴값은 ModelAndView 또는 String 가능함

//=> 요청별 Controller 를 한 클래스내에 메서드로 구현할 수 있게 됨  
//=> 요청 과 메서드 연결 은 @RequestMapping 으로
//------------------------------------------------------

//** @RequestMapping
//  DefaultAnnotationHandlerMapping에서 컨트롤러를 선택할 때 대표적으로 사용하는 애노테이션. 
//  DefaultAnnotationHandlerMapping은 클래스와 메서드에 붙은 @RequestMapping 애노테이션 정보를 결합해 최종 매핑정보를 생성한다.
//  기본적인 결합 방법은 클래스 레벨의 @RequestMapping을 기준으로 삼고, 메서드 레벨의 @RequestMapping으로 세분화하는 방식으로 사용된다.

//=> url당 하나의 컨트롤러에 매핑되던 다른 핸들러 매핑과 달리 메서드 단위까지 세분화하여 적용할 수 있으며,
//   url 뿐 아니라 파라미터, 헤더 등 더욱 넓은 범위를 적용할 수 있다. 
//=> 요청과 매핑메서드 1:1 mapping 
//=> value="/mlist" 
// : 이때 호출되는 메서드명과 동일하면 value 생략가능 그러나 value 생략시 404 (확인필요함)
// : 해당 메서드 내에서 mv.setViewName("...."); 을 생략하면
//   요청명을 viewName 으로 인식 즉, mv.setViewName("mlist") 으로 처리함.

//=> value : URL 패턴 ( 와일드카드 * 사용 가능 )
//		@RequestMapping(value="/post")
//		@RequestMapping(value="/post.*")
//		@RequestMapping(value="/post/**/comment")
//		@RequestMapping(value={"/post", "/P"}) : 다중매핑 가능

//=> method 
//  @RequestMapping(value="/post", method=RequestMethod.GET)
//		-> url이 /post인 요청 중 GET 메서드인 경우 호출됨
//  @RequestMapping(value="/post", method=RequestMethod.POST)
//		-> url이 /post인 요청 중 POST 메서드인 경우 호출됨
//  GET, POST, PUT, DELETE, OPTIONS, TRACE 총 7개의 HTTP 메서드가 정의되어 있음.

//=> params : 요청 파라미터와 값으로도 구분 가능함.

//	@RequestMapping(value="/post", params="useYn=Y")
//		-> /post?useYn=Y 일 경우 호출됨
//	@RequestMapping(value="/post", params="useYn!=Y")
//		->  not equal도 가능
//	@RequestMapping(value="/post", parmas="useYn")
//		-> 값에 상관없이 파라미터에 useYn이 있을 경우 호출됨
//	@RequestMapping(value="/post", params="!useYn")
//		-> 파라미터에 useYn이 없어야 호출됨
//------------------------------------------------------
// ** @RequestMapping @GetMapping @PostMapping 차이
// => @GetMapping : @RequestMapping(method = RequestMethod.GET ...) 동일효과
// => @PostMapping: @RequestMapping(method = RequestMethod.POST ...) 동일효과
// => 단, @RequestMapping 은 클래스레벨 적용가능 하지만, 나머지 둘은 메서드레벨 적용만 가능

//------------------------------------------------------
//** 매핑메서드 의 매개변수	
//=> 매개변수로 정의 하면 메서드 내에서 생성할 필요 없음
//=> request.getParameter값  VO에 담기 => 자동화됨 
//	-> vo를 매핑 메서드의 매개변수로 선언하면 자동으로 대입됨
//	-> 단, form 의 input Tag의 name과 vo의 변수명(setter로 찾음) 이 동일한 경우만 자동 대입됨. 

//=> Parameter처리 다른방법 : @RequestParam
//	public ModelAndView plogin(ModelAndView mv,
//		@RequestParam("id")String id, @RequestParam("pw")String pw) {……	
//		  
//		-> String id=request.getParameter("id") 와 동일,
//			그러나 매개변수 로 VO 를 사용하는것이 가장 간편 
//------------------------------------------------------

//** Model & ModelAndView
//=> Model(interface)
//	-> controller처리 후 데이터를 view에 전달하는 역할 
//	-> 구현클래스 : ModelMap
//=> ModelAndView (class)
//	-> controller처리 후 데이터와 출력할 view를 지정하는 역할
//	-> Object -> ModelAndView
//------------------------------------------------------

//** Logger & Locale ****************************

//** Logger : 현재 위치상태를 보여줘서 에러 위치를 잡을수 있게 해 줄 수 있는 코드
//=> Log4J의 핵심 구성 요소로 6개의 로그 레벨을 가지고 있으며,
//   출력하고자 하는 로그 메시지를 Appender (log4j.xml 참고) 에게 전달한다.

//=> 활용 하려면 pom.xml 에 dependency (log4j, slf4j) 추가 (되어있음),
//=> Controller에는 아래의 코드를 넣어주고,
//=> 확인이 필요한 위치에서 원하는 메세지 출력, Sysout 은 (I/O 발생으로) 성능 저하 유발
//   현재 클래스 내에서만 사용가능하도록 logger 인스턴스 선언 & 생성

//** Locale : (사건등의 현장), 다국어 지원 설정을 지원하는 클래스
//=> locale 값을 받아서 현재 설정된 언어를 알려줌 -> 한글 메시지 출력 가능
//=> jsp 의 언어설정을 받아 해당 언어에 맞게 자동으로 message가 출력 되도록 할때 사용.
//   logger.info("Welcome home! 로그 메시지 Test -> locale is {}.", locale);
//=> {} : 일종의 출력 포맷 으로 우측 ',' 뒷편 변수의 값이 표시됨.

//=> src/main/resources/log4j.xml 의 <logger> 태그에 패키지명을 동일하게 지정해야함
//=> 패턴 적용 가능
//https://blog.naver.com/deersoul6662/222024554482
//https://to-dy.tistory.com/20 (종합 )

//** Log4J : Log for Java(Apache Open Source Log Library)의 준말
//=> 자바기반 로깅 유틸리티로 디버깅용 도구로 주로 사용됨,되고 있다.
//   로그를 남기는 가장 쉬운 방법은 System.out.println("로그 메세지")이지만
//   프로그램 개발 완료 후 불필요한 구문은 삭제해야 하며 성능 저하 요인이 됨.
//   Log4J 라이브러리는 멀티스레드 환경에서도 성능에 전혀 영향을 미치지 않으면서
//   안전하게 로그를 기록할 수 있는 환경을 제공함.

//=> 로깅레벨 단계
//   TRACE > DEBUG > INFO > WARN > ERROR > FATAL(치명적인)
//   TRACE: Debug보다 좀더 상세한 정보를 나타냄
//   DEBUG: 애플리케이션의 내부 실행 상황을 추적하기 위한 상세 정보 출력
//         ( Mybatis 의 SQL Log 확인 가능 )
//   INFO : 상태변경과 같은 주요 실행 정보 메시지를 나타냄
//   WARN : 잠재적인 위험(에러)를 안고 있는 상태일 때 출력 (경고성 메시지)
//   ERROR: 오류가 발생했지만, 애플리케이션은 계속 실행할 수 있을 때 출력
//   FATAL: 애플리케이션을 중지해야 할 심각한 에러가 발생 했을 때 출력

//=> log4j.xml 의 root Tag 에서 출력 level 조정
//=> <root> <priority value 값 >
//-> 이 값을 warn (default) , error, debug, trace
// 변경하면서 Spring 이 출력하는 Console Message 를 비교해 본다.
//-> 차이점
//: info, warn, error -> INFO, WARN, ERROR
//: debug -> 위에 DEBUG 추가
//: trace -> 위에 TRACE 추가
//=> 실제는 DEBUG, WARN 이 주로 이용됨.

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		// ** Logger Message Test
		// 1) {} 활용
		logger.info("** Test1) 안녕하세요 ~~");
		logger.info("** Test1) 안녕하세요~~ {}님.","홍길동");
		// 2) 변수 활용
		String name = "김길동"; int age=100;
		logger.info("** Test2) 이름:"+name+", 나이:"+age);
		logger.info("** Test2) 이름:{}, 나이:{}",name,age);
		
		// ** 로깅레벨 조정 Test (log4j.xml 의)
		// => root Tag 에서 출력 level 조정 (system 오류 level조정) 
		//	    <root> <priority value 값 >
		// => <logger name="com.ncs.green"> 에서 출력 level 조정
		//      <level value="DEBUG" />
		// => 이 두곳의 값을 warn (default) , error, debug, trace
		logger.warn("** 로깅레벨 Test warn , name => "+name) ;
		logger.error("** 로깅레벨 Test error , name => "+name) ;
		logger.debug("** 로깅레벨 Test debug , name => "+name) ;
		logger.trace("** 로깅레벨 Test trace , name => "+name) ;
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	} //home
	
// ** AjaxTest Main Form
	@RequestMapping(value = "/axtestf")
	public ModelAndView axtestf(ModelAndView mv) {
		mv.setViewName("axTest/axTestForm");
		return mv;
	} 
// ** BCryptPasswordEncoder Test
//@RequestMapping(value = "/bcrypt")
//@RequestMapping() -> 요청명과 메서드멸 동일한경우 생략가능하다고함 그러나 value 생략시 404 (확인필요함)
	public ModelAndView bcrypt(ModelAndView mv) {
		
		// PasswordEncoder (Interface) -> BCryptPasswordEncoder 구현클래스
		// => import org.springframework.security.crypto.password.PasswordEncoder;
		// => import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password="12345!" ; // rawData
		
		// 1) encode(rawData) => 동일 원본(rawData)에 대해 다른 Digest 생성 Test
		//    String digest1,2,3 = passwordEncoder.encode("password");
		//    모두 동일 rawData 적용후 모두 다른 digest 가 생성됨을 Test 해본다.
		
		String digest1 = passwordEncoder.encode(password) ;
		String digest2 = passwordEncoder.encode("22221!") ;
		String digest3 = passwordEncoder.encode("33333!") ;
		System.out.println("** digest1 => "+digest1);
		System.out.println("** digest2 => "+digest2);
		System.out.println("** digest3 => "+digest3);
		
		// 2) matches(rawData, digest)
		System.out.println("** matches1 => "+ passwordEncoder.matches(password, digest1));
		System.out.println("** matches2 => "+ passwordEncoder.matches(password, digest2));
		System.out.println("** matches3 => "+ passwordEncoder.matches(password, digest3));
		
		mv.setViewName("redirect:home");
		return mv;
	}//bcrypt
	
// ** Exception  Test *************************** 	
	@RequestMapping(value = "/etest")
	public ModelAndView etest(ModelAndView mv) {
		// 1. ArithmeticException
		// => 1.1) web.xml 적용
		// => 1.2) Spring exceptionResolver 적용 ( servlet~~~.xml )
		//int i = 100/0 ; // by zero, 500
		
		// ** 실수형 Zero 나눔 Test : Exception 발생하지 않음
		// => 그러므로 Zero 연산 확인은 
		//	  if (Double.isInfinity(d)) ~ ~ ,  if (Double.isNan(p)) ~~~ 
		double d = 100.0/0.0 ; // Infinity
		double p = 100.0%0.0 ; // NaN
		System.out.println("** ArithmeticException Test => " +d+" , "+p);
		
		// 2. IllegalArgumentException
		String s = "123" ;  // s="123a" : Error_Value
		int i = 100;
		//i += Integer.parseInt(s);
		if ( Integer.parseInt(s) == 0) i=100 ;
		System.out.println("** IllegalArgumentException Test => " + i);
		
		// 3. NullPointerException
		s=null;
		// if ( s.equals("test")) s="Test" ; -> NullPointerException 발생
		// => 예방코드
		if ( "test".equals(s) ) s="Test" ;
		else s="Null" ;
		System.out.println("** NullPointerException Test => " + s);
		
		// 4. defaultErrorView Test
		String[] city = {"성남","용인","서울"};
		System.out.println("** defaultErrorView Test => " + city[3]);
		// java.lang.ArrayIndexOutOfBoundsException : xml 에 정의하지 않음
		
		// 5. Sql Test 
		// => axJoin 으로  DuplicateKeyException , SqlException 
		
		mv.setViewName("redirect:home");
		return mv;
	} 
// ************************************************	
// ** KaKaoMap Test *******************************
	@RequestMapping(value = "/greensn")
	public ModelAndView greensn(ModelAndView mv) {
		mv.setViewName("kakaoMapJsp/map01_greenSN");
		return mv;
	}	
	@RequestMapping(value = "/greenall")
	public ModelAndView greenall(ModelAndView mv) {
		mv.setViewName("kakaoMapJsp/map02_greenAll");
		return mv;
	}
	@RequestMapping(value = "/jeju")
	public ModelAndView jeju(ModelAndView mv) {
		mv.setViewName("kakaoMapJsp/map03_jeju");
		return mv;
	}

// **************************************************	
// ** WebSocket Echo Test ***************************
	@RequestMapping(value = "/echo")
	public ModelAndView echo(ModelAndView mv) {
		mv.setViewName("axTest/wsEcho");
		return mv;
	} //echo
	
// ** WebSocket Chat Test **
	@RequestMapping(value = "/chat")
	public ModelAndView chat(ModelAndView mv) {
		mv.setViewName("axTest/wsChat");
		return mv;
	} //chat	
	
// **************************************************	
// ** Spring Security Test **************************
	
// ** ~1.xml
// 1) 403 오류 화면 처리
	@RequestMapping(value = "/accessError")
	public ModelAndView accessError(ModelAndView mv) {
		mv.setViewName("errorPage/exception_403");
		return mv;
	} //accessError
	
// ** ..~2.xml ~ 4.xml	
// 2) Custom LoginForm 
// view 를 지정하지 않은경우 요청명.jsp 를 찾는다
// 단, 폴더 위치를 지정하려면 요청명도 동일한 규칙으로 할 수 있다.
// 아래의 경우에는  ~/views/ssLoginf.jsp 를 찾게됨.
// => "message" 사용하지 않는경우 return 값은 void, 매개변수없음 도 가능함.
	@RequestMapping(value = "/ssLoginf")
	public ModelAndView ssLoginf(ModelAndView mv) {
		mv.addObject("message", "** Spring Security Login Test **");
		return mv;
	} // ssLoginf	
	
// 3) Admin LoginSuccess 후 	
	@RequestMapping(value = "/adminf")
	public ModelAndView adminf(ModelAndView mv) {
		mv.setViewName("securityJsp/admin");
		return mv;
	} // adminf	
	
// 4) SSLogoutf => post 방식으로 처리하기위해 ssLogoutForm 사용 Test	
	@RequestMapping(value = "/sslogoutf")
	public ModelAndView sslogoutf(ModelAndView mv) {
		mv.setViewName("securityJsp/ssLogoutForm");
		return mv;
	} // sslogoutf		
	
// ** ..5.xml
// 5) LoginSuccess
	@RequestMapping(value = "/authSuccess")
	public ModelAndView authSuccess(ModelAndView mv) {
		mv.setViewName("securityJsp/authSuccessF");
		return mv;
	} // authSuccess
	
// 6) ssDetail
// ** 로그인한 user의 detail 정보 사용
// => 매개변수로 Principal 객체 접근
//    import java.security.Principal;
// => Principal 객체   
//    CustomUserDetailsService 의 loadUserByUsername() 메서드에서
//    반환된 CustomUser 객체를 의미
	
	//@Secured("ROLE_ADMIN")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	//@PreAuthorize("isAuthenticated()")
	//@PreAuthorize("principal.username == #vo.id")
	// => 로그인 User 가 자신의 정보에 접근한 경우에만 처리하고 싶을떄 유용
	//    authSuccessF.jsp 의 요청 url을 로그인과 다른 id로 변경 후 Test 
	//    <a href="ssdetail?id=apple">ssMyInfo</a> -> 그러나 위 @ 대부분 그냥 실행됨 (확인필요함)
	@RequestMapping(value = "/ssdetail")
	public ModelAndView ssdetail(ModelAndView mv, Principal principal) {
		System.out.println("** ssdetail principal.getName() => "+principal.getName());		
		mv.setViewName("securityJsp/ssDetail");
		return mv;
	} // ssdetail	
	
	
} //class
