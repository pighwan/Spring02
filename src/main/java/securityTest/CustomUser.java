package securityTest;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import vo.MemberVO;

// ** Lamda 와 stream ( 교재 정석 14장 )
// ** CustomUser
// => UserDetails (i) -> User (a) -> CustomUser	
// => 애플리케이션의 user 에 해당하는 VO 에 UserDetails 를 구현하여
//    Spring Security가 이해할 수 있는 형태의 User로 만들어 주기위함.

@Getter 
// 클래스의 맴버변수에 getter 를 자동생성 
// => getMember() 자동생성
// => JSP 에서 principal 사용시 필요함.
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	private MemberVO member;
	
	// ** Generic 타입제한 (Wildcards_와일드카드타입 이용으로)
	// => <?>
	//	  Unbounded Wildcards (제한없음_모든 클래스나 인터페이스 타입 가능)
	// => <? extends ...>
	//	  Upper Bounded Wildcards (상위클래스 제한_같거나 하위 타입 가능)
	// => <? super ...>
	//	  Lower Bounded Wildcards (하위클래스 제한_ 같거나 상위타입 가능)
	
	// ** Collection<? extends GrantedAuthority> authorities
	// => 인스턴스 생성시 타입인자로 GrantedAuthority 또는 이를 상속하는 클래스만 올 수 있음
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// 조상 생성자를 호출 해야 정상객체 생성 가능함 
	}
	
	// ** GrantAuthority (I)
	// => 현재 사용자(principal)가 가지고 있는 권한을 의미
	// => 특정 자원에 대한 권한이 있는지를 검사하여 접근 허용 여부를 결정한다.
	// => 계층도
	//    Serializable (I) -> GrantAuthority (I) -> SimpleGrantedAuthority 구현클래스
	// => 특징
	//    - 추상메서드 1개만 가지고 있는 함수형 인터페이스(Functional Interface) 
	//    - String getAuthority(); 
	//    - 그러므로 Lamda식 표현 가능
	
	// ** stream()
	// => Collection interface 에 정의된 default 메서드
	// => default 메서드란 ? 
	//	  Java 8 에 추가된 기능으로 메서드의 구현부를 허용하는 메서드 
	//    그러므로 implements 하는 클래스에서는 이를 구현하지 않아도 되며,
	//    필요한 경우에는 오버라이딩 해도 됨.
	//    ( 불필요한 경우에도 무조건 오버라이딩 하지 않아도 된다는 장점을 지니며, 
	//      추상 클래스와의 차이점은 다중 구현이 허용된다는점 )     
	//    return type 앞에 default 를 붙여주면 됨
	// => Stream<...> 를 return 함.  (Stream SpringSecurity.ppt 11~12 p 참고)
	//    아래 65행 에서는  Stream<AuthVO> 를 return 함.
	
	
	// ** map()
	// =>  매개변수를 Lamda 식으로 전달받음.
	/*
	  맵은 리스트에 들어있는 객체의 타입을 바꾸는 특징을 가지고 있다 
          조건을 지정해주면 해당 조건에서 지정된 리턴타입에 맞게 변환된다. 
	 */
	
	// ** new SimpleGrantedAuthority(auth.getAuth())
	// => SimpleGrantedAuthority 의 생성자
	
	//	public SimpleGrantedAuthority(String role) {
	//		Assert.hasText(role, "A granted authority textual representation is required");
	//		this.role = role;
	//	}
	// => role 을 초기화 함.
	
	// ** 참고
	// assert : (사실임을 강하게) 주장하다, (단호하게) 자기주장을 하다 
	
	// MemberVO 를 인자로 전달해서 조상인 User에 맞게 User 생성자를 호출
	// 이 과정에서 AuthVO 인스턴스는 GrantedAuthority 객체로 변환해야 하기 때문에 stream() 과 map() 을 이용해서 처리함.
	public CustomUser(MemberVO vo) {
		super(vo.getId(), vo.getPassword(), vo.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList()));
		// super(vo.getId(), vo.getPassword(),vo.getAuthList().stream().map(???).collect(???));
		// => 현재 사용자의 권한 목록을 가져옴
		this.member = vo;
	}
	
	// ** auth -> new SimpleGrantedAuthority .... 
	// => LamdaEx01_basic.java 참고 
	
	// ** ~~~List().stream().map(...);
	// => map 의 내용이  ~~List() 에  stream 되어짐 (즉 채워짐 / stream:흐름,개울,줄기)
	
	// ** collect() 메서드
	// => interface Collector를 구현한 클래스의 인스턴스를 매개변수로 받아 최종연산(연산:하나로합치는동작) 을 한다. 
	//    Object collect(Collector collector)
	
	// ** Collector interface
	// => collect() 메서드의 인자로 사용되며  collect() 메서드에서 사용될 수 있는 메서드들을 정의해 놓았다. 
	// 	    변환 : mapping(), toList(), toSet(), toMap(), toCollection(),.
	//    통계 : counting(), summingInt(), averagingInt(), ...
	//    문자열 결합 : joining() 
	//    리듀싱 : reducing()
	//    그룹화와 분할 : groupingBy(), partitioningBy(), collectionAndThen()		
	
	// ** Collectors 클래스
	// => interface Collector 를 구현한 클래스  

} // class
