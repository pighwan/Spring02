package service;

import java.util.List;

import criTest.SearchCriteria;
import vo.BoardVO;
import vo.PageVO;

public interface BoardService {
	
	// ** Board Check List
	List<BoardVO> checkList(BoardVO vo);
	
	// ** Ajax id_BoardList
	List<BoardVO> aidBList(BoardVO vo);
	
	// ** PageList 2. SearchCriteria PageList
	int searchRowsCount(SearchCriteria cri) ;
	List<BoardVO> searchList(SearchCriteria cri) ;
	// ** PageList1.
	PageVO<BoardVO> pageList(PageVO<BoardVO> pvo); // pageList 1 
	// **  CRUD
	List<BoardVO> selectList(); //selectList
	BoardVO selectOne(BoardVO vo); //selectOne
	int insert(BoardVO vo); //insert
	int update(BoardVO vo); //update
	int delete(BoardVO vo); //delete
	// ** 조회수증가
	int countUp(BoardVO vo); //countUp
	// ** 답글입력
	int rinsert(BoardVO vo);

}