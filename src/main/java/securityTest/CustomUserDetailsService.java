package securityTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import vo.MemberVO;

// ** UserDetailsService (i) 
// => loadUserByUsername(String userId) 1개의 메서드가 있음.
// => 이 메서드의 return Type 은 UserDetails 
// => 그러므로 Mapper의 read 결과인 MemberVO 인스턴스를 위의 return Type에 맞게
//    UserDetails Type으로 변환해야 한다. 
// => 그래서 UserDetails 구현 클래스를 작성하고 이를 이용해 변환한다.
// => UserDetails (i) 는 다양한 구현 클래스가 있는데 그중 
//    org.springframework.security.core.userdetails.User 
//    클래스를 상속받는 방법을 많이 이용한다.-> CustomUser 클래스 작성

// ** UserDetails (i)
// => getUserName(), getPassword(), getAuthorities() 등의 추상메서드를 가짐

// ** CustomUserDetailsService
// => UserDetailsService (i) 구현
// => DB 에서 로그인값을 이용해서 User read , Detail Value (member+auth) 값
//    을 활용할 수 있도록 전달  
// => SecurityMapper Type 의 인스턴스를 주입 받아서 기능구현


@Log4j
public class CustomUserDetailsService implements UserDetailsService {

	@Setter(onMethod_ = @Autowired)
	private SecurityMapper sMapper;
	// @Setter
	// => 자동으로 setter 를 컴파일시 생성
	// => onMethod 속성은 생성되는 setter에 @AutoWired 어노테이션을 추가해줌.
	//    ( Lombok으로 생성된 정보는 이클립스를 통해 확인가능 )
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		
		log.warn("*** Load User By UserName : " + id);
		MemberVO memberVO = sMapper.read(id);
		log.warn("*** queried by member mapper : " + memberVO);
		
		return memberVO == null ? null : new CustomUser(memberVO);
		// mappre 가 return 한 memberVO 를 Spring Security가 이해할수있는 UserDetails Type의 User로 만들어 주기위함.
	}

} // class
