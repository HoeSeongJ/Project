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
     				LIST ORDERDETAIL
     			</div>
     			<div class="box-body">
     				<h3>주문상세조회</h3>
     				<h5 style="color: red; font-weight: bold;">[주문정보]</h5>
  					<table class="table table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">주문번호</th>
					      <td scope="col">${orderVO.odr_code }</td>					      
					    </tr>
					  </thead>
					  <tbody>
					    <tr>
					      <th scope="col">주문일자</th>
					      <td scope="col"><fmt:formatDate value="${orderVO.odr_date }" pattern="yyyy-MM-dd hh:mm" /></td>
					    </tr>
					    <tr>
					      <th scope="col">주문자</th>
					      <td scope="col">${orderVO.mem_id }</td>
					    </tr>
					    <tr>
					      <th scope="col">주문처리상태</th>
					      <td scope="col">${orderVO.odr_status }</td>
					    </tr>
					  </tbody>
					</table>
					
					<h5 style="color: red; font-weight: bold;">[결제정보]</h5>
  					<table class="table table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">총 주문금액</th>
					      <td scope="col">${paymentVO.pay_tot_price }</td>					      
					    </tr>
					  </thead>
					  <tbody>
					    <tr>
					      <th scope="col">총 할인금액</th>
					      <td scope="col">0</td>
					    </tr>
					    <tr>
					      <th scope="col">총 결제금액</th>
					      <td scope="col">${paymentVO.pay_tot_price }</td>
					    </tr>
					    <tr>
					      <th scope="col">결제수단</th>
					      <td scope="col">${paymentVO.pay_method }</td>
					    </tr>
					  </tbody>
					</table>
					
					<h5 style="color: red; font-weight: bold;">[주문상품정보]</h5>
					<table class="table table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">이미지</th>
						  <th scope="col">상품정보</th>
						  <th scope="col">수량</th>
						  <th scope="col">상품구매금액</th>
						  <th scope="col">주문처리상태</th>
						  <th scope="col">취소/교환/반품</th>						      					      
					    </tr>
					  </thead>
					  <tbody>
					  <c:forEach items="${orderProductMap }" var="orderProduct">
					    <tr> <!-- MAP의 키를 대문자 사용해야 함. -->
					      <td scope="col">
					      <img src="/admin/order/displayFile?folderName=${orderProduct.Pdt_Img_Folder }&fileName=s_${orderProduct.Pdt_Img }" 
	      		alt="" style="width: 80px;height: 80px;" onerror="this.onerror=null; this.src='/image/no_images.png'">
					       
					      </td>
					      <td scope="col">${orderProduct.PDT_NAME }</td>
					      <td scope="col">${orderProduct.ODR_AMOUNT }</td>
					      <td scope="col">${orderProduct.ODR_UNIT_PRICE }</td>
					      <td scope="col">${orderProduct.ODR_STATUS }</td>
					      <td scope="col"><button type="button" name="btnOrderCancel" data-odr_code="${orderProduct.ODR_CODE }" data-pdt_num="${orderProduct.PDT_NUM }" data-odr_unitprce="${orderProduct.ODR_UNIT_PRICE }" class="btn btn-link">주문취소</button></td>
					    </tr>
					   </c:forEach> 
					  </tbody>
					</table>
					
	
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


    //주문상품 개별취소기능.  btnOrderCancel
    $("button[name='btnOrderCancel']").on("click", function(){


      if(!confirm("상품을 주문취소하겠습니까?")) return;

      let odr_code = $(this).data("odr_code");
      let pdt_num = $(this).data("pdt_num");
      let unit_price = $(this).data("odr_unitprce");

      console.log("주문번호: " + odr_code);
      console.log("상품번호: " + pdt_num);
      
      location.href = "/admin/order/orderUnitProductCancel?odr_code=" + odr_code + "&pdt_num=" + pdt_num + "&unit_price=" + unit_price ;


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
    
    
    
    
    
    
    
    //actionForm 참조 : 1)상품수정 2)상품삭제 3)페이지번호
    let actionForm = $("#actionForm");

    //1)상품수정버튼 클릭
    $("button[name='btnProductEdit']").on("click", function(){
      //console.log("상품코드: " + $(this).data("pdt_num"));

      //상품코드를 자식으로 추가
      actionForm.append("<input type='hidden' name='pdt_num' value='" + $(this).data("pdt_num")   + "'>");

      actionForm.attr("method", "get");
      actionForm.attr("action", "/admin/product/productModify");
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