<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
 uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 
uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/top"/>
<div class="row">

<div class="col-md-10 offset-md-1">
<h1 class="text-primary text-center mt-4">${loginUser.name}[ ${loginUser.userid} ]님의 주문정보</h1>

<table class="table table-responsive">
	<tr>
		<th colspan="5">주문 상품 정보</th>
	</tr>
	<tr bgcolor="#efefef" align="center">
		<td width="10%">상품번호</td>
		<td width="25%">상품명</td>
		<td width="30%">판매가</td>
		<td width="10%">수 량</td>
		<td width="25%">합계금액</td>
	</tr>
		<!-- --------------------- -->
		<c:set var="totalBuy" value="0"/> <!-- 총 주문가격 -->
		<c:set var="totalBuyPoint" value="0"/> <!-- 총 주문 누적포인트 -->
		<c:forEach var="p" items="${orderList}">
			<c:set var="totalBuy" value="${totalBuy+p.totalPrice}"/>
			<c:set var="totalBuyPoint" value="${totalBuyPoint+p.totalPoint}"/>
		<tr>
			<td>${p.pnum}</td>
			<td align="center">
			${p.pname}<br>
			<a href="../prodDetail?pnum=${p.pnum}" target="_blank"><img
				src="../images/${p.pimage1}"
				width="100" height="100" border="0" />
			</a>
			</td>
			<td align="right" style="padding-right:20px">
				<fmt:formatNumber value="${p.price }" pattern="###,###"/><br>
				[${p.point}] POINT
			</td>
			<td align="center">${p.pqty}개</td>
			<td  align="right" style="padding-right:20px">
			<b>
			<fmt:formatNumber value="${p.totalPrice}" pattern="###,###" />
			원<br />
			</b>
			<b>
			[${p.totalPoint}]POINT
			</b>
			</td>
		</tr>
		</c:forEach>
		<!-- ----------------------- -->

	<tr bgcolor="#efefef">
		<td colspan="2">주문일자<br />
		<b>
		<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd hh:mm:ss"/>
		</b>
		<br>
		<span style="font-size:1.5em;color:blue">배송비: 3,000원</span>
		</td>
		<td colspan="3" align="right">주문총금액: 
		<h2 class="text-primary">
		<fmt:formatNumber value="${totalBuy}" pattern="###,###" />원
		</h2><br/>
		적립 포인트: [<font color=blue><b> ${totalBuyPoint}</b></font>]POINT<br/>
		결제금액(총구매가격+배송비-사용포인트) : 
		<div class="text-warning" id="usePoint" style="font-weight:bold"></div>
		<h1 class="text-danger" id="total">결제금액:
		<fmt:formatNumber value="${totalBuy+3000}" pattern="###,###"/>원
		</h1>
		</td>
	</tr>
	<!-- --------------------- -->
</table>
<br />
<!-- form 시작=================== -->
<form name="custF" action="orderAdd" method="POST">
<!-- ----------------------------------------------------- -->
<input	type="text" name="ototalPrice" id="ototalPrice" value="${totalBuy}" />
<input	type="text" name="ototalPoint" id="ototalPoint" value="${totalBuyPoint}" />
<input type="text" name="odeliverPrice" value="3000">
<input type="text" name="idx_fk" value="${loginUser.idx}">
<!-- hidden으로 주문총액과 총포인트를 넘기자-------------------- -->
<!--  -->
<!-- 마일리지 사용 부가결제금액 -->
		<div id="pointInfo" style="margin-top:20px;">
			    <table class="table table-bordered" >			
                <!-- 적립금 -->
                <tbody>
                <tr>
				<th scope="row" width="150px">사용가능 적립금</th>
                <td style="padding-left:10px;">
                <p> 
	                <input name="opointUse" id="opointUse" type="text"
	                 class="text-right" value="0"
	                size="10" oninput="chkPoint(this,'${mileage}')" >
	                 원 (사용가능 적립금 :<span style="color:red;font-weight:bold">${mileage}  원</span> 
	                 <input type="checkbox" id="chkAll"
	                 onclick="useAllPoint('${mileage}')" >전부사용하기)
	                <input type="button" class="btn btn-success"
	                value="사용하기" 
	                onclick="calcToPrice(opointUse.value,'${totalBuy}')" >				
                </p>                 
                <p>
					적립금은 최소 0 이상일 때 결제가 가능합니다.
                     최대 사용금액은 제한이 없습니다.1회 구매시 적립금 최대 사용금액은 입니다.<br>
                     적립금으로만 결제할 경우, 결제금액이 0으로 보여지는 것은 정상이며
                     <b>[결제하기]</b> 버튼을 누르면 주문이 완료됩니다.<br>
                    적립금 사용 시 배송비는 적립금으로 사용 할 수 없습니다.
                </p>
			   </td>
               </tr>
               </tbody>
            	</table>            
			</div>


<table class="table table-bordered">
	<tr>
		<th colspan="2">배송지 정보
		<input type="radio" name="info" id="uinfo1" value="1" checked>주문자 정보와 동일
		<input type="radio" name="info" id="uinfo2" value="2">새로운 수령자 정보
		</th>
	</tr>
	<tr>
		<td><b>주문자</b></td>
		<td class="text-left">${loginUser.name} [ ${loginUser.userid} ]</td>
	</tr>
	<tr>
		<td><b>수령자</b></td>
		<td class="text-left">
			<input type="text" name="name" value="${loginUser.name}">
		</td>
	</tr>
	<tr>
		<td><b>연락처</b></td>
		<td class="text-left">
			<input type="text" name="hp1"
				value="${loginUser.hp1}"  size="3"
				maxlength="3" />
			- <input type="text" name="hp2" value="${loginUser.hp2}"
			size="4" maxlength="4" />- <input type="text" name="hp3"
			value="${loginUser.hp3}" size="4" maxlength="4" /></td>
	</tr>
	
	<tr>
		<td><b>우편번호</b></td>
		<td class="text-left">
		<div class="col-md-3">
		<input type="text" name="zipcode" value="${loginUser.post}" class="form-control"  maxlength="5" /> 
		<div>
		<div class="col-md-3 text-left">
		<input
			type="button" value="검  색" class="btn btn-warning" />
		</div>	
			</td>

	</tr>
	<tr>
		<td><b>주소</b></td>
		<td class="text-left">
		<input type="text" name="addr1" value="${loginUser.addr1}" class="form-control" /></td>
	</tr>
	<tr>
		<td><b>나머지 주소</b></td>
		<td class="text-left">
		<input type="text" name="addr2" value="${loginUser.addr2}" class="form-control" /></td>
	</tr>
	
	<tr>
		<td><b>배송시 요청사항</b></td>
		<td class="text-left">
		<input type="text" name="ordermemo"
		  class="form-control" /></td>
	</tr>
	<!-- 결제수단 -->
	<tr>
		<td><b>결제방법</b></td>
		<td class="text-left">
			<input type="radio" id="opayWay" name="opayWay" checked value="100" onclick="showSelect(this.value)">무통장입금
			<input type="radio" id="opayWay" name="opayWay" value="200"  onclick="showSelect(this.value)">카드 결제
			
			<span id="c1">
				<select name="bank" id="bank">
					<option value="1">국민</option>
					<option value="2">우리</option>
					<option value="3">신한</option>
				</select>
			</span>
			<span id="c2" style="display:none">
				<select name="card" id="card">
					<option value="1">국민카드</option>
					<option value="2">BC카드</option>
					<option value="3">현대카드</option>
					<option value="4">농협카드</option>
				</select>
			</span>
			
		</td>
	</tr>
	
	<tr>
		<td colspan="2"><input type="submit" value="주문결제"
		 class="btn btn-success"/></td>
	</tr>
	
</table>

</form>
<!-- ============================ -->
<script type="text/javascript">
	$(function(){
		$('#card').prop("disabled", true);
		$('#bank').prop("disabled", false);
	});
	function chkPoint(input, mileage){
		var tmpPoint = parseInt(input.value);
		if(tmpPoint>mileage){
			alert("사용 가능한 적립금액 보다 많습니다.");
			input.value="0";
			return;
		}
	}//--------------------------
	
	function useAllPoint(mileage){
		if($('#chkAll').is(':checked')){
			$('#opointUse').val(mileage);
		}else{
			$('#opointUse').val(0);
			$('#opointUse').select();
			$('#ototalPrice').val('${totalBuy}');
			$('#usePoint').html("사용포인트: 0 Point");
			var str="${totalBuy+3000}";
			$('#total').text(addCommaNum(parseInt(str)));
		}
		
	}//---------------------------

	function addCommaNum(x){
		return x.toLocaleString();
	}

	
	function calcToPrice(usePoint, totalBuy){
		//alert(usePoint+"/"+totalBuy);
		var price = totalBuy-usePoint;//총구매 가격에서 사용 포인트 차감
		if(price<0){
			alert('총구매가격보다 사용 포인트가 커요. \n사용포인트를 다시 입력하세요')
			$('#opointUse').select();
			return;
		}
		$('#ototalPrice').val(price);
		$('#usePoint').html("사용 포인트: "+usePoint+" POINT");
		var str=addCommaNum(price+3000);
		$('#total').html(str+"원");
	}//---------------------------
	

	function showSelect(pay){
		//alert(pay);
		
		if(pay=='100'){//무통장 입금
			$('#c1').show();
			$('#c2').hide();
			$('#bank').prop("disabled", false);//은행이 서버로 넘어가도록
			$('#card').prop("disabled", true);
		}else if(pay=='200'){
			//카드결제
			$('#c1').hide();
			$('#c2').show();
			$('#card').prop("disabled", false);//카드가 서버로 넘어가도록
			$('#bank').prop("disabled", true);
		}
	}

	$(function(){
		$('#uinfo1').click(function(){
			custF.name.value="${loginUser.name}";
			custF.hp1.value="${loginUser.hp1}";
			custF.hp2.value="${loginUser.hp2}";
			custF.hp3.value="${loginUser.hp3}";
			custF.zipcode.value="${loginUser.post}";
			custF.addr1.value="${loginUser.addr1}";
			custF.addr2.value="${loginUser.addr2}";
		});
		
		$('#uinfo2').click(function(){
			custF.name.value="";
			custF.hp1.value="";
			custF.hp2.value="";
			custF.hp3.value="";
			custF.zipcode.value="";
			custF.addr1.value="";
			custF.addr2.value="";
		});
	});

	
</script>
</div>
</div>
<c:import url="/foot"/>
