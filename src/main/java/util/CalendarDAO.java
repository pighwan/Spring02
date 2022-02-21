package util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.CalendarVO;

// ** interface 를 이용하여 Mapper 접근
// => 그러므로 CalendarDAO 는 사용하지 않음. 
//@Repository
public class CalendarDAO {
	
	//@Autowired
	private SqlSession dao;
	private static final String NS="green.mapper.CalendarMapper.";

	public List<CalendarVO>selectList(CalendarVO vo){
		return dao.selectList(NS+"selectList", vo);
	}
	
	public int listCount(CalendarVO vo) {
		return dao.selectOne(NS+"listCount", vo);
	}

	/* selectDetail 은 현재는 필요없음 */
	public CalendarVO selectOne(CalendarVO vo) {
		return dao.selectOne(NS+"selectDetail", vo);
	}

	public int insert(CalendarVO vo) {
		return dao.insert(NS+"insertCalendar", vo);
	}

	public int update(CalendarVO vo) {
		return dao.update(NS+"updateCalendar", vo);
	}

	public int delete(CalendarVO vo) {
		return dao.delete(NS+"deleteCalendar", vo);
	}
} // class
