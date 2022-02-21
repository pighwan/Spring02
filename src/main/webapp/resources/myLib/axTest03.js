/* ** Ajax, jsonView Board Test

Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  
*/
function jsBDetail1(seq) {
	$.ajax({
		type:"Get",
		url:"jsbdetail?seq="+seq,
		/*data:{seq:seq},*/
		success:function(resultData) {
			$('#resultArea2').html(resultData.content);
		},
		error:function() {
			alert("~~ 서버오류 !!! 잠시후 다시 하세요 ~~");
		}
	}); //ajax
} //jsBDetail1
/*
Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(<tr>추가 : span)에 글 내용 출력하기
	=> Toggle 방식으로 없을때 클릭하면 표시되고, 있을때 클릭하면 사라지도록 
*/
function jsBDetail2(seq, index) {
	
	if ( $('#content'+index).html()=='' ) {
		$.ajax({
			type:"Get",
			url:"jsbdetail?seq="+seq,
			/*data:{seq:seq},*/
			success:function(resultData) {
				$('#resultArea2').html('');
				$('.content').html('');
				$('#content'+index).html(resultData.content);
			},
			error:function() {
				alert("~~ 서버오류 !!! 잠시후 다시 하세요 ~~");
			}
		}); //ajax
	}else $('#content'+index).html('');
	
} //jsBDetail2

/*Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 
  => jQuery : id, class, this
  => hover(f1,f2);
  => 3.1) css 활용 : display 속성 */
$(function(){
	$('.sss1').hover(function(e){
		// ** 마우스포인터의 위치인식
		// => 현재 발생된 이벤트객체가 제공함
		//    이벤트핸들러인 익명함수의 매개변수는 현재 발생된 이벤트를 전달해줌 
		// => e.pageX, e.pageY : 전체 Page 기준
        //    e.clientX, e.clientY : 보여지는 화면 기준-> page Scroll 시에 불편함
		// => 마우스포인터 위치 보관이 필요함
		var mleft = e.pageX;
		var mtop = e.pageY;
		console.log('** e.pageX, e.pageY => '+e.pageX +', '+ e.pageY);
		console.log('** e.clientX, e.clientY => '+e.clientX +', '+ e.clientY);
		// 이벤트 Propagation(전달) Test : console.log 여러번 찍히는것을 확인
		// => return false; 추가후 비교해봄.
		
		// 1) seq 확인
		var seq = $(this).attr('id');
		// 2) ajax
		$.ajax({
			type:"Get",
			url:"jsbdetail?seq="+seq,
			success:function(resultData) {
			// 3) content 출력 (마우스 위치값 필요함)
			$('.content').html('');
			$('#content').html(resultData.content)
				.css({  /*Div 위치를 지정해서 출력함*/
					display:'Block',
					left:mleft,
					top: mtop
				}); //css
			},
			error:function() {
				alert("~~ 서버오류 !!! 잠시후 다시 하세요 ~~");
			}
		}); //ajax
		return false; // 이벤트 Propagation(전달) 방지
	 }, function(){ // clear
		$('.content').html('');
		$('#content').css('display','None') ;
		return false;
	 }); //sss1_hover

/* => 3.2) JQ 메서드 show() , hide() 활용 
	-> sss2 
	-> this, attr 활용 */
	$('.sss2').hover(function(e){
		var mleft = e.pageX;
		var mtop = e.pageY;
		console.log('** e.pageX, e.pageY => '+e.pageX +', '+ e.pageY);
		
		// 1) seq 확인
		var seq = $(this).attr('id');
		// 2) ajax
		$.ajax({
			type:"Get",
			url:"jsbdetail?seq="+seq,
			success:function(resultData) {
			// 3) content 출력 (마우스 위치값 필요함)
			$('.content').html('');
			$('#content').html(resultData.content)
				.css({  /*Div 위치를 지정해서 출력함*/
					left:mleft,
					top: mtop
				}).show();  
			},
			error:function() {
				alert("~~ 서버오류 !!! 잠시후 다시 하세요 ~~");
			}
		}); //ajax
		return false; // 이벤트 Propagation(전달) 방지
	 }, function(){ // clear
		$('.content').html('');
		$('#content').hide() ;
		return false;
	 }); //sss2_hover
	
}); //ready
/*
 * * JQ 로 이벤트 핸들러 작성시 주의 사항 
=> jQuery 로 이벤트를 처리하기 위해 지금처럼 ready 이벤트를 사용하는 경우
   본구문이 포함된 axTest03.js 를 부모창에도 추가하고, 
   결과로 불리워지는 loginForm.jsp 에도 포함 하게되면
   마치 callBack 함수처럼 실행 할 때마다 이중으로 불리워지면서
   2의 자승으로 늘어나게 됨. 
=> 해결방법
  1) ~.js 를 각각 분리한다. 
  -> $('#jlogin').click( .....) 부분 axTest04.js 로 독립 
  2) JS의 함수 방식 으로 이벤트 처리
  
** ** 이벤트 전달
=> Tag 들이 겹겹이 포함관계에 있을때 이벤트가 전달되어 여러번 발생가능함. 
=> 이것을 이벤트 Propagation(전파) 이라함.
=> 해결 : return false
  		-> e.preventDefault() + e.stopPropagation()

** ** JS & JQ 에서 익명함수의 매개변수역할 
=> 이벤트 핸들러 의 첫 매개변수 : 이벤트 객체를 전달 
=> ajax 메서드의 success 속성 : 서버의 response 결과 
=> css 속성값 : 0부터 순차적으로 대입됨 (jq03_attr.html 참고)

 */






