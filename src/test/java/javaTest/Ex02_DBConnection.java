package javaTest;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;



// ** @종류
// => @Before - @Test - @After
// => @ 적용 메서드 : non static, void 로 정의 해야 함.

public class Ex02_DBConnection {
	
	// 1) static, return 값 있는 경우 Test
	// => Test 메서드를 작성해서 Test 
	public static Connection getConnection() {
		try {
			// 1) Oracle JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 객체 생성
			String url ="jdbc:oracle:thin:@127.0.0.1:1521:XE";
			System.out.println("** DB Connection 성공 **");
			return DriverManager.getConnection(url,"system","oracle");
		} catch (Exception e) {
			System.out.println("** DB Connection 실패 => "+e.toString());
			return null;
		}
	} //getConnection()
	//@Test
	public void connectionTest() {
		//System.out.println("*** Connection => "+getConnection());
		// => 연결성공 : NotNull, 연결실패 : Null
		assertNotNull(getConnection()); // true(green)
	} // connectionTest
	
	// 2) non static, void 로 정의
    // => Connection 시 발생 가능 오류
    // 1522 : java.sql.SQLRecoverableException: IO 오류: The Network Adapter could not establish
    // xe : ORA-12505, TNS:listener does not currently know of SID given in connect descriptor
    // @192.10.10.1 : java.sql.SQLRecoverableException: IO 오류: The Network Adapter could not establish
    // thin1 : java.sql.SQLException: 부적합한 Oracle URL이 지정
    // OracleDriver11 : java.lang.ClassNotFoundException: oracle.jdbc.driver.OracleDriver11
	//@Test
	public void getConnectionVoid() {
		try {
			// 1) Oracle JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 객체 생성
			String url ="jdbc:oracle:thin:@127.0.0.1:1521:XE";
			Connection cn = DriverManager.getConnection(url,"system","oracle");
			System.out.println("** DB Connection 성공 => "+cn);
		} catch (Exception e) {
			System.out.println("** DB Connection 실패 => "+e.toString());
		}
	} //getConnectionVoid()
} // class
