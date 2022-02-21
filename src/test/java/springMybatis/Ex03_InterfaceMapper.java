package springMybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mapperInterface.MemberMapper;
import vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class Ex03_InterfaceMapper {
	
	@Autowired
	MemberMapper mapper;
	// => 위의 interface를 구현클래스를 자동주입 받아야함
	//    그러나 이 interface 구현클래스를 직접 만들지 않았고, ~Mapper.xml만 작성했었음.
	//    ~ServiceImpl에서는 ~Mapper.xml과 연동시켜 놓음(메서드명으로)
	//    스프링이 실행과정에서 이 클래스를 만들어 실행해줌
	//    이것을 위한 경로 맞춰주는 설정이
	//    <mybatis-spring:scan base-package="mapperInterface"/>
	
	@Autowired
	MemberVO vo;
	
	// ** Mapper의 동작 Test
	
	public void mapperTest() {
		System.out.println("**** Mapper interface => "+mapper.getClass().getName());
		// => getClass().getName() : 실제동작하는 클래스의 이름 확인가능
		//    이를 통해 우리는 Mapper interface 만 작성했지만, 
		//    내부적으로는 동일한 타입의 클래스가 만들어졌음을 알 수 있다.  
	} // mapperTest
	
	// ** Mapper의 각 sql 구문 동작 Test
	// test1)
    public void selectOne() {
    	vo.setId("unitTest");
    	// 존재하는 id 사용시 해당 Row return
    	// 없는 id 사용시 null return
    	vo=mapper.selectOne(vo);
    	System.out.println(" **** "+vo);
    	assertNotNull(vo);
    }
    
    // test2) 
    public void totalRowCountTest() {
    	int count = mapper.totalRowCount();
    	System.out.println("** Member 전체 Record count : "+count);
    } // totalRowCountTest()
    
    // test3)  Insert
    public void insertTest() {
    	vo.setId("junitS3");
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
    	assertEquals(mapper.insert(vo), 1);
 	 } // insertTest()
    @Test
    // test4) Delete
    public void deleteTest() {
    	vo.setId("junitS2");
    	assertEquals(mapper.delete(vo), 1);
    }   

} // class 
