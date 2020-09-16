<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/top"/>
<script>
	function selectDCate(upCode){
		//alert(upCode);
		//ajax로 상위 항목을 가져오자
		$.ajax({
			type:'get',
			url:'downCate.do?upCode='+upCode,
			dataType:'json',
			cache:false,
			success:function(res){
				//alert(JSON.stringify(res));
				var str="<select id='downCg_code' name='downCg_code'>";
				str+="<option value=''>::하위 카테고리::</option>";
				$.each(res,function(i,item){
					str+="<option value='"+item.downCg_code+"'>";
					str+=item.downCg_name;
					str+="</option>";
				})
				str+="</select>";
				$('#selectDcg').html(str);
			},error:function(err){
				alert("error:"+err.status);
			}
		})
	}
	
	$(function(){
		$('#prodF').on('submit',function(){
			//상위 카테고리코드, 하위 카테고리 코드
			if(!$("#upCg_code").val()){
				alert('상위 카테고리 선택하세요');
				$("#upCg_code").focus();
				return false;
			}
			if(!$("#downCg_code").val()){
				alert('하위 카테고리 선택하세요');
				$("#downCg_code").focus();
				return false;
			}
			//상품명
			if(!$('#pname').val()){
				alert('상품명을 입력');
				$('#pname').focus();
				return false;
			}
			//가격, 판매가,포인트 숫자 여부
			//숫자 여부를 하면 빈문자까지 확인 가능
			
			if(!($('#price').val())){
				alert('정가를 입력');
				$('#price').focus();
				return false;
			}
			if(isNaN($('#price').val())){
				alert('정가는 숫자여야 합니다.');
				$('#price').select();
				return false;
			}	
			if(!($('#saleprice').val())){
				alert('판매가를 입력');
				$('#saleprice').focus();
				return false;
			}
			if(isNaN($('#saleprice').val())){
				alert('판매가는 숫자여야 합니다.');
				$('#saleprice').select();
				return false;
			}
		})
	})
	
</script>

<div class="container">
	<div class="row">
		<div class="col-md-10 offset-md-1 text-left">

			<h1>상품 등록[ADMIN모드]</h1>
			
			<form name="prodF" id="prodF" action="prodInsert" method="POST"
				enctype="multipart/form-data">
				<table class="table table-condensed table-bordered mt-4">
					<thead>
						<tr class="bg-success">
							<th colspan="2">
								<h3>:::Product Register:::</h3>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td width="20%"><b>카테고리</b></td>
							<td width="80%"><select id="upCg_code" name="upCg_code"
								onchange="selectDCate(this.value)">
									<option value="">::상위 카테고리::</option>
									<c:forEach var="up" items="${upCgList}">
									<option value="${up.upCg_code}">${up.upCg_name}</option>
									</c:forEach>
							</select> 
							<span id="selectDcg"> 
								<!-- <select name="downCode">
									<option value="1">노트북.PC</option>
								</select> -->
							</span></td>
						</tr>
						<tr>
							<td width="20%"><b>상품명</b></td>
							<td width="80%"><input type="text" name="pname" id="pname">
								<span style="color: red"> 
							</span></td>
						</tr>
						<tr>
							<td width="20%"><b>제조사</b></td>
							<td width="80%"><input type="text" name="pcompany"
								id="pcompany"></td>
						</tr>
						<tr>
							<td width="20%"><b>상품스펙</b></td>
							<td width="80%"><select name="pspec" id="pspec">
									<option value="NEW" selected>NEW</option>
									<option value="HIT">HIT</option>
									<option value="BEST">BEST</option>
							</select></td>
						</tr>
						<tr>
							<td>상품이미지</td>
							<td>
							<input type="file" name="pimage1"><br> 
							<input type="file" name="pimage2"><br> 
							<input type="file" name="pimage3"><br>
						</td>
						</tr>

						<tr>
							<td width="20%"><b>상품수량</b></td>
							<td width="80%"><input type="number" name="pqty" id="pqty">
								개
					<span style="color: red"> 
					</span></td>
								</td>
						</tr>
						<tr>
							<td width="20%"><b>상품정가</b></td>
							<td width="80%">
							<input type="number" name="price" id="price">
		
								원
							<span style="color: red"> 
							
					</span>			
								</td>
						</tr>
						<tr>
							<td width="20%"><b>상품판매가</b></td>
							<td width="80%"><input type="text" name="saleprice"
								id="saleprice"> 원
								<span style="color: red"> 
							
					</span>	
								
								</td>
						</tr>
						<tr>
							<td width="20%"><b>상품설명</b></td>
							<td width="80%"><textarea name="pcontents" id="pcontents"
									rows="5" cols="60"></textarea></td>
						</tr>
						<tr>
							<td width="20%"><b>포인트</b></td>
							<td width="80%"><input type="number" name="point" id="point">
								POINT</td>
						</tr>
						<tr>
							<td colspan="2">
								<button class="btn btn-success">상품등록</button>
							</td>
						</tr>
					</tbody>
				</table>
			
		</div>
	</div>
</div>
<script>
	
</script>
<c:import url="/foot"/>





