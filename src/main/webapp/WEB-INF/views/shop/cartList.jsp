<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/top"/>

<h1 class="text-success text-center">${loginUser.name} [${loginUser.userid}]님 장바구니</h1>
<!-- 주문 폼 시작--------------------- -->
<form name="orderF" id="orderF" action="order.do">
	<table class="table table-striped">
		<thead>
		<tr class="info">
			<th class="text-center">상품번호</th>
			<th class="text-center">상품명</th>
			<th class="text-center">수량</th>
			<th class="text-center">단가</th>
			<th class="text-center">금액</th>
			<th class="text-center">삭제</th>
		</tr>
		</thead>
		<tbody>
		<c:if test="${cartList eq null or empty cartList}">
			<tr>
				<td colspan="6"><b>담긴 상품이 없습니다.</b></td>
			</tr>
		</c:if>
		<c:if test="${cartList ne null and not empty cartList}">
		<!-- ---------반복문-------------- -->
		<c:forEach var="cp" items="${cartList}" varStatus="st">
		<tr>
			<td>
			<label>
				<input type="checkbox" name="pnum" id="pnum${st.count}" value="${cp.pnum}">${cp.pnum}
			</label>
			</td>
			<td>
				${cp.pname}<br>
				<a href="../prodDetail.do?pnum=${cp.pnum}" target="_blank">
				<img src="../images/${cp.pimage1}" class="img-thumbnail"
				alt="${cp.pname }"
				style="width:140px"></a>
			</td>
			<td>
			<input type="number" name="oqty" id="oqty${st.count}" value="${cp.oqty}" min="1" max="50" size="3"> 개
			<button type="button" class="btn btn-info" onclick="cartEdit('${cp.cartNum}','${st.count}')">수정</button>
			</td>
			<td>
				<fmt:formatNumber value="${cp.saleprice}" pattern="###,###"/>원
				<br>
				<span class="badge badge-danger">${cp.point}</span>POINT
			</td>
			<td style="font-weight:bold">
				<fmt:formatNumber value="${cp.totalPrice}" pattern="###,###"/>원
				<br>
				<span class="badge badge-danger">${cp.totalPoint}</span>POINT
			</td>
			<td>
			<a class="btn btn-outline-danger" onclick="cartDel('${cp.cartNum}','${cp.pnum}')">삭제</a>
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<!-- ---------------------------- -->
		
		<tr>
			<td colspan="3">
				<h5>장바구니 총      액: 
				<span class="text-danger">
				<fmt:formatNumber value="${cartTotalPrice}" pattern="###,###"/>
				</span> 원</h5>
				<h5>장바구니 총포인트: 
				<span class="text-success">
				<fmt:formatNumber value="${cartTotalPoint}" pattern="###,###"/>
				</span> point</h5>
			</td>
			<td colspan="3">
				<button type="submit" class="btn btn-warning">주문하기</button>
				<button type="button" class="btn btn-success" 
				onclick="location.href='../index.do'">계속쇼핑</button>
			</td>
		</tr>
		
		</tbody>
	</table>
</form>
<!-- 주문폼 end--------------------- -->

<!-- 삭제 폼 시작-------------------- -->
<form name="df" action="cartDel">
	<input type="hidden" name="cartNum">
</form>

<!-- 수정 폼 시작-------------------- -->
<form name="ef" action="cartEdit">
	<input type="hidden" name="cartNum">
	<input type="hidden" name="oqty">
</form>
<!-- ---------------------------- -->
<script>
	function cartEdit(num,count){
		//alert(num+"/"+count);
		ef.cartNum.value=num;
		var qty=$('#oqty'+count).val(); //수정된 수량값을 알 수 있음
		//alert(qty);
		ef.oqty.value=qty;
		ef.method='post';
		ef.submit();
	}//----------------------------

	function cartDel(num, pnum){
		//alert(num);
		var yn=confirm(pnum+'번 상품을 정말 삭제하시겠습니까?');
		if(yn){
		df.cartNum.value=num;
		df.method='post';
		df.submit();
		}
	}
</script>

<c:import url="/foot"/>