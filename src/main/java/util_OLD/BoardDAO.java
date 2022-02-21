package util_OLD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.BoardVO;
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
	
	Connection cn = DBConnection.getConnection();
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	
	// ** PageList1.
	// => List<T> list  : 출력할 목록
	// => totalRowCount : 전체Row 갯수 (전체 Page수 계산위해 필요) 
	public int totalRowCount() {
		sql="select count(*) from board";
		try {
			st = cn.createStatement();
			rs= st.executeQuery(sql);
			if (rs.next()) return rs.getInt(1);
			else return 0;
		} catch (Exception e) {
			System.out.println("** totalRowCount Exception  => "+e.toString());
			return 0;
		}
	} //totalRowCount
	
	public PageVO<BoardVO> pageList(PageVO<BoardVO> pvo) {
		// ** totalRowCount
		pvo.setTotalRowCount(totalRowCount());
		
		// ** List<T> list  : 출력할 목록
		sql="select seq, title, id, regdate, cnt, root, step, indent from "
				+ "(select b.*, ROW_NUMBER() OVER(order by root desc, step asc) rnum from board b)"
				+ "where rnum between ? and ?" ;
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, pvo.getSno());
			pst.setInt(2, pvo.getEno());
			rs=pst.executeQuery();
			// 요청객체로 결과 전달
			// => 출력자료가 있는지 확인
			if (rs.next()) {
				// => 출력자료 1row -> vo 에 set  -> list 에 add 
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setId(rs.getString(3));
					vo.setRegdate(rs.getString(4));
					vo.setCnt(rs.getInt(5));
					vo.setRoot(rs.getInt(6));
					vo.setStep(rs.getInt(7));
					vo.setIndent(rs.getInt(8));
					list.add(vo);
				}while(rs.next());
			}else {
				System.out.println("** B pagetList: 출력 자료가 없습니다 ~~");
				list=null;
			}
		} catch (Exception e) {
			System.out.println("** B pagetList Exception => "+e.toString());
			list=null;
		}
		pvo.setList(list);
		return pvo;
	} //pageList 
	
	// ** selectList
	public List<BoardVO> selectList() {
		sql="select seq, title, id, regdate, cnt, root, step, indent from board order by root desc, step asc" ;
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			st=cn.createStatement();
			rs=st.executeQuery(sql);
			// 요청객체로 결과 전달
			// => 출력자료가 있는지 확인
			if (rs.next()) {
				// => 출력자료 1row -> vo 에 set  -> list 에 add 
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setId(rs.getString(3));
					vo.setRegdate(rs.getString(4));
					vo.setCnt(rs.getInt(5));
					vo.setRoot(rs.getInt(6));
					vo.setStep(rs.getInt(7));
					vo.setIndent(rs.getInt(8));
					list.add(vo);
				}while(rs.next());
			}else {
				System.out.println("** selectList: 출력 자료가 없습니다 ~~");
				list=null;
			}
		} catch (Exception e) {
			System.out.println("** selectList Exception => "+e.toString());
			list=null;
		}
		return list;
	} //selectList 

	// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		sql="select * from board where seq=?" ;
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1,vo.getSeq());
			rs = pst.executeQuery();
			// 결과 확인
			if (rs.next()) {
				vo.setSeq(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setId(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setRoot(rs.getInt(7));
				vo.setStep(rs.getInt(8));
				vo.setIndent(rs.getInt(9));
			}else {
				System.out.println("** 글번호에 해당하는 자료가 없습니다 ~~ **");
				vo=null;
			} //else
		} catch (Exception e) {
			System.out.println("** selectOne Exception => "+e.toString());
			vo=null;
		}
		return vo;
	} //selectOne	
	
	// ** 새글등록 -> insert
	// ** MySQL 
	//sql="insert into board(title,id,content) values(?,?,?)";
	     // insert into board (title,id,content) values ('Spring 이란?','green','처음엔 ~~~ 편리하다'); 
	public int insert(BoardVO vo) {
		// ** Oracle : seq 에 nvl함수 적용 
		sql="insert into board values("
				+ "(select nvl(max(seq),0)+1 from board)"
				+ ",?,?,?,sysdate,0,(select nvl(max(seq),0)+1 from board),0,0)";
		// insert into board values (100,'Spring 이란?','green','처음엔 ~~~ 편리하다',sysdate,0); 
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getTitle());
			pst.setString(2,vo.getId());
			pst.setString(3,vo.getContent());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** insert Exception => "+e.toString());
			return 0;
		}
	} //insert
	
	// ** Update
	public int update(BoardVO vo) {
		sql="update board set title=?,content=? where seq=?";
		// update board set title='newTitle', content='newContent' where seq=100 ; 
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getTitle());
			pst.setString(2,vo.getContent());
			pst.setInt(3,vo.getSeq());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** update Exception => "+e.toString());
			return 0;
		}
	} //update
	
	// ** Delete
	// => 답글 추가후
	//	  - 원글삭제시 : 하위글모두 삭제
	//    - 답글삭제시 : 해당답글만 삭제
	public int delete(BoardVO vo) {
		// 원글삭제 or 답글삭제 확인
		if ( vo.getSeq()==vo.getRoot() ) { 
			// 원글 : 동일 root 모두 삭제
			// -> 원글은 seq와 root가 동일하므로 where 의 비교 컬럼만 root 이고, ? 는 seq 로 해도됨   
			sql = "delete from board where root=?" ;
		}else {  // 답글 -> 해당 seq 만 삭제
			sql="delete from board where seq=?";
		}
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			return pst.executeUpdate() ;
		} catch (Exception e) {
			System.out.println("** delete Exception => "+e.toString());
			return 0;
		}
	} //delete
	
	// ** 조회수증가
	public int countUp(BoardVO vo) {
		sql="update board set cnt=cnt+1 where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1,vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** countUp Exception => "+e.toString());
			return 0;
		} 
	} //countUp
	
	// ** 답글입력
	public int stepUpdate(BoardVO vo) {
		// 답글입력 성공후  stepUpdate 실행 
		// -> 입력된 답글 자신의 step 값은 변경되지않도록 하기위해 seq 비교조건 추가함 
		sql="update board set step=step+1 where root=? and step>=? "
				+ "and seq <> (select max(seq) from board)" ;
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getRoot());
			pst.setInt(2, vo.getStep());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** stepUpdate Exception => "+e.toString());
			return 0;
		}
	} //stepUpdate
	
	public int rinsert(BoardVO vo) {
		sql="insert into board values("
				+ "(select nvl(max(seq),0)+1 from board)"
				+ ",?,?,?,sysdate,0,?,?,?)";
		int result=0;
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getTitle());
			pst.setString(2,vo.getId());
			pst.setString(3,vo.getContent());
			pst.setInt(4, vo.getRoot());
			pst.setInt(5, vo.getStep());
			pst.setInt(6, vo.getIndent());
			result=pst.executeUpdate();
			
			// 답글입력 성공후  stepUpdate 실행 
			// -> 자신의 step 값은 변경되지않도록 stepUpdate sql 구문의 조건 주의 
			System.out.println("** StepUpdate count => "+stepUpdate(vo));
			
		} catch (Exception e) {
			System.out.println("** Reply_insert Exception => "+e.toString());
		}
		return result;
	} //rinsert

} // class
