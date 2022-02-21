package springMybatis;

import static org.junit.Assert.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-*.xml"})
public class Ex01_SqlSession {
	
	@Autowired
	private SqlSessionFactory sqlFactory;
	// 계층도: SqlSessionFactory (interface)  
	//        -> FactoryBean<SqlSessionFactory> (interface) -> SqlSessionFactoryBean
	//@Before()
	// import org.junit.Before; (확인)
	// SqlSessionFactory 생성 & 자동주입 확인 ->  sqlFactory is Not Null
	public void sqlFactoryTest() {
		assertNotNull(sqlFactory);
		System.out.println("\n*** SqlSessionFactory => "+sqlFactory);
	} // sqlFactoryTest
	
	// SqlSession -> 실제 DB 연결, Mapper의 Sql 구문을 이용해 DAO의 요청을 처리.
    // test1) 정상 의 경우 sqlSessionTest() 만 Test
    // test2) SqlSessionFactory 생성 안된 경우 Test
    // test2) @Before 적용  sqlFactoryTest() , sqlSessionTest() 모두 Test
	//@Test
	public void sqlSessionTest() {
		SqlSession sqlSession = sqlFactory.openSession();
		// sqlFactory가 NotNull 이어야 실행 가능 -> Before 적용
		System.out.println("*** SqlSession 생성 성공 => "+sqlSession);
		// 계층도 : SqlSession (interface) -> SqlSessionTemplate
		
		
	} // sqlSession

} // class
