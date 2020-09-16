<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/top"/>

<h1 class="text-success text-center">${loginUser.name} [${loginUser.userid}]님 장바구니</h1>
<!-- 주문 폼 시작--------------------- -->
<form name="orderF" id="orderF" action="orderSheet">
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
				<input type="checkbox" name="opnum" id="pnum${st.count}" value="${cp.pnum}">${cp.pnum}
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
				<button type="button" onclick="goOrder()" class="btn btn-warning">주문하기</button>
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
	/*체크박스에 체크한 상품(상품번호,주문수량)을 가지고 주문 폼 페이지로 이동*/
	function goOrder(){
		//1. 장바구니에 담긴 상품이 없는 경우
		var chk=$('input[name="opnum"]');
		if(chk.length==0){
			return;
		}
		//2. 담긴 상품이 있다면 체크박스 갯수 만큼 반복문 돌면서 체크한 상품과 체크 안된 상품을 구분하여, 체크 안된 상품의 주문 수량은 서버쪽에 적송되지 않도록 disabled 처리한다.
		var cnt =0;
		$.each(chk,function(i,ch){
			if($(ch).is(":checked")){
				cnt++;
				$('#oqty'+(i+1)).prop('disabled',false);//비활성화
			}else{
				//체크 안된 상품의 주문 수량 비활성화 
				$('#oqty'+(i+1)).prop('disabled',true);//비활성화
			}
		});

		if(cnt==0){
			alert('주문할 상품을 체크하세요');
			$('input[name="oqty"]').prop('disabled',false);//비활성화
			return;	
		}
		orderF.submit();
	}

	
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