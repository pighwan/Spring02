package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.SearchCriteria;
import mapperInterface.BoardMapper;
import util.BoardDAO;
import vo.BoardVO;
import vo.PageVO;

//** interface 자동완성 
//=> Alt + Shift + T  
//=> 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper dao;
	
	// ** Board Check List
	@Override
	public List<BoardVO> checkList(BoardVO vo) {
		return dao.checkList(vo);
	}
	
	// ** Ajax id_BoardList
	@Override
	public List<BoardVO> aidBList(BoardVO vo) {
		return dao.aidBList(vo);
	}
	
	// ** PageList 2. SearchCriteria PageList
	@Override
	public int searchRowsCount(SearchCriteria cri) {
		return dao.searchRowsCount(cri);
	} 
	@Override
	public List<BoardVO> searchList(SearchCriteria cri) {
		return dao.searchList(cri) ;
	}
	// ** PageList1.
	@Override
	public PageVO<BoardVO> pageList(PageVO<BoardVO> pvo) {
		pvo.setTotalRowCount(dao.totalRowCount());
		pvo.setList(dao.pageList(pvo));
		return pvo;
	} //pageList
	
	@Override
	public List<BoardVO> selectList() {
		return dao.selectList();
	} //selectList
	@Override
	public BoardVO selectOne(BoardVO vo) {
		return dao.selectOne(vo);
	} //selectOne

	@Override
	public int insert(BoardVO vo) {
		return dao.insert(vo) ;
	} //insert
	@Override
	public int update(BoardVO vo) {
		return dao.update(vo) ;
	} //update
	@Override
	public int delete(BoardVO vo) {
		return dao.delete(vo) ;
	} //delete
	
	// ** 조회수증가
	@Override
	public int countUp(BoardVO vo) {
		return dao.countUp(vo) ;
	} //countUp
	
	// ** 답글입력
	@Override
	public int rinsert(BoardVO vo) {
		return dao.rinsert(vo);
	}

} //class
