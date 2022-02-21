package criTest;

import lombok.Getter;
import lombok.ToString;

//** Criteria : (판단이나 결정을 위한) 기준
//=> 출력할 Row 를 select 하기 위한 클래스
//=> 이것을 위한 기준 값들을 관리

@Getter
@ToString
public class Criteria {
	private int rowsPerPage; // 1Page당 출력할 row갯수 
	private int currPage; // 요청(출력할) PageNo
	private int sno ; // start RowNo
	private int eno ; // end RowNo
	
	// 1) 필요한 초기값은 생성자로 초기화  
	public Criteria() {
		this.rowsPerPage=3;
		this.currPage=1;
	}
	// 2) setCurrPage : 요청받은(출력할) PageNo set
	public void setCurrPage(int currPage) {
		if (currPage>1) this.currPage=currPage;
		else this.currPage=1;
	}
	// 3) setRowsPerPage 
	// => 1페이지당 보여줄 Row(Record,튜플) 갯수 확인
	// => 제한조건 점검 ( 50개 까지만 허용)
	// => 당장은 사용하지 않지만 사용가능하도록 작성
	public void setRowsPerPage(int rowsPerPage) {
		if (rowsPerPage<3 || rowsPerPage>50)
			 this.rowsPerPage=3;
		else this.rowsPerPage=rowsPerPage; 
	}
	// 4) setSnoEno : sno, eno 계산
	public void setSnoEno() {
		if (this.sno<1) this.sno=1;
		this.sno=(this.currPage-1)*this.rowsPerPage + 1 ;
		this.eno=this.sno + this.rowsPerPage - 1 ;
	}
	
} //class
