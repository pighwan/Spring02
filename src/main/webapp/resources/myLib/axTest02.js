/**
** AjaxTest02
=> 반복문에 이벤트 적용하기 
=> axmlist -> id별로 board조회, delete 기능, ImageDownload
=> axblist -> 상세글 보기 기능
 */
$(function(){
// ** Ajax MemberList
	$('#axmlist').click(function(){
		$.ajax({
			type:"Get",
			url:"axmlist",
			success:function(resultPage) {
				$('#resultArea1').html(resultPage);	
			},
			error:function() {
				alert("~~ 서버오류!!! 잠시후 다시 하세요 ~~");
			}
		}); //ajax
	}); //axmlist_click	
	
	// ** Ajax BoardList	
  $('#axblist').click(function(){
	$.ajax({
		type:'Get',
		url:'axblist',
		success:function(resultPage){
			$('#resultArea1').html(resultPage);
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
	}); //ajax
  }); //axblist_click	

// ** 반복문에 이벤트 적용하기
// => 2) JQuery : class, id, this 이용
  $('.ccc').click(function(){
	/* id값 가져오기 
	1) 컨텐츠 인식
	var id = $(this).html(); // 또는 $(this).text();
	2) 미리 만들어둔 id 값 이용하기 */
	var id = $(this).attr('id');
	
	$.ajax({
		type:"Get",
		url:"aidblist",
		data: {
			id:id
		},
		success:function(resultPage) {
			$('#resultArea2').html(resultPage);
		},
		error:function() {
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
	}); //ajax 
  }); //ccc_click 	

// => Member Delete 기능 2) : JQ
// => class, id, this 활용

  $('.ddd').click(function(){
	
	var id = $(this).attr('id');
	$.ajax({ 
		type:"Get",
		url:"jsdelete",
		data:{
			id:id
		},
		success:function(resultData){
			// 삭제성공 : Delete -> Deleted , 이벤트제거
			if ( resultData.success=='T' ) {
				alert("~~ JQ방식 삭제성공 !!! ~~");
				/*ajax 처리 단계이므로 이미 this는 변경되어 click 시의 this 가 아님 */
				$('#'+id).html('Deleted');
				$('#'+id).css({
					color:'Gray',
					fontWeight:'bold'
				});
				$('#'+id).off();
				// 이벤트제거 (적용됨) , removeClass 는 적용안됨
			}
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
		
	}); //ajax
	
	
	
	
	
	
	
  }); //ddd_click


	
}); //ready

// ** 반복문에 이벤트 적용하기 
// => id별로 board조회
// test 1) JS function
function aidBList(id) {
	$.ajax({
		type:"Get",
		url:"aidblist",
		data: {
			id:id
		},
		success:function(resultPage) {
			$('#resultArea2').html(resultPage);
		},
		error:function() {
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
	}); //ajax 
} //aidBList

// => Member Delete 기능 1) : JS 
//    jsonView 처리
function amDelete(id, index) {
	console.log('** amDelete id, index =>'+id+index);
	$.ajax({
		type: 'Get',
		url: 'jsdelete',
		data:{
			id:id
		},
		success:function(resultData){
			if (resultData.success=='T') {
				alert('~~ JS: 삭제 성공 !! ~~');
				
				// 삭제됨 으로 변경하고, 더이상 클릭하지 못하도록 함
				// => 그러나, this 는 적용안됨
				// => ajax  가 실행되면서 this 는 이미 변경됨
				// => this 대신 사용가능한 속성이 필요함 ( id 를 부여해서 사용)
				
				// => <span> 에 id 를 적용한 경우
				// $('#'+index).html('Deleted').removeClass('textLink');
				// $('#'+index).off('click'); // 이벤트 제거
				// => Deleted 는 적용 되지만, removeClass, off 적용안됨.
				// => JQ 메서드가 적용안되므로 <td> 에 id 를 적용하고 의 값을 변경함 
				$('#'+index).html('Deleted');
				$('#'+index).css({
					color:'gray',
					fontWeight:'bold'
				});
				
			}else {
				alert('~~ JS: 삭제 실패 ~~');
			}
		},
		error:function(){
			alert('~~ 서버 오류 !!! 잠시후 다시 하세요 ~~');
		}
	}); //ajax
} //amDelete







