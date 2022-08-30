<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
  <h5 class="my-0 mr-md-auto font-weight-normal">DocMall</h5>
  <nav class="my-2 my-md-0 mr-md-3">
  	
    <c:if test="${sessionScope.loginStatus == null }">
    <!-- 로그인 이전상태 -->
    <a class="p-2 text-dark" href="/member/login">LOGIN</a> |
    <a class="p-2 text-dark" href="/member/join">JOIN</a> |
    </c:if>
      
    <c:if test="${sessionScope.loginStatus != null }">
    <!--  로그인 이후상태 -->
    <a class="p-2 text-dark" href="/member/logout">LOGOUT[${sessionScope.loginStatus.mem_id}]</a> |
  	</c:if>
    
    <a class="p-2 text-dark" href="#">MYPAGE
    	<c:if test="${sessionScope.loginStatus != null }">
    		<span style="color:red;">point[${sessionScope.loginStatus.mem_point}]</span>
    	</c:if>
    </a> |
    <a class="p-2 text-dark" href="#">ORDER</a> |
    <a class="p-2 text-dark" href="#">CART</a>
  </nav>
</div>