package vo;

import java.util.List;

import lombok.Data;

//** DTO : Data Transfer Object
//** member , board 모두 사용 가능하도록 Generic 설정
@Data
public class PageVO<T> {
	
	private List<T> list;  // 출력할 목록
	private int totalRowCount; // 전체Row 갯수 (전체 Page수 계산위해 필요) 
	private int rowsPerPage = 3; // 1Page당 출력할 row갯수 
	private int pageNoCount = 3; // 1Page당 표시할 pageNo갯수
	private int currPage; // 요청(출력할) PageNo
	private int sno ; // start RowNo
	private int eno ; // end RowNo
	
} //class
