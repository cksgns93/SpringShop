<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <!-- The Modal -->
  <div class="modal" id="myLoginModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="${pageContext.request.contextPath}/login" method="POST">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title text-primary text-center">Login</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button> 
          <!-- 창닫기: class="close" data-dismiss="modal"-->
        </div>
  
        <!-- Modal body -->
        <div class="modal-body">
            <div class="row m-3">
                <div class="col-md-3 text-rigth">
         <label for="userid">아이디</label>
            </div>
            <div class="col-md-9">
         <input type="text" class="form-control" name="userid" id="userid" placeholder="User Id">
        </div>
        </div>
        <div class="row m-3">
            <div class="col-md-3 text-rigth">
     <label for="userPwd">비밀번호</label>
        </div>
        <div class="col-md-9">
     <input type="text" class="form-control" autofocus="autofocus" name="Pwd" id="userPwd" placeholder="Password">
    </div>
    </div>
    <div class="row m-3">
        <div class="col-md-12 text-right form-check">
            <label class="form-check-label">
                <input type="checkbox" class="form-check-input" name="saveId" id="saveId">아이디 저장
            </label>
            </div>
        </div>
    </div>
        <!-- Modal footer -->
        <div class="modal-footer">
            <button class="btn btn-success">로그인</button>
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
        </form>
        <!-- form end -->
      </div>
    </div>
  </div>
    </div>