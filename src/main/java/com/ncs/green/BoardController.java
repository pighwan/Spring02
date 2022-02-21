package com.ncs.green;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import criTest.PageMaker;
import criTest.SearchCriteria;
import lombok.extern.log4j.Log4j;
import service.BoardService;
import vo.BoardVO;
import vo.PageVO;

@Log4j
@Controller
public class BoardController {
	@Autowired
	BoardService service;

	// ** @Log4j Test
	@RequestMapping(value = "/logj")
	public ModelAndView logj(ModelAndView mv) {
		
		log.info("** @Log4j Test => info");
		log.warn("** @Log4j Test => warn");
		log.error("** @Log4j Test => error");
		log.trace("** @Log4j Test => trace");
		
		mv.setViewName("home");
		return mv;
	} //logj
	
	
	// ** Board Check List ******************************
	@RequestMapping(value = "/bchecklist")
	public ModelAndView bchecklist(ModelAndView mv, BoardVO vo) {
		// ** @Log4j Test
		//=> 로깅레벨 단계 준수함 ( log4j.xml 의 아래 logger Tag 의 level 확인)
		//   TRACE > DEBUG > INFO > WARN > ERROR > FATAL(치명적인)
		//	<logger name="com.ncs.green">
		//		<level value="info" />
		//	</logger>
		log.info("** bchecklist => "+vo);
		
		// 1) Check_Box 처리
		// 2) Service 실행
		List<BoardVO> list = null;
		if ( vo.getCheck() != null ) list = service.checkList(vo) ;
		else list = service.selectList();
		
		// => Mapper 는 null 을 return 하지 않으므로 길이로 확인 
		if ( list != null && list.size()>0 ) mv.addObject("banana", list);
		else mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다 ~~");
		
		mv.setViewName("board/bCheckList");
		return mv;
	} //bchecklist
	
	// ** jsonView Board Detail 
	@RequestMapping(value = "/jsbdetail")
	public ModelAndView jsbdetail(HttpServletResponse response, ModelAndView mv, BoardVO vo) {
		// jsonView 사용시 response 의 한글 처리
		response.setContentType("text/html; charset=UTF-8");
		
		vo = service.selectOne(vo);
		if ( vo != null ) mv.addObject("content", vo.getContent());
		else mv.addObject("content", "~~ 글번호에 해당하는 자료가 없습니다. ~~");
		
		mv.setViewName("jsonView");
		return mv;
	} //jsbdetail
	
	// ** Ajax id_BoardList 
	@RequestMapping(value = "/aidblist")
	public ModelAndView aidblist(ModelAndView mv, BoardVO vo) {
		List<BoardVO> list = service.aidBList(vo);
		
		// => Mapper 는 null 을 return 하지 않으므로 길이로 확인 
		if ( list != null && list.size()>0 ) {
			mv.addObject("banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("board/boardList");
		return mv;
	} //aidblist
	
	// ** Ajax BoardList 
	@RequestMapping(value = "/axblist")
	public ModelAndView axblist(ModelAndView mv) {
		List<BoardVO> list = service.selectList();
		// => Mapper 는 null 을 return 하지 않으므로 길이로 확인 
		if (list != null && list.size()>0 ) {
			mv.addObject("banana", list);
		}else {
			mv.addObject("message", "~~ 출력할 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("axTest/axBoardList");
		return mv;
	} //axblist
	// **********************************************************
	
	// ** Member PageList 2. SearchCriteria PageList
	@RequestMapping(value = "/bcplist")
	// ** ver02
	public ModelAndView bcplist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {	
		// 1) Criteria 처리 
		// => setCurrPage, setRowsPerPage 는 Parameter 로 전달되어,
		//    setCurrPage(..) , setRowsPerPage(..) 는 자동처리됨(스프링에 의해)
		//    -> cri.setCurrPage(Integer.parseInt(request.getParameter("currPage")))
		// => 그러므로 currPage 이용해서 sno, eno 계산만 하면됨
		cri.setSnoEno();
		
		// 2) 서비스처리
		// => List 처리, 
		mv.addObject("banana", service.searchList(cri));
		
		// 3) PageMaker 처리
		pageMaker.setCri(cri);
		pageMaker.setTotalRowCount(service.searchRowsCount(cri));
		
		mv.addObject("pageMaker", pageMaker);
		mv.setViewName("board/bCriList");
		return mv;
	} //bcplist
	
	// ** Board PageList 1.
	@RequestMapping(value = "/bpagelist")
	public ModelAndView bpagelist(ModelAndView mv, PageVO<BoardVO> pvo) {
		// 1) Paging 준비
		// => 한 Page당 출력할 Row 갯수 : PageVO 에 지정
		// => 요청 Page 확인 : currPage ( Parameter )
		// => sno , eno 계산후 List 읽어오기
		// => totalRowCount : 전체Page수 계산
		int currPage = 1;
		if (pvo.getCurrPage() > 1) currPage = pvo.getCurrPage();
		else pvo.setCurrPage(currPage) ;
		
		int sno = (currPage-1)*pvo.getRowsPerPage() + 1 ;
		int eno = sno + pvo.getRowsPerPage() - 1 ;
		pvo.setSno(sno);
		pvo.setEno(eno);
		
		// 2) Service 처리
		// => List 읽어오기, 전체Row수(totalRowCount) 
		// => 전체 PageNo 계산하기
		pvo = service.pageList(pvo) ;
		int totalPageNo = pvo.getTotalRowCount()/pvo.getRowsPerPage();
		// 20/3 -> 6 나머지 2 : 6 page 와 2개 -> 7page
		if ( pvo.getTotalRowCount()%pvo.getRowsPerPage() !=0 )
			totalPageNo +=1;
		
		// 3) View 처리
		// ** view02 
		// => PageBlock 기능 추가 : sPageNo, ePageNo
		// => 이를 위해 currPage, pageNoCount
		// => 유형 1) currPage 가 항상 중앙에 위치하도록 할때 (ex. 쿠팡)
		// int sPageNo = currPage - (pageNoCount/2) ;      
		// int ePageNo = currPage + (pageNoCount/2) ;
		
		// => 유형 2) 11번가의 상품List, Naver 카페글 유형
		// 예를들어 currPage=3 이고 pageNoCount 가 3 이면 1,2,3 page까지 출력 되어야 하므로
		// 아래 처럼 currPage-1 을 pageNoCount 으로 나눈후 다시 곱하고 +1
		// currPage=11 -> 10,11,12, => (11-1)/3 * 3 +1 = 10
		// 연습 ( pageNoCount=5 )
		// -> currPage=11 인경우 : 11,12,13,14,15 -> ((11-1)/5)*5 +1 : 11
		// -> currPage=7 인경우 : 6,7,8,9,10 -> ((7-1)/5)*5 +1 : 6
		int sPageNo = ((currPage-1)/pvo.getPageNoCount())*pvo.getPageNoCount() + 1 ;
		int ePageNo = sPageNo + pvo.getPageNoCount() - 1 ;
		// 계산으로 얻어진 ePageNo가 실제 LastPage 인 totalPageNo 보다 크면 수정 필요.
		if ( ePageNo > totalPageNo ) ePageNo = totalPageNo ;
		mv.addObject("sPageNo", sPageNo);
		mv.addObject("ePageNo", ePageNo);
		mv.addObject("pageNoCount", pvo.getPageNoCount());
		
		// ** view01
		mv.addObject("currPage",currPage);
		mv.addObject("totalPageNo",totalPageNo);
		mv.addObject("banana", pvo.getList());
		
		mv.setViewName("board/pageBList");
		return mv;
	}//bpagelist
	
	// ** Reply Insert **
	@RequestMapping(value = "/rinsertf")
	public ModelAndView rinsertf(ModelAndView mv, BoardVO vo) {
		// => vo 에는 전달된 부모글의 root, step, indent 가 담겨있음 
		// => 매핑메서드의 인자로 정의된 vo 는 request.setAttribute 와 동일 scope
		//    단, 클래스명의 첫글자를 소문자로 ...  ${boardVO.root}
		mv.setViewName("board/rinsertForm");
		return mv;
	} //rinsertf
	
	@RequestMapping(value = "/rinsert")
	public ModelAndView rinsert(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		// ** 답글 입력 Service
		// => 성공 : blist
		// => 실패 : 재입력 유도 (rinsertForm) 
		
		// => 전달된 vo 에 담겨있는 value
		//		: id, title, content, 
		//		  + 추가적으로 필요한 value : 부모글의 root, step, indent
		//    root : 부모글의 root와 동일
		//    step : 부모글의 step+1 
		//   		(기존 답글의 step 값이 현재 계산된 이값보다 같거나 큰값들은 +1씩 모두증가 : sql 에서 처리) 
		//    indent: 부모글의 indent+1 
		// => 이를 위해 boardDetail 에서 요청시 퀴리스트링으로 전달 -> rinsertf  
		// => 부모글의 root, step, indent 를 replyForm 에서 hidden으로 처리한 후 
		//    전달된 vo 에는 이 값이 담겨있으므로 step, indent만 1씩 증가해주면 됨. 
		
		String uri="redirect:blist";
		vo.setStep(vo.getStep()+1);
		vo.setIndent(vo.getIndent()+1);
		
		if (service.rinsert(vo) > 0) {
			// 답글 입력 성공 -> blist
			rttr.addFlashAttribute("message","~~ 답글 입력 성공 ~~");
		}else {
			// 답글 입력 실패 -> 재입력 유도 (rinsertForm)
			// => step, indent 값을 부모의 값으로 변경한 후, forward
			vo.setStep(vo.getStep()-1);
			vo.setIndent(vo.getIndent()-1);
			mv.addObject("message","~~ 답글 입력 실패 !! 다시하세요 ~~");
			uri="board/rinsertForm";
		}
		
		mv.setViewName(uri);
		return mv;
	} //rinsert
	
	// ** Board CRUD *****
	@RequestMapping(value = "/blist")
	public ModelAndView blist(ModelAndView mv) {
		
		List<BoardVO> list = new ArrayList<BoardVO>();
    	list = service.selectList();
    	
    	// => Mapper 는 null 을 return 하지 않으므로 길이로 확인 
    	if ( list!=null && list.size()>0 ) mv.addObject("banana", list);
    	else mv.addObject("message", "~~ 출력 자료가 없습니다 ~~");
		
    	mv.setViewName("board/boardList");
		return mv;
	} //blist
	
	@RequestMapping(value = "/bdetail")
	public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		
		String uri = "board/boardDetail";
		
		// ** Service 처리
    	// => 조회수 증가 추가하기 ( 조회수 증가의 조건 )
    	//    글보는이(loginID)와 글쓴이가 다를때 && jcode!="U" -> countUp 메서드
		
		vo = service.selectOne(vo);
    	if ( vo!=null ) {
    		// 조회수 증가 추가
    		String loginID = (String)request.getSession().getAttribute("loginID") ;
    		if ( !vo.getId().equals(loginID) &&    
    			 !"U".equals(request.getParameter("jcode")) ) {
    			// 조회수 증가
    			if ( service.countUp(vo) > 0 )
    					vo.setCnt(vo.getCnt()+1) ;
    		} //if
    		
    		mv.addObject("apple", vo);
    		// 글 수정인지 확인
    		if ( "U".equals(request.getParameter("jcode")) ) 
    			uri = "board/bupdateForm";
    	}else {
    		mv.addObject("message", "~~ 글번호에 해당하는 자료가 없습니다 ~~");
    	}
		
		mv.setViewName(uri);
		return mv;
	} //bdetail
	
	@RequestMapping(value = "/binsertf")
	public ModelAndView binsertf(ModelAndView mv) {
		mv.setViewName("board/binsertForm");
		return mv;
	} //binsertf
	
	@RequestMapping(value = "/binsert")
	public ModelAndView binsert(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		
		String uri = "redirect:blist";
		if ( service.insert(vo) > 0 ) { 
    		// 글등록 성공 -> blist , redirect
    		rttr.addFlashAttribute("message", "~~ 새글 등록 완료 !!! ~~");
    	}else {
    		mv.addObject("message", "~~ 새글 등록 실패 !!! ~~");
    		uri = "board/binsertForm";
    	}
		mv.setViewName(uri);
		return mv;
	} //binsert
	
	@RequestMapping(value = "/bupdate")
	public ModelAndView bupdate(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		
		String uri = "redirect:blist";
		if ( service.update(vo) > 0 ) { 
    		// 글수정 성공 -> blist : redirect
    		rttr.addFlashAttribute("message", "~~ 글수정 성공 !!! ~~");
    	}else {
    		rttr.addFlashAttribute("message", "~~ 글수정 실패 !!! 다시 하세요 ~~");
    		uri = "redirect:bdetail?jcode=U&seq="+vo.getSeq();
    	}
		mv.setViewName(uri);
		return mv;
	} //bupdate
	
	@RequestMapping(value = "/bdelete")
	public ModelAndView bdelete(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
		
		String uri = "redirect:blist";
		if ( service.delete(vo) > 0 ) { 
    		// 글삭제 성공 -> blist  : redirect
			rttr.addFlashAttribute("message", "~~ 글삭제 성공 !!! ~~");
    	}else {
    		rttr.addFlashAttribute("message", "~~ 글삭제 실패 !!! ~~");
    		uri = "redirect:bdetail?seq="+vo.getSeq();
    	}
		mv.setViewName(uri);
		return mv;
	} //bdelete	

} //class
