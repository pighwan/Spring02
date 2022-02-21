package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import criTest.SearchCriteria;
import vo.BoardVO;
import vo.MemberVO;
import vo.PageVO;

//** DAO (Data Access Object)
//=> CRUD 구현 
// C: create -> insert
// R: read   -> selectList, selectOne
// U: update -> update
// D: delete -> delete

//=> 전역변수 정의 , 메서드 작성
@Repository
public class BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	private static final String NS="green.mapper.BoardMapper.";
	
	// ** PageList 2. SearchCriteria PageList
	public int searchRowsCount(SearchCriteria cri) {
		return  sqlSession.selectOne(NS+"searchRowsCount", cri);
	}
	public List<BoardVO> searchList(SearchCriteria cri) {
		return sqlSession.selectList(NS+"searchList", cri);
	}
	
	// ** PageList 1.
	public PageVO<BoardVO> pageList(PageVO<BoardVO> pvo) {
		// ** 전체Row수(totalRowCount)
		pvo.setTotalRowCount(sqlSession.selectOne(NS+"totalRowCount")); 
		// ** List 읽기
		pvo.setList(sqlSession.selectList(NS+"pageList",pvo));
		return pvo;
	} //pageList() 
	
	// ** 답글등록
	public int rinsert(BoardVO vo) {
		return sqlSession.insert(NS+"replyInsert",vo);
	} //replyInsert
	
	// ** selectList
	public List<BoardVO> selectList() {
		return sqlSession.selectList(NS+"selectList");
	} //selectList
	
	// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		return sqlSession.selectOne(NS+"selectOne", vo);
	} //selectOne 
	
	// ** 조회수 증가
	public int countUp(BoardVO vo) {
		return sqlSession.update(NS+"countUp",vo);
	} //countUp
	
	// ** insert (원글)
	public int insert(BoardVO vo) {
		return sqlSession.insert(NS+"insert",vo);
	} //insert
	// ** update
	public int update(BoardVO vo) {
		return sqlSession.update(NS+"update",vo);
	} //update
	// ** delete
	public int delete(BoardVO vo) {
		return sqlSession.delete(NS+"delete",vo);
	} //delete
} // class
