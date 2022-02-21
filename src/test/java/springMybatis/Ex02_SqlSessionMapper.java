package springMybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-*.xml"})
public class Ex02_SqlSessionMapper {
	
	@Autowired
	private SqlSessionFactory sqlFactory;
	//@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	MemberVO vo;	
	private static final String NS = "green.mapper.MemberMapper." ;
	
// ** Mapper의 각 sql 구문 동작 Test
// => SqlSession 생성을 설정화일에서 하지 않고, 메서드 내부에서 전달받아 처리함
//    맴버변수에서 직접 전달은 오류 -> 실행순서때문에 sqlFactory가 Null임.
	@Before
	public void sqlSessionTest() {
		sqlSession = sqlFactory.openSession();
		assertNotNull(sqlSession);
	} // sqlSessionTest
	
	// Test 1)
	//@Test
	public void selectOne() {		
		vo.setId("banana");
		vo = sqlSession.selectOne(NS+"selectOne", vo);
		assertNotNull(vo);
		System.out.println("*** selectOne => "+vo);
	} // selectOne
	
	// test 2) 
	//@Test
	public void totalRowCountTest() {
       int count = sqlSession.selectOne(NS+"totalRowCount") ;
       System.out.println("** Member 전체 Record count : "+count);
    } // totalRowCountTest()
	
	// test 3) Insert
	//@Test
	public void inserTest() {
      vo.setId("junitS");
      vo.setPassword("12345!");
      vo.setName("유니트");
      vo.setLev("A");
      vo.setBirthd("1909-09-09");
      vo.setPoint(1000);
      vo.setWeight(33.44);
      vo.setRid("test");
      vo.setUploadfile("test");
      // 입력 성공 1 return
      // => 처리 결과가 1 과 같은지 비교하여 성공 / 실패
      assertEquals(sqlSession.insert(NS+"insert", vo), 1);
		/*
		 * memberMapper.xml 파일 94번 줄 <![CDATA[ select count(*) from member where id <>
		 * 'admin' ]]> 가 적혀있기 때문에 총인원수에서 -1됨 디벨로퍼로 검색했을 때는
		 */
	} // insertTest()

	// test4) Delete
	//@Test
	public void deleteTest() {
      vo.setId("unitTest");
      assertEquals(sqlSession.delete(NS+"delete", vo), 1);
   }
	
} // class
