package securityTest;

import vo.MemberVO;

// ** SQL 구문을 작성한 SecurityMapper.xml 과 Service 연결

// 1) DAO
//    => SqlSession 클래스를 통해 접근

// 2) interface ~~Mapper.java 
//    => ~~Mapper.java 와  mapper.xml 의  경로 , 화일명, 
//       mapper.xml 의  namespace 값은 모두 반드시 같아야 한다.
//       때문에 sqlSessionFactory bean 등록시   mapperLocations 속성 설정 필요 없음 
//    => SqlSession 클래스를 사용하지 않기 때문에 
//       xml 설정시  SqlSession 의   bean 등록 하지 않아도됨 
//	  => root~ 또는 serv~.xml 에 패키지 설정 : <mybatis-spring:scan base-package="securityTest"/>  	    

// **  ServiceImpl 의 예

/* 	@Autowired
	MemberMapper memberMapper;
	
	public int insert(MemberVO memberVO) {
		return memberMapper.insert(memberVO);
	}
*/

public interface SecurityMapper {
	
	MemberVO read(String id);
	// 매퍼 SecurityMapper.xml 의 id 가 read 인 sql 구문을 의미함.

} // interface
