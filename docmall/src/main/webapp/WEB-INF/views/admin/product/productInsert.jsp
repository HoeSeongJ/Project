<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
     			<form id="productForm" action="productInsert" method="post" enctype="multipart/form-data">
     			<div class="box-header">
     				REGISTER PRODUCT
     			</div>
     			<div class="box-body">	
					  <div class="form-group row">
					    <label for="pdt_name" class="col-sm-2 col-form-label">카테고리</label>
					    <div class="col-sm-3">
					    	<select name="cate_code_prt" id="firstCategory" class="form-control">
					    		<option value="">1차카테고리 선택</option>
					    		<c:forEach items="${cateList }" var="categoryVO">
					    			<option value="${categoryVO.cate_code }">${categoryVO.cate_name }</option>
					    		</c:forEach>
					    	</select>
					    </div>
						<div class="col-sm-3">					    	
					    	<select name="cate_code" id="secondCategory" class="form-control">
					    		<option>2차카테고리 선택</option>
					    	</select>
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pdt_name" class="col-sm-2 col-form-label">상품명</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_name" name="pdt_name">
					    </div>
					    <label for="pdt_price" class="col-sm-2 col-form-label">상품가격</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_price" name="pdt_price">
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pdt_discount" class="col-sm-2 col-form-label">할인율</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_discount" name="pdt_discount">
					    </div>
					    <label for="pdt_company" class="col-sm-2 col-form-label">제조사</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_company" name="pdt_company">
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pdt_img" class="col-sm-2 col-form-label">상품이미지</label>
					    <div class="col-sm-4"> <!-- name="pdt_img" 존재안함.  스프링에서는 Null 이 됨.-->
					      <input type="file" class="form-control" id="uploadFile" name="uploadFile">
					    </div>
					    <label for="pdt_img" class="col-sm-2 col-form-label">미리보기 이미지</label>
					    <div class="col-sm-4">
					      <img id="change_img" style="width: 200px;height: 200px;">
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pdt_detail" class="col-sm-2 col-form-label">상품설명</label>
					    <div class="col-sm-10">
					      <textarea class="form-control" id="pdt_detail" name="pdt_detail" rows="3"></textarea>
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pdt_amount" class="col-sm-2 col-form-label">수량</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pdt_amount" name="pdt_amount">
					    </div>
					    <label for="pdt_buy" class="col-sm-2 col-form-label">판매여부</label>
					    <div class="col-sm-4">
					      <select id="pdt_buy" name="pdt_buy">
					      	<option value="Y">판매가능</option>
					      	<option value="N">판매불가능</option>
					      </select>
					    </div>
					  </div>	  
				 
     			</div>
     			<div class="box-footer">
     				<div class="form-group">
     					<ul class="uploadedList"></ul>
     				</div>    				
     				<div class="form-group row">
						  <div class="col-sm-12 text-center">
						  	<button type="submit" class="btn btn-dark" id="btnProduct">상품등록</button>
						  </div>			
					</div>
     			</div>
     			</form>
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
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script>

  $(document).ready(function(){

    // ckeditor 환경설정
    var ckeditor_config = {
			resize_enabled : false,
			enterMode : CKEDITOR.ENTER_BR,
			shiftEnterMode : CKEDITOR.ENTER_P,
			toolbarCanCollapse : true,
			removePlugins : "elementspath", 
			filebrowserUploadUrl: '/admin/product/imageUpload' //업로드 탭기능추가 속성
    }

    CKEDITOR.replace("pdt_detail", ckeditor_config);
    
    console.log("버전: " + CKEDITOR.version);

    //1차카테고리 선택
    $("#firstCategory").on("change", function(){

      let firstCategoryCode = $(this).val();

      console.log("1차카테고리코드: " + firstCategoryCode);

      let url = "/admin/product/subCategoryList/" + firstCategoryCode;

      $.getJSON(url, function(subCategoryList){
        /*
        console.log(subCategoryList.length);
        console.log("첫번째 데이타: " + subCategoryList[0].cate_code);
        console.log("첫번째 데이타: " + subCategoryList[0].cate_code_prt);
        console.log("첫번째 데이타: " + subCategoryList[0].cate_name);
        */

        //2차카테고리 태그를 참조.
        let optionStr = "";
        let secondCategory = $("#secondCategory");
        
        secondCategory.find("option").remove(); //기존 카테고리에 의하여 출력되는 요소를 제거
        secondCategory.append("<option value=''>2차카테고리 선택</option>")

        for(let i=0; i<subCategoryList.length; i++){
          optionStr += "<option value='" + subCategoryList[i].cate_code + "'>" + subCategoryList[i].cate_name + "</option>";
        }
        secondCategory.append(optionStr);


      });

    });
    
  //이미지 미리보기
    $("#uploadFile").on("change", function(e){

      let file = e.target.files[0];

      let reader = new FileReader();
      reader.onload = function(e){
        $("#change_img").attr("src", e.target.result);
      }

      reader.readAsDataURL(file);

    });

  });

</script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. -->
</body>
</html>