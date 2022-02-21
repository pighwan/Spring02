package com.ncs.green;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MemberService;
import vo.MemberVO;
import vo.PageVO;

@Controller
public class MemberController {
	
	@Autowired  // -> MemberService 는 반드시 생성되어있어야 함
	MemberService service ;
	@Autowired
	PasswordEncoder passwordEncoder ;
	// PasswordEncoder interface 구현 클래스
	// => Pbkdf2PasswordEncoder, BCryptPasswordEncoder, 
	//    SCryptPasswordEncoder, StandardPasswordEncoder, 
	//    NoOpPasswordEncoder
	// => 대표적인 BCryptPasswordEncoder root-context.xml 또는 servlet-context.xml 에 bean 설정
	
	
	// ** Member Check List ******************************
	@RequestMapping(value = "/mchecklist")
	public ModelAndView mchecklist(ModelAndView mv, MemberVO vo) {
		
		// 1) Check_Box 처리
		// String[] check = request.getParameterValues("check");
		// => vo 에 배열 Type의 check 컬럼을 추가하면 편리
		
		// 2) Service 실행
		// => 선택하지 않은경우 : selectList() 
		// => 선택을 한 경우 : 조건별 검색 checkList(vo) -> 추가
		List<MemberVO> list = null;
		
		//if ( vo.getCheck() != null && vo.getCheck().length > 0 ) {...}
		// => 배열Type의 경우 선택하지 않으면 check=null 이므로 길이 비교 필요없음. 
		if ( vo.getCheck() != null ) list = service.checkList(vo) ;
		else list = service.selectList();
		
		// => Mapper 는 null 을 return 하지 않으므로 길이로 확인 
		if ( list != null && list.size()>0 ) mv.addObject("banana", list);
		else mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다 ~~");
		
		mv.setViewName("member/mCheckList");
		return mv;
	} //mchecklist
	
	
	// ** Ajax MemberList ******************************
	@RequestMapping(value = "/axmlist")
	public ModelAndView axmlist(ModelAndView mv) {
		List<MemberVO> list = service.selectList();
		if (list != null) mv.addObject("banana", list);
		else mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다 ~~");
		mv.setViewName("axTest/axMemberList");
		return mv;
	} //axmlist
	
	// ** Image Download
	@RequestMapping(value = "/dnload")
	public ModelAndView dnload(HttpServletRequest request, ModelAndView mv,
			@RequestParam("dnfile") String dnfile ) {
			// String dnfile = request.getParameter("dnfile"); 와 동일구문
		
		// ** real 저장위치 확인 -> 해당화일 선택 -> response 처리
		// 1) real 저장위치 확인
		// => 개발중인지, 배포후인지 확인
		String realPath = request.getRealPath("/"); //deprecated Method
		String fileName = dnfile.substring(dnfile.lastIndexOf("/")+1);
		if (realPath.contains(".eclipse."))
			 realPath = "D:/MTest/MyWork/Spring02/src/main/webapp/resources/uploadImage/"
					 	+ fileName ;
		else realPath += "resources\\uploadImage\\" + fileName;
		File file = new File(realPath) ;
		
		mv.addObject("downloadFile", file);
		mv.setViewName("download");
		// ** 일반적인 경우 ~/views/download.jsp 를 찾음, 그러나 이 경우에는 아님
		// => servlet-context.xml 에 설정하는 view 클래스 (DownloadView.java) 의
		// id 와 동일 해야함.
		return mv;
	} //dnload
	// ** 위 addOb.. , setView.., return..  3 구문은 아래처럼 작성도 가능.
	// => return new ModelAndView("download", "downloadFile", file);
	// => 생성자 참고 
	//    public ModelAndView(View view, String modelName, 
	//     				Object modelObject) { 
	//     		this.view = view; addObject(modelName, modelObject); }
	
	// ** Json MemberDelete *******************************
	@RequestMapping(value = "/jsdelete")
	public ModelAndView jsdelete(ModelAndView mv, MemberVO vo) {
		if (service.delete(vo) > 0) 
			 mv.addObject("success", "T");
		else mv.addObject("success", "F");
		mv.setViewName("jsonView");
		return mv;
	} //jsdelete
	
	// ** JSON Login 
	// *** JSON : 제이슨, JavaScript Object Notation
	// 자바스크립트의 객체 표기법으로, 데이터를 전달 할 때 사용하는 표준형식.
	// 속성(key) 과 값(value) 이 하나의 쌍을 이룸
			
	// ** JAVA의 Data 객체 -> JSON 변환하기
	// 1) GSON
	// : 자바 객체의 직렬화/역직렬화를 도와주는 라이브러리 (구글에서 만듦)
	// 즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체
			
	// 2) @ResponseBody (매핑 메서드에 적용)
	// : 메서드의 리턴값이 View 를 통해 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
	// 이때 쓰여지기전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
	// MappingJacksonHttpMessageConverter 를 사용하면 request, response 를 JSON 으로 변환
	// view (~.jsp) 가 아닌 Data 자체를 전달하기위한 용도
	// @JsonIgnore : VO 에 적용하면 변환에서 제외

	// 3) jsonView
	// => Spring 에서 MappingJackson2JsonView를 사용해서
	// ModelAndView를 json 형식으로 반환해 준다.
	// => 방법
	// -> pom dependency추가 , 설정화일 xml 에 bean 등록
	// -> return할 ModelAndView 생성시 View를 "jsonView"로 설정

	// ** Json Login Test	
	// => viewName 을 "jsonView"	로
	// => addObject
//		-> 성공 : loginSuccess = 'T'
//		-> 실패 : loginSuccess = 'F' , 실패 message
	
	@RequestMapping(value = "/jslogin")
	public ModelAndView jslogin(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, MemberVO vo) {
		
		// ** jsonView 사용시 response 의 한글 처리
		response.setContentType("text/html; charset=UTF-8");
		
		// 1) request 처리
		String id = vo.getId();
		String password = vo.getPassword();
		
		// 2) service 처리
		vo = service.selectOne(vo);
		if ( vo != null ) { 
			// ID 는 일치 -> Password 확인
			if ( vo.getPassword().equals(password) ) {
				// Login 성공 -> login 정보 session에 보관, home (새로고침) 
				mv.addObject("loginSuccess", "T");
				request.getSession().setAttribute("loginID", id);
				request.getSession().setAttribute("loginName", vo.getName());
			}else {
				// Password 오류 -> 재로그인 유도 (loginForm 으로)
				mv.addObject("loginSuccess", "F");
				mv.addObject("message", "~~ Password 오류,  다시 하세요 ~~");
			}
		}else {	// ID 오류 -> 재로그인 유도 (loginForm 으로)
			mv.addObject("loginSuccess", "F");
			mv.addObject("message", "~~ ID 오류,  다시 하세요 ~~");
		} //else
		
		mv.setViewName("jsonView");
		// => /WEB-INF/views/jsonView.jsp -> 안되도록 servlet-context.xml 설정 
		return mv;
	} //jslogin
	
	// ** Member PageList 2. 
	@RequestMapping(value = "/mcplist")
	// ** ver01 : Criteria PageList
	//public ModelAndView mcplist(ModelAndView mv, Criteria cri, PageMaker pageMaker) {
	// ** ver02 : SearchCriteria PageList
	public ModelAndView mcplist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {	
		// 1) Criteria 처리 
		// => setCurrPage, setRowsPerPage 는 Parameter 로 전달되어,
		//    setCurrPage(..) , setRowsPerPage(..) 는 자동처리됨(스프링에 의해)
		//    -> cri.setCurrPage(Integer.parseInt(request.getParameter("currPage")))
		// => 그러므로 currPage 이용해서 sno, eno 계산만 하면됨
		cri.setSnoEno();
		
		// 2) 서비스처리
		// => List 처리, (totalRowCount 는 PageMaker 처리에서) 
		// ** ver01
		// mv.addObject("banana", service.criPList(cri)); 
		// ** ver02 : searchType, keyword 에 따른 조건검색
		// => service 에 메서드 추가 searchList(cri) , searchRowsCount(cri) 
		mv.addObject("banana", service.searchList(cri));
		
		// 3) PageMaker 처리
		pageMaker.setCri(cri);
		// ** ver01
		//pageMaker.setTotalRowCount(service.totalRowCount()); 
		// ** ver02
		pageMaker.setTotalRowCount(service.searchRowsCount(cri));
		
		mv.addObject("pageMaker", pageMaker);
		mv.setViewName("member/mCriList");
		return mv;
	} //mcplist
	
	// ** Member PageList 1.
	@RequestMapping(value = "/mpagelist")
	public ModelAndView mpagelist(ModelAndView mv, PageVO<MemberVO> pvo) {
		// 1) Paging 준비
		int currPage = 1;
		if (pvo.getCurrPage() > 1) currPage = pvo.getCurrPage();
		else pvo.setCurrPage(currPage) ;
		
		int sno = (currPage-1)*pvo.getRowsPerPage() + 1 ;
		int eno = sno + pvo.getRowsPerPage() - 1 ;
		pvo.setSno(sno);
		pvo.setEno(eno);
		
		// 2) Service 처리
		pvo = service.pageList(pvo) ;
		int totalPageNo = pvo.getTotalRowCount()/pvo.getRowsPerPage();
		// 20/3 -> 6 나머지 2 : 6 page 와 2개 -> 7page
		if ( pvo.getTotalRowCount()%pvo.getRowsPerPage() !=0 )
			totalPageNo +=1;
		
		// 3) View 처리
		// ** view02 : 추가 
		int sPageNo = ((currPage-1)/pvo.getPageNoCount())*pvo.getPageNoCount() + 1 ;
		int ePageNo = sPageNo + pvo.getPageNoCount() - 1 ;
		if ( ePageNo > totalPageNo ) ePageNo = totalPageNo ;
		
		mv.addObject("sPageNo", sPageNo);
		mv.addObject("ePageNo", ePageNo);
		mv.addObject("pageNoCount", pvo.getPageNoCount());
		
		// ** view01
		mv.addObject("currPage",currPage);
		mv.addObject("totalPageNo",totalPageNo);
		mv.addObject("banana", pvo.getList());
		
		mv.setViewName("member/pageMList");
		return mv;
	}//mpagelist
	
	@RequestMapping(value = "/mlist")
	public ModelAndView mlist(ModelAndView mv)
			throws ServletException, IOException {
		// MemberList
		
		List<MemberVO> list = new ArrayList<MemberVO>();
    	list = service.selectList();
    	if ( list!=null ) {
    		mv.addObject("banana", list);
    	}else {
    		mv.addObject("message", "~~ 출력 자료가 없습니다 ~~");
    	}
    	
    	mv.setViewName("member/memberList");
		return mv ;
	}
	
	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, ModelAndView mv, MemberVO vo)
			throws ServletException, IOException {
		// Detail 요청경로 
		// 1) MyInfo -> id 를 session 에서 get
		// 2) admin 모드 -> id parameter 로 전달됨 
		// => parameter 에 id 가 없으면 session 에서 get
		HttpSession session = request.getSession(false);
		if (vo.getId()==null || vo.getId().length()<1) {
			
			if ( session!=null && session.getAttribute("loginID")!=null ) {
				vo.setId((String)session.getAttribute("loginID"));
			}
		}
		String uri = "member/memberDetail";
		vo=service.selectOne(vo);
		if (vo != null) {
			if ( "U".equals(request.getParameter("jcode")) ) { 
				// "jcode" 가 없는경우 NullPointerException 발생
				// => 예방차원에서 위처럼 비교
				uri="member/updateForm";
				// ** PasswordEncoder 사용 때문에 
				//    session에 보관해 놓은 raw_password 를 수정할수 있도록 vo에 set 해줌.
				vo.setPassword((String)session.getAttribute("loginPW"));
			}
			mv.addObject("apple", vo);
		}else {
			mv.addObject("message","~~ "+request.getParameter("id")+"님의 자료는 존재하지 않습니다 ~~"); 
		}	
		mv.setViewName(uri);
		return mv;
	}
	
	@RequestMapping(value = "/loginf")
	public ModelAndView loginf(ModelAndView mv) {
		mv.setViewName("member/loginForm");
		return mv;
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, ModelAndView mv, MemberVO vo)
			throws ServletException, IOException {
		// 1) request 처리
		String id = vo.getId();
		String password = vo.getPassword();
		String uri = "member/loginForm";
		
		// 2) service 처리
		//vo.setId(id); -> 필요없게됨.
		vo = service.selectOne(vo);
		if ( vo != null ) { 
			// ID 는 일치 -> Password 확인
			// ver01 -> if ( vo.getPassword().equals(password) ) {
			
			// ver02 -> BCryptPasswordEncoder 적용
			// => passwordEncoder.matches(rawData, digest) -> (입력값, DB에보관된값_digest) 
			if ( passwordEncoder.matches(password, vo.getPassword()) ) {
				// Login 성공 -> login 정보 session에 보관, home
				request.getSession().setAttribute("loginID", id);
				request.getSession().setAttribute("loginName", vo.getName());
				
				// BCryptPasswordEncoder 로 암호화되면 복호화가 불가능함.
				// => password 수정 을 별도로 처리해야 함.
				// => 그러나 기존의 update  Code 를 활용하여 updateForm.jsp 에서 수정을 위해
				//    User가 입력한 raw_password 를 보관함. 
				// => 이 session에 보관한 값은 detail 에서 "U" 요청시 사용함. 
				request.getSession().setAttribute("loginPW", password);
				uri="redirect:home" ;
			}else {
				// Password 오류
				mv.addObject("message", "~~ Password 오류,  다시 하세요 ~~");
			}
		}else {	// ID 오류
			mv.addObject("message", "~~ ID 오류,  다시 하세요 ~~");
		} //else
		
		mv.setViewName(uri);
		return mv;
	} //doUser 

	// ** redirect 시 message 처리하기
	// => RedirectAttributes
	//   - addFlashAttribute("message",message)
	//     -> 세션을 통해 url에 표시되지 않게 보낼 수 있으면서, 일회성 임.
	//        session 에 보관되므로 url에 붙지않기 때문에 깨끗하고 f5(새로고침)에 영향을 주지않음 
	//        즉, home 에서 다시 request 처리하지않아도 됨
	//     -> 비교 : mv.addObject("message", message) 하고, 
	//              redirect 하면 message 내용이 url 에 붙어 전달됨
	//   - addAttribute("message",message)
	//     -> url 에 붙어전달됨	
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr)
			throws ServletException, IOException {
		
		// ** session 인스턴스 정의 후 삭제하기
    	// => 매개변수: 없거나, true, false
    	// => false : session 이 없을때 null 을 return;
		HttpSession session = request.getSession(false);
    	if (session!=null) session.invalidate();
    	// mv.addObject("message", "~~ 로그아웃 되었습니다 ~~");  -> forward
    	rttr.addFlashAttribute("message", "~~ 로그아웃 되었습니다 ~~"); // redirect
    	
    	//mv.setViewName("home");  // home.jsp 로 forward
    	mv.setViewName("redirect:home");  // home 요청을  redirect
		return mv;
	} //doUser 
	
	// ** Join ****************************
	@RequestMapping(value = "/joinf")
	public ModelAndView joinf(ModelAndView mv) {
		mv.setViewName("member/joinForm");
		return mv;
	}
	@RequestMapping(value = "/join")
	public ModelAndView join(HttpServletRequest request, ModelAndView mv, MemberVO vo) 
					 	throws IOException {
		// 1. 요청처리 
		// => parameter : 매개변수로 ...
		// => 한글: web.xml 에서 <Filter> 로 ...
		
		// ** Uploadfile (Image) 처리
		// => MultipartFile 타입의 uploadfilef 의 정보에서 
		//    upload된 image 와 화일명을 get 처리,
		// => upload된 image 를 서버의 정해진 폴더 (물리적위치)에 저장 하고,  -> file1
		// => 이 위치에 대한 정보를 table에 저장 (vo의 UploadFile 에 set) -> file2
		// ** image 화일명 중복시 : 나중 이미지로 update 됨.  
		
		// ** Image 물리적위치 에 저장
		// 1) 현재 웹어플리케이션의 실행 위치 확인 : 
		// => eslipse 개발환경 (배포전)
		//    D:\MTest\MyWork\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring03\
		// => 톰캣서버에 배포 후 : 서버내에서의 위치가 됨
		//    D:\MTest\IDESet\apache-tomcat-9.0.41\webapps\Spring02\
		String realPath = request.getRealPath("/"); // deprecated Method
		System.out.println("** realPath => "+realPath);
		
		// 2) 위 의 값을 이용해서 실제저장위치 확인 
		// => 개발중인지, 배포했는지 에 따라 결정
		if (realPath.contains(".eclipse."))
			 realPath = "D:/MTest/MyWork/Spring02/src/main/webapp/resources/uploadImage/";
		// realPath = "D:/MTest/MyWork/Spring02/src/main/webapp/resources/"+vo.getId()+"/";
		else realPath += "resources\\uploadImage\\";
		
		// ** 폴더 만들기 (File 클래스활용)
		// => 위의 저장경로에 폴더가 없는 경우 (uploadImage가 없는경우)  만들어 준다
		File f1 = new File(realPath);
		if ( !f1.exists() ) f1.mkdir();
		// realPath 디렉터리가 존재하는지 검사 (uploadImage 폴더 존재 확인)
		// => 존재하지 않으면 디렉토리 생성
		
		// ** 기본 이미지 지정하기 
		String file1, file2="resources/uploadImage/basicman1.jpg";
		
		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		//    -> String getOriginalFilename(), 
		//    -> void transferTo(File destFile),
		//    -> boolean isEmpty()
		
		MultipartFile uploadfilef = vo.getUploadfilef();
		if ( uploadfilef !=null && !uploadfilef.isEmpty() ) {
			// Image 를 선택했음 -> Image 처리 (realPath+화일명)
			// 1) 물리적 위치에 Image 저장 
			file1=realPath + uploadfilef.getOriginalFilename(); //  전송된File명 추출 & 연결
			uploadfilef.transferTo(new File(file1)); // real 위치에 전송된 File 붙여넣기
			// 2) Table 저장위한 경로 
			file2 = "resources/uploadImage/"+ uploadfilef.getOriginalFilename();
		}
		vo.setUploadfile(file2);
		
		// ** Password 암호화
		// => BCryptPasswordEncoder 적용
		//    encode(rawData) -> digest 생성 & vo 에 set  
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		
		// 2. Service 처리
		String uri = "member/loginForm";  // 성공시 로그인폼으로
		
		// *** Transaction_AOP Test 
		/*	1. dependency 확인
		<!-- AspectJ -->
		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjrt</artifactId>
			<version>${org.aspectj-version}</version>
		</dependency>
		<!-- AspectJ Weaver -->
		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>${org.aspectj-version}</version>
		</dependency>
		
		*	2. servlet-context.xml AOP 설정
		*
		* 	3. Rollback Test
		  	3.1) Aop xml 적용전 => insert1 은 입력되고, insert2 에서  500_Dupl..Key  오류 발생
		  	3.2) Aop xml 적용후 => insert2 에서 오류발생시 모두 Rollback 되어 insert1, insert2 모두 입력 안됨
		*/
		// 3.1) Transaction 적용전 : 동일자료 2번 insert
		//    => 첫번째는 입력완료 되고, 두번째자료 입력시 Key중복 오류발생 (500 발생)
		// 3.2) Transaction 적용후 : 동일자료 2번 insert
		//    => 첫번째는 입력완료 되고, 두번째 자료입력시 Key중복 오류발생 하지만,
		//       rollback 되어 둘다 입력 안됨
		//service.insert(vo) ;
		
		int cnt = service.insert(vo) ;
		if ( cnt > 0 ) {
			 // insert 성공
			 mv.addObject("message", "~~ 회원가입 완료!!, 로그인 후 이용하세요 ~~");
		 }else { 
			 // insert 실패
			 mv.addObject("message", "~~ 회원가입 실패!!, 다시 하세요 ~~");
			 uri="member/joinForm";
		 }
		mv.setViewName(uri);
		return mv;
	} //join
	@RequestMapping(value = "/idcheck")
	public ModelAndView idcheck(ModelAndView mv, MemberVO vo) {
		// 입력한 newID 보관
		mv.addObject("newID", vo.getId());
		if ( service.selectOne(vo) != null ) {
			mv.addObject("idUse", "F"); // 사용불가
		}else {
			mv.addObject("idUse", "T"); // 사용가능
		}
		mv.setViewName("member/idDupCheck"); 
		return mv;
	} //idcheck
	// ************************************
	
	// ** Member Update **
	@RequestMapping(value = "/mupdate")
	public ModelAndView mupdate(HttpServletRequest request, 
			ModelAndView mv, MemberVO vo, RedirectAttributes rttr) throws IOException {
		String uri = null; 
		// ** Service 
		// => 성공후 
		//		-> 수정된 정보확인 : memberList.jsp (출력가능하도록 요청)
		//		-> name 수정한 경우도 있을수있으므로 session 에 보관중인 loginName도 변경
		// => 실패후 -> 다시 수정하기 : 수정가능한 폼 출력
		
		// ** ImageUpload 추가
		// => Image 수정여부 확인
		// 	  -> 수정하지않은경우 : vo에 전달된 uploadfile 값을 사용 
		// 	  -> 수정시에만 처리
		MultipartFile uploadfilef = vo.getUploadfilef();
		String file1, file2;
		if ( uploadfilef != null && !uploadfilef.isEmpty() ) {
			// 1) 저장폴더지정
			String realPath = request.getRealPath("/");
			if (realPath.contains(".eclipse."))
				 realPath = "D:/MTest/MyWork/Spring02/src/main/webapp/resources/uploadImage/";
			else realPath += "resources\\uploadImage\\"; // 배포환경
			File f1 = new File(realPath);
			if ( !f1.exists() ) f1.mkdir();
			// 2) 전송된 file 처리
			// 2.1) Image 붙여넣기
			file1 = realPath + uploadfilef.getOriginalFilename();
			uploadfilef.transferTo(new File(file1));
			// 2.2) Table 저장위한 값 set
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
			vo.setUploadfile(file2);
		}
		
		// ** Password 암호화
		// => BCryptPasswordEncoder 적용
		//    encode(rawData) -> digest 생성 & vo 에 set  
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		
		if ( service.update(vo) > 0 ) {
			 // update 성공
			 rttr.addFlashAttribute("message", "~~ 회원정보 수정 완료 !!!  ~~") ; 
			 // => redirect시 message 전달가능
			 
			 request.getSession().setAttribute("loginName", vo.getName());
			 uri = "redirect:mlist";  // redirect 로 처리함 (재요청처리)
		 }else { 
			 // update 실패 : 수정가능한 폼 출력할수있도록 요청 
			 rttr.addFlashAttribute("message", "~~ 회원정보 수정 실패!!, 다시 하세요 ~~");
			 uri="redirect:mdetail?jcode=U&id="+vo.getId();
		 }
		
		mv.setViewName(uri); 
		return mv;
	} //mupdate
	
	// ** Member Delete : 회원탈퇴
	// => member delete, session 무효화
	// => 삭제 성공 -> home,
	//    삭제 실패 -> memberDetail 
	@RequestMapping(value = "/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, 
			ModelAndView mv, MemberVO vo, RedirectAttributes rttr) {
		String uri = "home"; 
		String id = null;
		
		HttpSession session = request.getSession(false);
		if ( session!=null && session.getAttribute("loginID")!=null ) {
			id =(String)session.getAttribute("loginID");
			// ** 삭제 가능
			// => 관리자 작업인경우 : 이미 vo에 삭제할 ID 가 set 되어있음
			// => 관지자 작업아닌경우: session 에서 get한 ID 를 vo에 set 
			if (!id.equals("admin")) vo.setId(id);
			
			if ( service.delete(vo)>0 ) {
				// 삭제 성공 : 성공 message, home, session무효화
				if (!id.equals("admin")) {
					session.invalidate();
					mv.addObject("message", "~~ 탈퇴 성공 : 1개월후 재가입 가능합니다. ~~") ;
				}else {
					uri = "redirect:mlist"; // 관리자 작업인 경우 
					rttr.addFlashAttribute("message", "~~ member 삭제 성공 !!! ~~");
				}
			}else {
				// 삭제 오류 -> mdetail -> memberDetail.jsp 
				uri="redirect:mdetail";
				rttr.addFlashAttribute("message", "~~ 탈퇴 오류 : 다시 하세요. ~~");
			}
		}else { // session is null -> loginForm.jsp
			uri="member/loginForm" ;
			mv.addObject("message", "~~ 로그인 정보 없음 : 로그인 후 탈퇴 가능 합니다. ~~") ;
		}
		mv.setViewName(uri); 
		return mv;
	} //mdelete
	
} //MemberController
