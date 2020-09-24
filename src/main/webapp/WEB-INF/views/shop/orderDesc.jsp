<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--  -->
<c:import url="/top"/>
<div class="section">
<div class="row">
<div align="center" class="col-md-10 offset-md-1">
<hgroup>
	<h1>주문 내역 정보</h1>
</hgroup>

	<br/>
	<%-- ${orderDesc} --%>
	<c:set var="order" value="${orderDesc[0]}"/>
	
	<table width="95%" border="0" class="table">
		<tr height="30" bgcolor="#ffefef">
			<th colspan="4" class="text-center">
			<span class="glyphicons glyphicons-notes"></span>
			<h3>${loginUser.name}
			[${loginUser.userid}]님의 주문 내역서</h3>
			</th>
		</tr>
		<tr>
			<td>주문번호</td>
			<td><b>${onum}</b></td>
			<td>수령자</td>
			<td><b>${order.receiver.name}</b></td>
		</tr>		
	
		<tr>
			<td>총구매가격</td>
			<td><b style="color:red">
		<fmt:formatNumber value="${order.ototalPrice}"
		 pattern="###,###"/>	
				원</b>
		<br>[사용포인트:<b>${order.opointUse}</b>POINT]		
				</td>
			<td>총누적 포인트</td>
			<td><b>${order.ototalPoint} POINT</b></td>
		</tr> 
		<tr>
			<td>연락처</td>
			<td><b>${order.receiver.allHp}
			</b></td>
			<td>메모</td>
			<td><b>${order.orderMemo}</b></td>
		</tr>
		<tr>
			<td>배송지</td>
			<td colspan="3">
			<b>
			[${order.receiver.zipcode}]
			${order.receiver.allAddr}
			</b>
			</td>			
		</tr>
	</table>
	<br/>
	<table width="95%" class="table table-bordered">
		<tr bgcolor="#ffefef" height="30">
			<th>상품번호</th>
			<th>상품명</th>
			<th>판매가</th>
			<th>수량</th>
			<th>소계금액</th>
			<th>지불상태</th>
			<th>배송상태</th>
		</tr>
		<!-- ---------------- -->
		<c:forEach var="pd" items="${order.orderList}">
		<tr>
			<td>${pd.pnum}</td>
			<td align="center">
	<a href="../prodDetail?pnum=${pd.pnum}" target="_blank">
	<img src="../images/${pd.pimage1}"
			 width="80" height="80" /></a>
			<br/> ${pd.pname }
			</td>
			<td align="right">
			<fmt:formatNumber value="${pd.saleprice}" pattern="###,###"/>
			원
			</td>
			<td>${pd.pqty}개</td>
			<td align="right">
			<fmt:formatNumber 
			value="${pd.pqty*pd.saleprice}" pattern="###,###"/>
			원
			</td>
			<td>${order.opayState}</td>
			<td>${order.odeliver}</td>
		</tr>
		</c:forEach>
		<!-- ---------------- -->
	
	</table>	
</div>
</div>
</div>
<c:import url="/foot"/>