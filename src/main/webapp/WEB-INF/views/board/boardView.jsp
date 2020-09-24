<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/top" />

<div align="center" class="section">
<div class="row">
	<div class="col-md-10 offset-md-1">
	<hgroup>
		<h1>글 내용 보기</h1>
	</hgroup>
	<br>
	<c:if test="${board eq null }">
		<h3>존재하지 않는 글입니다.</h3>
	</c:if>
	<c:if test="${board ne null }">
	<table class="table table-bordered">
		<tr>
			<td width="20%">글번호</td>
			<td width="30%">
				<c:out value="${board.idx}"/>
			</td>
			<td width="20%">작성일</td>
			<td width="30%">
				<fmt:formatDate value="${board.wdate}"
				 pattern="yyyy-MM-dd hh:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td width="20%">글쓴이</td>
			<td width="30%">
				<c:out value="${board.name}"/>
			</td>
			<td width="20%">조회수</td>
			<td width="30%">
				<c:out value="${board.readnum}"/>
			</td>
		</tr>
		<tr>
			<td width="20%">제목</td>
			<td colspan="3" align="left">
				<c:out value="${board.subject}"/>
			</td>
		</tr>
		<tr height="60">
			<td width="20%">글내용</td>
			<td colspan="3" align="left">
				<pre><c:out value="${board.content}"/></pre>
			</td>
		</tr>
		<tr>
			<td width="20%">첨부파일</td>
			<td colspan="3">
				<a onclick="fDown('${board.filename}','${board.originFilename}')">
				<c:out value="${board.originFilename}"/>
				</a> [ <c:out value="${board.filesize}"/>bytes]
				  
		<c:set var="fname" value="${fn:toLowerCase(board.filename)}"/>		
		<c:if test="${fn:endsWith(fname,'.jpg') or fn:endsWith(fname,'.png') }">
					<img width="80px" class="img img-thumbnail"
					 src="../Upload/${board.filename}">
				</c:if>	
						
				
				</td>
		</tr>

		<tr>
			<td colspan="4" align=center>
			<a href="input">글쓰기</a>|
			<a href="list">목록</a>| <a
				href="#" onclick="go('${board.idx}',1)">편집</a>| <a href="#"
				onclick="go('${board.idx}',2)">삭제</a>| <a
				href="javascript:goRe()">답변</a></td>
		</tr>
	</table>
	</c:if>
	
	<!--편집 또는 삭제 관련 form 시작----------------  -->
	<form name="vf">
		<input type="hidden" name="idx">
		<input type="hidden" name="mode">
	</form>	
	<!-- -------------------------------------------- -->
	<!-- 답변 달기 form 시작------------------------------- -->
	<form name="reF" action="rewrite" method="POST">
		<!-- hidden으로 부모글(원글)의 글번호(idx)와 제목 subject를 넘기자. -->
		<input type="hidden" name="idx" value="<c:out value="${board.idx}"/>">
		<input type="hidden" name="subject" value="<c:out value="${board.subject}"/>">
	</form>
	<!-- 첨부파일 다운로드 form시작 -->
	<form name="ff" action="fileDown" method="POST">
		<input type="hidden" name="filename" value="${board.filename}">
		<input type="hidden" name="origin"
		 value="${board.originFilename}">
	</form>
	
		</div>
	</div>	
</div>
<script type="text/javascript">
	var fDown=function(fname, origin){
		/* alert(fname);
		alert(origin); */
		ff.submit();
	}

	var goRe=function(){
		reF.submit();
	}


	var go=function(num, md){
			//vf폼의 idx에 삭제할 글번호를 setting
			vf.idx.value=num;
			if(md==1){//편집
				vf.mode.value="Edit";
			}else if(md==2){//삭제
				var yn=confirm(num+"번 글을 정말 삭제할까요?");
				if(!yn) return;
				vf.mode.value="Delete";
			}
			vf.action="boardPasswd";
			
			vf.method="GET";
			vf.submit();
		
	}
	
</script>

<c:import url="/foot" />

