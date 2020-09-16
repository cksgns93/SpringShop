<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/top"/>
<%-- <jsp:include page="../top.jsp" /> --%>
<script type="text/javascript" src="js/jquery.magnifier.js">
	
</script> 
	<div align="center" class="row">
		<c:if test="${prod eq null}">
			<h2 class="text-center text-danger">해당 상품은 존재하지 않아요</h2>
		</c:if>
		<c:if test="${prod ne null}">
			<div class="col-md-10 offset-md-1 table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th colspan="2"><h3 class="text-center">상품 정보</h3></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="center" width="50%">
								<%-- <a href="javascript:openPop('${prod.pimage1}')">  --%>
								<img src="images/${prod.pimage1}" class="img-fluid magnify"
								data-magnifyby="2" style="width: 70%;"> <!-- </a> -->
							</td>
							<td align="left" width="50%" style="padding:20px">
								<h4>
									<span class="badge badge-danger"> ${prod.pspec}</span>
								</h4> 상품번호: ${prod.pnum} <br> 상품이름: ${prod.pname} <br> 정가:<del>
									<fmt:formatNumber value="${prod.price}" pattern="###,###" />
								</del>원<br> 판매가:<span style="color: red; font-weight: bold">
									<fmt:formatNumber value="${prod.saleprice}" pattern="###,###" />
							</span>원<br> 할인율:<span style="color: red">${prod.percent}%</span><br> POINT:<b
								style="color: green">[${prod.point}]</b>POINT<br> <!-- form시작---------- -->
								<form name="frm" id="frm" method="GET">
									<!-- 상품번호를 hidden으로 넘기자------ -->
									<input type="hidden" name="pnum" value="${prod.pnum}">
									<input type="hidden" name="opnum" value="${prod.pnum}">
									<!-- -------------------------------- -->
									<label for="oqty">상품갯수</label> <input type="number" name="oqty"
										id="oqty" min="1" max="50" size="2" value="1">

								</form> <!-- form end------------ -->
								<button type="button" onclick="goCart()"
									class="btn btn-primary">장바구니</button>
								<button type="button" onclick="goOrder()"
									class="btn btn-warning">주문하기</button>
								<button type="button" onclick="goWish()" class="btn btn-danger">위시리시트</button>
							</td>
						</tr>
						<tr style="border: 0">
							<td align="center"><img src="images/${prod.pimage2}"
								class="img-fluid img-thumbnail" style="width: 70%;"></td>
							<td align="center"><img src="images/${prod.pimage3}"
								class="img-fluid img-thumbnail" style="width: 70%;"></td>
						</tr>
						<tr>
							<td colspan="2">
								<p>상품설명</p> <pre>${prod.pcontents}</pre>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	</c:if>
	</div>
	<script type="text/javascript">
	var goCart=function(){
		frm.action="user/cartAdd";
		frm.method='POST';
		frm.submit();
	}
	var goOrder=function(){
		frm.action="user/orderSheet";
		frm.submit();
	}
	var goWish=function(){
		frm.action="user/wish";
		frm.submit();
	}
	var openPop=function(pimage){
		//alert(pimage);
		var url="images/"+pimage;
		var obj=new Image();
		obj.src=url;
		var w=obj.width+100;
		var h=obj.height+100;
		window.open(url,'preview',"width="+w+",height="+h+",top=100,left=100");
	}
	</script>
<%-- 	<jsp:include page="../foot.jsp" /> --%>
<c:import url="/foot"/>