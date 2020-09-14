<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/top/"/>

<script type="text/javascript">
   var check=function(){
      if(sf.findType.value==''){
         alert('검색 유형을 선택하세요');
         sf.findType.focus();
         return;
      }
      if(sf.findType.value==4){
         
         /* if(!sf.mstate.value){
            alert('검색할 회원 유형을 선택하세요');            
            return;
         } */
      }else{
         if(!sf.findKeyword.value){
            alert('검색어를 입력하세요');
            sf.findKeyword.focus();
            return;
         }
      }
      sf.submit();
   }//check()-------------------
   var change=function(val){
      var obj1=document.getElementById('input');
      var obj2=document.getElementById('radio');
      if(val==4){//회원유형 검색
         obj2.style.display=''; //라디오버튼 보여주기
         obj1.style.display='none';//텍스트박스 감추기
         sf.keyword.disabled=true;
         //keyword파라미터가 서버로 넘어가지 않도록 disabled속성을
         //주자
         for(var i=0;i<sf.mstate.length;i++){         
            sf.mstate[i].disabled=false;
         }
      }else{
         sf.keyword.disabled=false;
         
         for(var i=0;i<sf.mstate.length;i++){         
            sf.mstate[i].disabled=true;
         }
         obj1.style.display='';
         obj2.style.display='none';
         //alert(sf.mstate.disabled);
      }
   }

</script>

<div class="row">
<div class="col-md-10 offset-md-1">
   <h1 class="text-center text-primary">User List Page</h1>
   <div class="row" style="margin-top:30px">
      
      <!-- 검색 form시작------------------ -->
     <!--  <form name="sf" action="userList"
       class="form-horizontal">      
      <div class="col-md-2 col-md-offset-1">
      <select name="findType" class="form-control"
       onchange="change(this.value)">
         <option value="">::검색유형::</option>
         <option value="1">회원명</option>
         <option value="2">아이디</option>
         <option value="3">연락처</option>
         <option value="4">회원상태</option>
      </select>
      </div>
      <div class="col-md-6" id="input">
      <input type="text" name="findKeyword" 
      placeholder="검색어를 입력하세요" class="form-control">
      </div>
      <div class="col-md-6" id="radio" style="display: none">
         <input type="radio" name="mstate" value="0"
          checked>일반회원
         <input type="radio" name="mstate"  value="-1">정지회원
         <input type="radio" name="mstate" value="-2">탈퇴회원
      </div>
      
      <div class="col-md-2">
      <button type="button" onclick="check()"
         class="btn btn-info">검색</button>
      </div>
      </form> -->
      <!-- ------------------------------ -->
      </div>   
   
   
   <table class="table table-striped" style="margin-top:40px">
      <thead>
         <tr class="bg-info">
            <th width="10%">번호</th>
            <th width="15%">이름</th>
            <th width="10%">아이디</th>
            <th width="15%">연락처</th>
            <th width="10%">가입일</th>
            <th width="15%">회원상태</th>
            <th width="10%">수정|삭제</th>
         </tr>
      </thead>
      <tbody>
      <c:if test="${userList eq null or empty userList}">
         <tr><td colspan="8">
            <b>서버 오류이거나 데이터가 없습니다.</b>
            </td>
         </tr>
         </c:if>
         <c:if test="${userList ne null and not empty userList}">
         <c:set var="mstateStr" value="회원"/>
         <c:forEach var ="user" items="${userList}"> 
         <c:choose>
         	<c:when test="${user.mstate eq 0}">
         		<c:set var="mstateStr" value="일반회원"/>
         	</c:when>
         	<c:when test="${user.mstate eq 1}">
         		<c:set var="mstateStr" value="정지회원"/>
         	</c:when>
         	<c:otherwise>
         		<c:set var="mstateStr" value="탈퇴회원"/>
         	</c:otherwise>
         	</c:choose>       
         <tr>
            <td>${user.idx}</td>
            <td>${user.name}</td>
            <td>${user.userid}</td>
            <td>${user.allHp}</td>
            <td>${user.indate}</td>
            <td>${mstateStr}</td>
            <td>
            <a onclick="goEdit('${user.idx}')"  href="#">수정</a>|
            <a onclick="goDel('${user.idx}')" href="#">삭제</a></td>
         </tr>
         </c:forEach>
      </c:if>
         <!-- --------------- -->
      <tr>      
         <td colspan="5" class="text-center">
            
         </td>   
         <td colspan="3">
            <b>회원수 총 :${userCount}명</b>
         </td>
      </tr>   
      </tbody>
   </table>
   <!-- 회원정보 수정 또는 삭제 처리 form----- -->
   <form name="uf" method="post">
      <input type="hidden" name="idx">
   </form>   
   <!-- ------------------------------------ -->
</div>
</div>
<script type="text/javascript">
   goEdit=function(idx){
      uf.idx.value=idx;
      uf.action="edit";
      uf.submit();
   }

   goDel=function(idx){
      var yn=confirm(idx+'번 회원정보를 정말 삭제할까요?');
      if(yn){
         uf.idx.value=idx;
         uf.action="delete";
         uf.submit();
      }
   }
</script>
<c:import url="/foot/"/>

