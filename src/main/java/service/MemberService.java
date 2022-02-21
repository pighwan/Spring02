package service;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.MemberVO;
import vo.PageVO;

public interface MemberService {
	
	// ** Member Check List
	List<MemberVO> checkList(MemberVO vo);
	
	// ** PageList 2.1) Criteria PageList
	int totalRowCount();
	List<MemberVO> criPList(Criteria cri);
	// ** PageList 2.2) SearchCriteria PageList
	int searchRowsCount(SearchCriteria cri);
	List<MemberVO> searchList(SearchCriteria cri);
	
	// ** PageList1.
	PageVO<MemberVO> pageList(PageVO<MemberVO> pvo) ;
	List<MemberVO> selectList(); //selectList
	MemberVO selectOne(MemberVO vo); //selectOne
	int insert(MemberVO vo); //insert
	int update(MemberVO vo); //update
	int delete(MemberVO vo); //delete

}