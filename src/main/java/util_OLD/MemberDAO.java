package util_OLD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.BoardVO;
import vo.MemberVO;
import vo.PageVO;

//** DAO (Data Access Object)
//=> CRUD 구현 
//   C: create -> insert
//   R: read   -> selectList, selectOne
//   U: update -> update
//   D: delete -> delete

@Repository
public class MemberDAO {
	// ** 전역변수 정의
	Connection cn = DBConnection.getConnection();
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	String sql;
	
	// ** PageList1.
	// => List<T> list  : 출력할 목록
	// => totalRowCount : 전체Row 갯수 (전체 Page수 계산위해 필요) 
	public int totalRowCount() {
		sql="select count(*) from member where id!='admin'";
		try {
			st = cn.createStatement();
			rs= st.executeQuery(sql);
			if (rs.next()) return rs.getInt(1);
			else return 0;
		} catch (Exception e) {
			System.out.println("** Member totalRowCount Exception  => "+e.toString());
			return 0;
		}
	} //totalRowCount
	
	public PageVO<MemberVO> pageList(PageVO<MemberVO> pvo) {
		// ** totalRowCount
		pvo.setTotalRowCount(totalRowCount());
		
		// ** List<T> list  : 출력할 목록
		sql="select * from "
				+ "(select m.*, ROW_NUMBER() OVER(order by id asc) rnum from member m where id!='admin')"
				+ "where rnum between ? and ?" ;
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, pvo.getSno());
			pst.setInt(2, pvo.getEno());
			rs=pst.executeQuery();
			if (rs.next()) {
				// => 출력자료 1row -> vo 에 set  -> list 에 add 
				do {
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString(1));
					vo.setPassword(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setLev(rs.getString(4));
					vo.setBirthd(rs.getString(5));
					vo.setPoint(rs.getInt(6));
					vo.setWeight(rs.getDouble(7));
					vo.setRid(rs.getString(8));
					vo.setUploadfile(rs.getString(9));
					list.add(vo);
				}while(rs.next());
			}else {
				System.out.println("** M pageList: 출력 자료가 없습니다 ~~");
				list=null;
			}
		} catch (Exception e) {
			System.out.println("** M pageList Exception => "+e.toString());
			list=null;
		}
		pvo.setList(list);
		return pvo;
	} //pageList 	
	
	// ** selectList
	public List<MemberVO> selectList() {
		sql="select * from member where id != 'admin'" ;
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			st=cn.createStatement();
			rs=st.executeQuery(sql);
			// 요청객체로 결과 전달
			// => 출력자료가 있는지 확인
			if (rs.next()) {
				// => 출력자료 1row -> vo 에 set  -> list 에 add 
				do {
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString(1));
					vo.setPassword(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setLev(rs.getString(4));
					vo.setBirthd(rs.getString(5));
					vo.setPoint(rs.getInt(6));
					vo.setWeight(rs.getDouble(7));
					vo.setRid(rs.getString(8));
					vo.setUploadfile(rs.getString(9));
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
	} //selectList()

	// ** selectOne
	public MemberVO selectOne(MemberVO vo) {
		sql="select * from member where id=?" ;
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getId());
			rs = pst.executeQuery();
			// 결과 확인
			if (rs.next()) {
				vo.setId(rs.getString(1));
				vo.setPassword(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setLev(rs.getString(4));
				vo.setBirthd(rs.getString(5));
				vo.setPoint(rs.getInt(6));
				vo.setWeight(rs.getDouble(7));
				vo.setRid(rs.getString(8));
				vo.setUploadfile(rs.getString(9));
			}else {
				System.out.println("** 해당하는 자료가 없습니다 ~~ **");
				vo=null;
			} //else
		} catch (Exception e) {
			System.out.println("** selectOne Exception => "+e.toString());
			vo=null;
		}
		return vo;
	} //selectOne	
	
	// ** Join -> insert
	public int insert(MemberVO vo) {
		sql="insert into member values(?,?,?,?,?,?,?,?,?,1)";
		// insert into member values('javasam','12345','김유신','A','1990-01-01',1000,55.89); 
		// => JUnit Test : security 적용 후 추가된 enabled 컬럼 때문에 1 추가
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getId());
			pst.setString(2,vo.getPassword());
			pst.setString(3,vo.getName());
			pst.setString(4,vo.getLev());
			pst.setString(5,vo.getBirthd());
			pst.setInt(6,vo.getPoint());
			pst.setDouble(7,vo.getWeight());
			pst.setString(8,vo.getRid()); 
			pst.setString(9,vo.getUploadfile());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** insert Exception => "+e.toString());
			return 0;
		}
	} //insert
	
	// ** Update
	public int update(MemberVO vo) {
		sql="update member set password=?, name=?, lev=?, birthd=?, point=?, weight=?, rid=?, uploadfile=? where id=?";
		// update member set password='newPassword', name='newName' where id='banana'; 
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getPassword());
			pst.setString(2,vo.getName());
			pst.setString(3,vo.getLev());
			pst.setString(4,vo.getBirthd());
			pst.setInt(5,vo.getPoint());
			pst.setDouble(6,vo.getWeight());
			pst.setString(7,vo.getRid());
			pst.setString(8,vo.getUploadfile());
			pst.setString(9,vo.getId());
			return pst.executeUpdate();
			// executeUpdate() => 처리된 row 의 갯수 return
		} catch (Exception e) {
			System.out.println("** update Exception => "+e.toString());
			return 0;
		}
	} //update
	
	// ** Delete
	public int delete(MemberVO vo) {
		sql="delete from member where id=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			return pst.executeUpdate() ;
		} catch (Exception e) {
			System.out.println("** delete Exception => "+e.toString());
			return 0;
		}
	} //delete
} //class
