<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/top"/>
<script type="text/javascript" src="../js/stupidtable.min.js">
</script>
<script>
	$(function(){
		$('#productTable').stupidtable();
	})
</script>
<div class="text-left p-5">
	<h1>Product List [Admin]</h1>
	<table id="productTable" class="table table-striped mt-4">
		<thead>
			<tr class="bg-warging">
				<th data-sort="int">상품번호</th>
				<th>카테고리</th>
				<th data-sort="string">상품명</th>
				<th data-sort="int">정가/판매가</th>
				<th>제조사</th>
				<th>편집|삭제</th>
			</tr>
		</thead>
		<tbody>
			<!-- --------------- -->
			<c:if test="${prodList eq null or empty prodList}">
				<tr>
					<td colspan="6"><b>데이터가 없습ㄴ디ㅏ.</b>
				</tr>
			</c:if>
			<c:if test="${prodList ne null and not empty prodList}">
			<c:forEach items="${prodList}" var="prod" varStatus="st">
			<tr id="pd${st.count}">
				<td>${prod.pnum}</td>
				<td>${prod.allCategory}</td>
				<td><h4>${prod.pname}</h4><br>
					<a href="../prodDetail.do?pnum=${prod.pnum}"><img style="width:200px" src="../images/${prod.pimage1}"></a>
				</td>
				<td>${prod.price}원<br>
					<span class="text-danger">${prod.saleprice}</span>
					<span class="badge badge-info">${prod.percent}%할인</span>
				</td>
				<td>${prod.pcompany}</td>
				<td><a>편집</a>|<a href="#pd${st.count}" onclick="del('${prod.pnum}')">삭제</a></td>
			</tr>
			</c:forEach>
			</c:if>
			<!-- --------------- -->
		</tbody>
	</table>
	<table class="table">
		<tr>
			<td style="width:60%">pageNavi</td>
			<td style="width=:40%"><b>총 상품갯수:${totalCount}개</b></td>
		</tr>
	</table>
</div>
<!-- 삭제 또는 수정 form 시작 -->
<form name="pf" id="pf">
	<input type="hidden" name="pnum" id="pnum">
</form>

<script>
	function del(pnum){
		var yn=confirm(pnum+"번 상품을 정말 삭제할까요?");
		if(!yn)	return;
		pf.pnum.value=pnum;
		pf.action="prodDel.do";
		pf.method='post';
		pf.submit();
	}
</script>
<c:import url="/foot"/>
