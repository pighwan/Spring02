<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_Mybatis MemberJoin Form **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
<script src="resources/myLib/inCheck.js"></script>
<script src="resources/myLib/axTest01.js"></script>

<script>
//1) 개별적 오류 확인을 위한 switch 변수 (전역)	
  var iCheck=false;
  var pCheck=false;	
  var nCheck=false;
  var bCheck=false;
  var oCheck=false; 
  var wCheck=false; 

// 2) 개별적 오류점검위한 focusout 이벤트 핸들러 : JQuery
  $(function() {
	  $('#id').focus();
	  $('#id').keydown(function(e){
		  // enter 누르면 다음 으로 이동
			if (e.which==13) {
				e.preventDefault(); 
				$('#idDup').focus()
			}
	  }).focusout(function() {
		 	iCheck=idCheck();
	  }); //id_focusout
	  
	  $('#password').focusout(function(){
			pCheck=pwCheck();		  
	  }); //password_focusout
	  
	  $('#name').focusout(function(){
			nCheck=nmCheck();		  
	  }); //name_focusout
	  
	  $('#birthd').focusout(function(){
			bCheck=bdCheck();		  
	  }); //birthd_focusout
	  
	  $('#point').focusout(function(){
			oCheck=poCheck();		  
	  }); //point_focusout
	  
	  $('#weight').focusout(function(){
			wCheck=weCheck();		  
	  }); //weight_focusout
  }); //ready


// 3) submit 여부를 판단 & 실행 : JS 의 function
  function inCheck(){
	// 모든 항목에 오류 없음을 확인 : switch 변수
	if (iCheck==false) {
		$('#iMessage').html('~~ id 를 확인하세요 ~~');
	}
	if (pCheck==false) {
		$('#pMessage').html('~~ password 를 확인하세요 ~~');
	}	
	if (nCheck==false) {
		$('#nMessage').html('~~ name 을 확인하세요 ~~');
	}	
	if (bCheck==false) {
		$('#bMessage').html('~~ birthday 를 확인하세요 ~~');
	}
	if (oCheck==false) {
		$('#oMessage').html('~~ point 를 확인하세요 ~~');
	}
	if (wCheck==false) {
		$('#wMessage').html('~~ weight 를 확인하세요 ~~');
	}	
	// 모든 오류 확인완료 
	// => 없으면 submit : return true , 
	//    있으면 submit 을 취소 : return false 
	if ( iCheck && pCheck && nCheck &&
		 bCheck && oCheck && wCheck ) {
		// => submit : 404
		if (confirm("~~ 정말 가입 하십니까 ? (Yes:확인 / No:취소)")==false) {   
		 	  alert('~~ 가입이 취소 되었습니다 ~~');
		 	  return false;
		}else return true; // submit 진행 -> server의 join  
	} else return false; 
  } //inCheck

  //** ID 중복 확인하기
  function idDupCheck() {
	// id 의 입력값 무결성 점검 확인
	if (iCheck==false) {
		iCheck=idCheck();
	}else { 
		// id 중복확인
		// => id를 서버로 보내 중복확인, 결과 처리 
		// => window.open(url,'','')
		//    url 요청을 서버로 전달(request) 하고, 그결과(response)를 Open 해줌
		var url="idcheck?id="+$('#id').val(); 
		window.open(url,'_blank',
				'toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,width=400,height=300');
	}  
  } //idDupCheck
  
</script>

</head>
<body>
<h3>** Spring_Mybatis MemberJoin **</h3>
<!-- <pre><h3>
=> FileUpLoad TestForm
=> form 과 table Tag 사용시 주의사항 : form 내부에 table 사용해야함
   -> form 단위작업시 인식안됨
   -> JQ 의 serialize, FormData 의 append all 등
</h3></pre>
 -->
<form action="join" method="post" enctype="multipart/form-data" id="myForm">
<!-- => method="post" : 255 byte 이상 대용량 전송 가능 하므로 
	 => enctype="multipart/form-data" : 화일 upload 를 가능하게 해줌 
	** multipart/form-data는 파일업로드가 있는 입력양식요소에 사용되는 enctype 속성의 값중 하나이고, 
       multipart는 폼데이터가 여러 부분으로 나뉘어 서버로 전송되는 것을 의미
       이 폼이 제출될 때 이 형식을 서버에 알려주며, 
       multipart/form-data로 지정이 되어 있어야 서버에서 정상적으로 데이터를 처리할 수 있다.  -->
<table>
	<tr height="40"><td bgcolor="aqua">I D</td>
		<td><input type="text" name="id" id="id" value="testid" size="20">&nbsp;&nbsp;
			<input type="button" value="ID중복확인" id="idDup" onclick="idDupCheck()"><br>
			<span id="iMessage" class="eMessage"></span></td></tr>
	<tr height="40"><td bgcolor="aqua">Password</td>
		<td><input type="password" name="password" id="password" value="12345" size="20"><br>
			<span id="pMessage" class="eMessage"></span></td></tr>	
	<tr height="40"><td bgcolor="aqua">Name</td>
		<td><input type="text" name="name" id="name" value="홍길동" size="20"><br>
			<span id="nMessage" class="eMessage"></span></td></tr>
	<tr height="40"><td bgcolor="aqua">Level</td>
		<td><select name="lev" id="lev">
				<option value="A" >관리자</option>
				<option value="B" >나무</option>
				<option value="C" >잎새</option>
				<option value="D" selected>새싹</option>
			</select>
		</td></tr>
	<tr height="40"><td bgcolor="aqua">Birthday</td>
		<td><input type="date" name="birthd" id="birthd"><br>
			<span id="bMessage" class="eMessage"></span></td></tr>
	<tr height="40"><td bgcolor="aqua">Point</td>
		<td><input type="text" name="point" id="point" value="3000" size="20"><br>
			<span id="oMessage" class="eMessage"></span></td></tr>
	<tr height="40"><td bgcolor="aqua">Weight</td>
		<td><input type="text" name="weight" id="weight" value="66.77" size="20"><br>
			<span id="wMessage" class="eMessage"></span></td></tr>
	<tr height="40"><td bgcolor="aqua">추천인ID</td>
		<td><input type="text" name="rid" id="rid" size="20"></td>
	</tr>		
	<tr height="40"><td bgcolor="aqua">Image</td>
		<td><img src="" class="select_img"><br>
			<input type="file" name="uploadfilef" id="uploadfilef">
			<script>
			// 해당 파일의 서버상의 경로를 src로 지정하는것으로는 클라이언트 영역에서 이미지는 표시될수 없기 때문에
			// 이를 해결하기 위해 FileReader이라는 Web API를 사용
			// => 이 를 통해 url data를 얻을 수 있음.
			//    ( https://developer.mozilla.org/ko/docs/Web/API/FileReader)
			// ** FileReader
			// => 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는File
			//    혹은 Blob 객체를 이용해 파일의 내용을(혹은 raw data버퍼로) 읽고 
			//    사용자의 컴퓨터에 저장하는 것을 가능하게 해줌.	
			// => FileReader.onload 이벤트의 핸들러.
			//    읽기 동작이 성공적으로 완료 되었을 때마다 발생.
			// => e.target : 이벤트를 유발시킨 DOM 객체
	  		
				$('#uploadfilef').change(function(){
					if(this.files && this.files[0]) {
						var reader = new FileReader;
				 			reader.onload = function(e) {
			 				$(".select_img").attr("src", e.target.result)
			 					.width(100).height(100); 
			 				} // onload_function
			 				reader.readAsDataURL(this.files[0]);
			 		} // if
				}); // change			
			</script>
		</td>
	</tr>		
	<tr height="40"><td></td>
		<td><input type="submit" value="가입" onclick="return inCheck()" id="submit" disabled>&nbsp;&nbsp;
			<input type="reset" value="취소">&nbsp;&nbsp;
			<span id="axjoin" class="textLink">AxJoin</span>
		</td></tr>			
</table>
</form>
<c:if test="${not empty message}">
<br>=> ${message}<br><br> 
</c:if>
<hr>
<a href="home">[Home]</a>
</body>
</html>