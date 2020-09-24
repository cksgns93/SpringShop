<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:import url="/top" />
<%
	String ctx=request.getContextPath();
%>
<script type="text/javascript">
	var check=function(){
		if(!sf.findType.value){
			alert('검색 유형을 선택하세요');
			sf.findType.focus();
			return false;
		}
		
		if(!sf.findKeyword.value){
			alert('검색어를 입력하세요');
			sf.findKeyword.focus();
			return false;
		}
		sf.submit();
	}


</script>

<div class="section">
<div class="row">
	<div align="center" id="bbs" class="col-md-10 offset-md-1">
	<h1>Spring Board</h1>
	<p>
		<a href="<%=ctx%>/board/write#bbs">글쓰기</a>| <a
			href="<%=ctx%>/board/list#bbs">글목록</a>
		<p>
		<!-- 검색 폼 시작--------------------- -->
			<form name="sf" action="find"  onsubmit="return check()">
		<div class="row m-4">
			<div class="col-md-2">
				<select name="findType" class="form-control">
					<option value="">::검색 유형::</option>
					<option value="1">글제목</option>
					<option value="2">작성자</option>
					<option value="3">글내용</option>
				</select>
			</div>
			<div class="col-md-7">
				<input type="text" name="findKeyword" class="form-control"
				placeholder="검색어를 입력하세요">
			</div>
			<div class="col-md-2">
				<button type="button" onclick="check()" class="btn btn-warning">검색</button>
			</div>	
		</div> <!--  row end -->
			</form>
			<!-- 검색 폼 끝---------------------- -->						
		
		
		<table class="table table-condensed table-striped">
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>글쓴이</th>
				<th>날짜</th>
				<th>조회수</th>
			</tr>
			<!-- ---------------------------- -->
			<c:forEach var="board" items="${boardList}">
				<tr>
					<td>
						${board.idx}
					</td>
					<td align="left">
					
					<a href="view?idx=${board.idx}">					
					${board.subject}						
					</a></td>
					<td>
					${board.name} 
					</td>
					<td>	
						<fmt:formatDate value="${board.wdate}" pattern="yyyy-MM-dd"/>
					</td>
					<td>
						${board.readnum}
					</td>
				</tr>
			</c:forEach>
			<!-- ----------------------------- -->
			<tr>
				<td colspan="3" class="text-center">
					<ul class="pagination jutify-content-center">
						<c:forEach var="i" begin="1" end="${pageCount}">
							<li class="page-item">
								<a class="page-link" href="list?cpage=${i}">${i}</a>
							</li>
						</c:forEach>						
					</ul>
				</td>
				<td colspan="2">총게시물수:
				<span class="text-danger" style="font-weight:bold">${totalCount}개</span> 
				<br>
				 <span  class="text-danger" style="font-weight:bold" >${cpage}</span>
				 /${pageCount} pages
				</td>
			</tr>
		</table>
	</div>
</div>	
<c:import url="/foot" />