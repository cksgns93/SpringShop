<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!-- errorAlert.jsp -->
<%
	response.setStatus(200);
%>
<script>
	alert('<%=exception.getMessage()%>');
	//location.href='../index.jsp';
	history.back();
	<%
		exception.printStackTrace(); //서버에 예외기록 출력
	%>
</script>