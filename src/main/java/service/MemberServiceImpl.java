package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.Criteria;
import criTest.SearchCriteria;
import mapperInterface.MemberMapper;
import vo.MemberVO;
import vo.PageVO;

//** interface 자동완성 
//=> Alt + Shift + T  
//=> 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

// ** Interface 를 이용한 Mapper 접근 
// => DAO 를 사용하지않고 interface MemberMapper 를 통해 MemberMapper 에 접근
// => 주의) 이전 DAO의 pageList 메서드 에서 totalRowCount() 와 pageList(pvo) 
//    처리하고 pvo 를 return 했지만, interface의 메서드는 mapper 의 id 
//    와 같은 이름으로 1:1 매칭되기 때문에 이부분을 ServiceImpl 에서 처리해야함
//    -> 아래 PageVO<MemberVO> pageList(PageVO<MemberVO> pvo) 확인 

@Service
public class MemberServiceImpl implements MemberService {
	
	//@Setter(onMethod = @Autowired) 
	// => onMethod 속성은 생성되는 setter에 @AutoWired 어노테이션을 추가해줌.
	@Autowired
	MemberMapper dao;
	// MemberMapper 의 인스턴스를 스프링이 생성 해주고 이를 주입받아 실행함
	// 단, 설정화일에 <mybatis-spring:scan base-package="mapperInterface"/> 추가해야함
	
	// ** Member Check List
	@Override
	public List<MemberVO> checkList(MemberVO vo) {
		return dao.checkList(vo);
	}
	
	// ** PageList 2.1) Criteria PageList
	@Override
	public int totalRowCount() {
		return dao.totalRowCount();
	}
	@Override
	public List<MemberVO> criPList(Criteria cri) {
		return dao.criPList(cri);
	}
	
	// ** PageList 2.2) SearchCriteria PageList
	@Override
	public int searchRowsCount(SearchCriteria cri) {
		return dao.searchRowsCount(cri);
	}
	@Override
	public List<MemberVO> searchList(SearchCriteria cri) {
		return dao.searchList(cri);
	}
	
	// ** PageList1.
	@Override
	public PageVO<MemberVO> pageList(PageVO<MemberVO> pvo) {
		pvo.setTotalRowCount(dao.totalRowCount());
		pvo.setList(dao.pageList(pvo));
		return pvo ;
	}
	@Override
	public List<MemberVO> selectList() {
		return dao.selectList();
	} //selectList
	@Override
	public MemberVO selectOne(MemberVO vo) {
		return dao.selectOne(vo);
	} //selectOne

	@Override
	public int insert(MemberVO vo) {
		return dao.insert(vo) ;
	} //insert
	@Override
	public int update(MemberVO vo) {
		return dao.update(vo) ;
	} //update
	@Override
	public int delete(MemberVO vo) {
		return dao.delete(vo) ;
	} //delete
	
} //class
