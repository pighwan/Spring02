package javaTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



// ** Book class
// => 맴버필드 3개 정의, 맴버필드 3개를 초기화하는 생성자를 만드세요 ~

class Book {
	String author;
	String title;
	int price;
	
	Book(String author, String title, int price) {
		this.author = author;
		this.title = title;
		this.price = price;
	} // 생성자
	
	public boolean isBook(boolean b) {
		return b;
	}	
} // Book

// ** @ 종류
// => @Before - @Test - @After
// => @ 적용 메서드 : non static, void 로 정의 해야 함.

// ** org.junit.Assert 가 지원하는 다양한 Test용 Method 
// 1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
// 2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
// 3) assertTrue(a) : a가 참인지 확인
// 4) assertNotNull(a) : a객체가 Null 이 아님을 확인
// 5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인

public class Ex01_BookTest {
	
// 1) assertEquals(a, b) : 값의 동일성 ( 벨류 비교)
	//@Test
	public void equalsTest() {
		Book b1 = new Book("톨스토이", "죄와벌", 9900);
		// assertEquals(b1.author, "콩길동"); // => red_line
		assertEquals(b1.author, "톨스토이"); // => green_line
	} // equalsTest
	
// 2) assertSame(a, b) : 객체의 동일성 (주소 비교)
	//@Test
	public void sameTest() {
		Book b1 = new Book("톨스토이", "죄와벌", 9900);
		Book b2 = new Book("톨스토이", "죄와벌", 9900);
		Book b3 = new Book("허균", "홍길동전", 8800);
		// assertSame(b1, b2); // => false (red)
		b1 = b3;
		assertSame(b1, b3); // => true (green)
	} // sameTest
	
// 3) assertTrue(a) : a가 참인지 확인
	//@Test
	public void trueTest() {
		Book b1 = new Book("톨스토이", "죄와벌", 9900);
		assertTrue(b1.isBook(false)); // => red
	} // trueTest
	
// 4) assertNotNull(a)
	//@Test
	public void notNullTest() {
		Book b1 = new Book("톨스토이", "죄와벌", 9900);
		Book b2 = null;
		System.out.println("** b1 => "+b1);
		System.out.println("** b2 => "+b2);
		// assertNotNull(b1); // green
		//assertNotNull(b2); // red
	} // notNullTest
	
// 5) assertArrayEquals(a, b) : 배열의 동일성
	@Test
	public void arrayEqualsTest() {
		String[] a1 = new String[] {"가", "나", "다"};
		String[] a2 = new String[] {"가", "나", "다"};
		String[] a3 = new String[] {"가", "다", "나"};
		String[] a4 = new String[] {"가", "다", "라"};
		System.out.println("** a1 => "+a1);
		System.out.println("** a2 => "+a2);
		System.out.println("** a3 => "+a3);
		System.out.println("** a4 => "+a4);
		// 5.1) 두 배열의 순서, 값 모두 동일(a1, a2)
		//assertArrayEquals(a1, a2); // green		
		assertSame(a1, a2); // red
		
		// 5.2) 두 배열의 순서는 다르고, 값 모두 동일(a1, a3)
		//assertArrayEquals(a1, a3); // red
		
		// 5.3) 모두 다른 경우(a1, a4)
		//assertArrayEquals(a1, a4); // red
		
	} // arrayEqualsTest
	
	
} // class
