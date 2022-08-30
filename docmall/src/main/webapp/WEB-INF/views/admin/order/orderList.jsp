<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Starter</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <!--Admin LTE css파일들  -->
  <%@include file="/WEB-INF/views/admin/include/plugin1.jsp" %>
  
 
  
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <%@include file="/WEB-INF/views/admin/include/header.jsp" %>
  <!-- Left side column. contains the logo and sidebar -->
  <%@include file="/WEB-INF/views/admin/include/nav.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Page Header
        <small>Optional description</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

     <div class="row">
     	<div class="col-md-12">
     		<div class="box box-primary">
     			<div class="box-header">
     				LIST ORDER
     			</div>
     			<div class="box-body">
     				<!-- 1)검색폼 -->
     				<form id="searchForm" action="/admin/order/orderList" method="get">
					  <select name="type">
						  <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
						  <option value="O" <c:out value="${pageMaker.cri.type eq 'O' ? 'selected' : '' }" />>주문번호</option>
						  <option value="M" <c:out value="${pageMaker.cri.type eq 'M' ? 'selected' : '' }" />>주문자ID</option>
						  <%--<option value="NC" <c:out value="${pageMaker.cri.type eq 'NC' ? 'selected' : '' }" />>상품명 or 제조사</option> --%>
					  </select>
					  <input type="text" name="keyword" value="${pageMaker.cri.keyword }">
					  <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					  <input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					  
					   주문일자 <input type="date" name="startDate" value="${startDate}"> ~ <input type="date" name="endDate" value="${endDate}"> 
					  
					  <button type="button" id="btnSearch" class="btn btn-link">Search</button>
  					</form>
  					<button type="button" name="btnCheckedOrderDelete" class="btn btn-link">선택삭제</button>
  <table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col"><input type="checkbox" id="checkAll" name="checkAll"></th>
	      <th scope="col">주문번호</th>
	      <th scope="col">주문일시</th>
	      <th scope="col">주문자ID/수령인</th>
	      <th scope="col">주문금액/배송비</th>
	      <th scope="col">결제상태</th>
	      <th scope="col">배송상태</th>
	      <th scope="col">주문관리</th>
	    </tr>
	  </thead>
	  <tbody>
	    <c:forEach items="${orderList }" var="orderVO">
	    <!-- BoardVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨. -->
	    <tr>
	      <td scope="row"><input type="checkbox" class="check" value="${orderVO.odr_code }"></td>
	      <td><c:out value="${orderVO.odr_code }" /></td>
	      <td>
	      	<fmt:formatDate value="${orderVO.odr_date }" pattern="yyyy-MM-dd hh:mm" />
	      </td>
	      <td><c:out value="${orderVO.mem_id }" /> / <c:out value="${orderVO.odr_name }" /></td>	      
	      <td><c:out value="${orderVO.odr_total_price }" /></td>
	      <td><c:out value="${orderVO.payment_status }" /></td>
	      <td>
	      	<select name="odr_status">
	      		<option value="주문접수" ${orderVO.odr_status eq '주문접수'? 'selected':'' }>주문접수</option>
	      		<option value="결제완료" ${orderVO.odr_status eq '결제완료'? 'selected':'' }>결제완료</option>
	      		<option value="배송준비중" ${orderVO.odr_status eq '배송준비중'? 'selected':'' }>배송준비중</option>
	      		<option value="배송처리" ${orderVO.odr_status eq '배송처리'? 'selected':'' }>배송처리</option>
	      		<option value="배송완료" ${orderVO.odr_status eq '배송완료'? 'selected':'' }>배송완료</option>
	      		<option value="주문취소" ${orderVO.odr_status eq '주문취소'? 'selected':'' }>주문취소</option>
	      		<option value="취소요청" ${orderVO.odr_status eq '취소요청'? 'selected':'' }>취소요청</option>
	      		<option value="취소완료" ${orderVO.odr_status eq '취소완료'? 'selected':'' }>취소완료</option>
	      		<option value="교환요청" ${orderVO.odr_status eq '교환요청'? 'selected':'' }>교환요청</option>
	      		<option value="교환완료" ${orderVO.odr_status eq '교환완료'? 'selected':'' }>교환완료</option>
	      	</select>
	        <button type="button" name="btnChangeOrderStatus" data-odr_code="${orderVO.odr_code }" class="btn btn-link">변경</button>
	      
	      
	      
	      </td>
	      <td><button type="button" name="btnOrderDetail" data-odr_code="${orderVO.odr_code }" class="btn btn-link">Detail</button></td>
	    </tr>
	    </c:forEach>
	    
	  </tbody>
	</table>
	<nav aria-label="...">
	  <ul class="pagination">
	    <!-- 이전표시 -->
	    <c:if test="${pageMaker.prev }">
		    <li class="page-item">
		      <a class="page-link" href="${pageMaker.startPage - 1 }">Previous</a>
		    </li>
	    </c:if>
	    
	    <!-- 페이지번호 표시.  1  2  3  4  5 -->
	    
	    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="num" >
	    	<li class='page-item ${pageMaker.cri.pageNum == num ? "active": "" }'><a class="page-link" href="${num}">${num}</a></li>
	    </c:forEach>
	    <!-- 
	    <li class="page-item active" aria-current="page">
	      <span class="page-link">2</span>
	    </li>
	    <li class="page-item"><a class="page-link" href="#">3</a></li>
	     -->
	    <!-- 다음표시 -->
	    <c:if test="${pageMaker.next }">
		    <li class="page-item">
		      <a class="page-link" href="${pageMaker.endPage + 1 }">Next</a>
		    </li>
	    </c:if>
		
	  </ul>
	  <!--1)페이지 번호 클릭시 2)상품수정버튼 클릭시 3)상품삭제버튼 클릭시-->
		<form id="actionForm" action="/admin/order/orderList" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
			<input type="hidden" name="type" value="${pageMaker.cri.type}">
			<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
      		<input type="hidden" name="startDate" value="${startDate}">
			<input type="hidden" name="endDate" value="${endDate}">
		</form>
	</nav>
     			</div>
     		</div>
     	</div>
     </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
<%@include file="/WEB-INF/views/admin/include/footer.jsp" %>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane active" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                <p>Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="pull-right-container">
                    <span class="label label-danger pull-right">70%</span>
                  </span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 3, Bootstrap 3.3.7, AdminLTE App -->
<%@include file="/WEB-INF/views/admin/include/plugin2.jsp" %>


<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->

<script>

  $(document).ready(function(){

    // 배송상태 변경작업
    $("button[name='btnChangeOrderStatus']").on("click", function(){

      //주문번호, 선택한 배송상태값
      let odr_code = $(this).data("odr_code");
      let odr_status = $(this).parent().find("select[name='odr_status'] option:selected").val();

      console.log("주문번호: " + odr_code);
      console.log("주문상태: " + odr_status);

      $.ajax({
        url : '/admin/order/orderStatusChange',
        method : "get",
        data: { odr_code : odr_code, odr_status : odr_status},
        dataType: 'text',
        success: function(result) {
          if(result == "success") {
            alert("배송상태가 변경되었습니다.");
            
          }
        }
      });
    });

    //체크박스 선택. 제목행 체크박스 클릭
    let isCheck = true;
    $("#checkAll").on("click", function(){
      $(".check").prop("checked", this.checked); // attr()메서드 사용하면 체크처리 안됨.

      isCheck = this.checked;
    });

    // 데이터행 체크박스 클릭
    $(".check").on("click", function(){
      
      //제목행의 전체선택 체크박스
      $("#checkAll").prop("checked", this.checked);

      //데이터 행의 체크박스의 선택자 해당하는 만큼 동작하는 구문
      $(".check").each(function(){
        if(!$(this).is(":checked")) {
          $("#checkAll").prop("checked", false);
        }
      });
    });

    //선택삭제
    $("button[name='btnCheckedOrderDelete']").on("click", function(){

      if($(".check:checked").length == 0) {
        alert("삭제할 주문데이타를 체크하세요.");
        return;
      }

      let isOrderDel = confirm("주문상품을 삭제하시겠습니까?");
      if(!isOrderDel) return;

      // 자바스크립트 배열문법
      // 삭제할 주문번호 배열.
      let ordCodeArr = [];
      
      $(".check:checked").each(function(){
        ordCodeArr.push($(this).val());
      });

      console.log("선택된 주문번호: " + ordCodeArr);

      $.ajax({
        url : '/admin/order/orderCheckedDelete',
        type: 'post',
        dataType : 'text',
        data : {
          ordCodeArr : ordCodeArr
        },
        success : function(result) {
          if(result == "success") {
            alert("선택한 주문정보가 삭제되었습니다.");

            location.href= "/admin/order/orderList";
          }
        }
      });
    });
    
    
    
    
    
    
    
    //actionForm 참조 
    let actionForm = $("#actionForm");


    //1)주문상세 버튼 클릭
    $("button[name='btnOrderDetail']").on("click", function(){
      console.log("주문번호: " + $(this).data("odr_code"));


      actionForm.append("<input type='hidden' name='odr_code' value='" + $(this).data("odr_code")   + "'>");

      actionForm.attr("method", "get");
      actionForm.attr("action", "/admin/order/orderDetail");
      actionForm.submit();
    });

    
    //3)페이지번호 클릭
    $("ul.pagination li a.page-link").on("click", function(e){

      e.preventDefault(); // <a>태그의 링크기능 무력화

      let pageNum = $(this).attr("href");

      actionForm.find("input[name='pageNum']").val(pageNum);

      //pageNum 필드는 actionForm에 수동으로 작업되어 있어, 추가하는 것이 아니라, 참조하여 값을 변경한다.

      actionForm.attr("method", "get");
      actionForm.attr("action", "/admin/order/orderList");
      actionForm.submit();

     
    });

    
    let searchForm = $("#searchForm");
    //검색버튼 클릭시 pageNum 초기화
    $("#btnSearch").on("click", function(){

      searchForm.find("input[name='pageNum']").val(1);
      searchForm.submit();
    });
  });

</script>
    </body>
</html>