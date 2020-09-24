<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/top" />

<script type="text/javascript">
	$(function() {
		$('#btnWrite').click(function() {
			if (!$('#subject').val()) {
				alert('글제목을 입력하세요');
				$('#subject').focus();
				return;
			}
			if (!$('#name').val()) {
				alert('글쓴이를 입력하세요');
				$('#name').focus();
				return;
			}

			if (!$('#bpwd').val()) {
				alert('비밀번호를 입력하세요');
				$('#bpwd').focus();
				return;
			}

			$('#bf').submit();
		});
	});
</script>

<%
	String ctx = request.getContextPath();
%>
<div class="section">
<div class="row">
<div align="center" id="bbs" class="col-md-10 offset-1">
	<h1>Spring Board 답변 쓰기</h1>
	<p>
		<a href="<%=ctx%>/board/write#bbs">글쓰기</a>| <a
			href="<%=ctx%>/board/list#bbs">글목록</a>
		<p>
			<!--파일 업로드시
	method: POST
	enctype: multipart/form-data  
	
	[1] 글쓰기 =>  input [mode=write]
	[2] 글수정 =>  input [mode=Edit]
	[3] 답글쓰기=> input [mode=rewrite]
	-->	

	<form name="bf" id="bf" role="form" action="write"
		method="POST" enctype="multipart/form-data">
	<!-- 부모글의 글번호(idx)와 mode값(rewrite)를 hidden데이터로 넘기자 -->
	<input type="hidden" name="idx" value="<c:out value="${idx}"/>">
	<input type="hidden" name="mode" value="rewrite">	
	<!-- ------------------------------------------ -->	 	
 	<table class="table table-bordered">
 		<tr>
 			<td style="width:20%"><b>제목</b></td>
 			<td style="width:80%">
 			<input type="text" name="subject"
 			 value="[RE]<c:out value="${subject}"/>"
 			 id="subject" class="form-control">
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>글쓴이</b></td>
 			<td style="width:80%">
 			<input type="text" name="name" id="name" class="form-control">
 			</td>
 		</tr> 		
 		<tr>
 			<td style="width:20%"><b>글내용</b></td>
 			<td style="width:80%">
 			<textarea name="content" id="content" rows="10" cols="50"
						class="form-control"></textarea>
 			</td>
 		</tr>
 		<tr>
 			<td style="width:20%"><b>비밀번호</b></td>
 			<td style="width:80%">
 			<div class="col-md-5">
 			<input type="password" name="pwd" id="bpwd" class="form-control">
 			</div>
 			</td>
		</tr>
		<tr>
			<td style="width: 20%"><b>첨부파일</b></td>
			<td style="width: 80%">
			<input type="file" name="mfilename"
				id="filename" class="form-control"></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="btnWrite" class="btn btn-success">글쓰기</button>
				<button type="reset" id="btnReset" class="btn btn-warning">다시쓰기</button>
			</td>
		</tr>
	
		</table>
	

</form>	
</div>
</div>
</div>
<c:import url="/foot"/>

