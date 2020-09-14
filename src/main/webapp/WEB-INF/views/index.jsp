<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/top" />
<c:import url="/carousel" />
<c:import url="/prodPspec" >
	<c:param name="pspec" value="HIT"/>
</c:import>

<c:import url="/prodPspec" >
	<c:param name="pspec" value="NEW"/>
</c:import>

<c:import url="/foot"/>