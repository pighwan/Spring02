<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Mybatis MemberUpdate Form **</title>
<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
<script src="resources/myLib/jquery-3.2.1.min.js"></script>
</head>
<body>
<h3>** Spring Mybatis MemberUpdate Form **</h3>
<form action="mupdate" method="post" enctype="multipart/form-data">
<table>
	<tr height="40"><td bgcolor="Lavender">I D</td>
		<td><input type="text" name="id" value="${apple.id}" size="20" readonly></td></tr>
			<!-- ** input Tag 입력 막기 
				=> disabled :  서버로 전송 안됨 
				=> readonly :  서버로 전송 됨   -->
	<tr height="40"><td bgcolor="Lavender">Password</td>
		<td><input type="password" name="password" value="${apple.password}" size="20"></td></tr>	
	<tr height="40"><td bgcolor="Lavender">Name</td>
		<td><input type="text" name="name" value="${apple.name}" size="20"></td></tr>
	<tr height="40"><td bgcolor="Lavender">Level</td>
	<%-- ${apple.lev} 에 따라서 해당되는 option 을 selected --%>
		<td><select name="lev">
		    <c:choose>
			<c:when test="${apple.lev=='A'}">
				<option value="A" selected>관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
			</c:when>
			<c:when test="${apple.lev=='B'}">
				<option value="A">관리자</option>
				<option value="B" selected>나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
			</c:when>
			<c:when test="${apple.lev=='C'}">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C" selected>잎새</option>
				<option value="D">새싹</option>
			</c:when>
			<c:when test="${apple.lev=='D'}">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D" selected>새싹</option>
			</c:when>
		    </c:choose>
			</select>
		</td></tr>
	<tr height="40"><td bgcolor="Lavender">Birthday</td>
		<td><input type="date" name="birthd" value="${apple.birthd}"></td></tr>
	<tr height="40"><td bgcolor="Lavender">Point</td>
		<td><input type="text" name="point" value="${apple.point}" size="20"></td></tr>
	<tr height="40"><td bgcolor="Lavender">Weight</td>
		<td><input type="text" name="weight" value="${apple.weight}" size="20"></td></tr>
	<tr height="40"><td bgcolor="aqua">추천인ID</td>
		<td><input type="text" name="rid" id="rid" value="${apple.rid}" size="20"></td>
	</tr>		
	<tr height="40"><td bgcolor="aqua">Image</td>
		<td><img src="${apple.uploadfile}" class="select_img">
			<input type="hidden" name="uploadfile" value="${apple.uploadfile}"><br>
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
		<td><input type="submit" value="수정">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td></tr>			
</table>
</form>
<c:if test="${not empty message}">
<br>=> ${message}<br><br> 
</c:if>
<hr>
<a href="home" >[Home]</a>
</body>
</html>