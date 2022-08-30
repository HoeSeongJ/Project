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
     				LIST PRODUCT
     			</div>
     			<div class="box-body">
     				<!-- 1)검색폼 -->
     				<form id="searchForm" action="/admin/product/productList" method="get">
					  <select name="type">
						  <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : '' }" />>--</option>
						  <option value="N" <c:out value="${pageMaker.cri.type eq 'N' ? 'selected' : '' }" />>상품명</option>
						  <option value="C" <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : '' }" />>제조사</option>
						  <option value="NC" <c:out value="${pageMaker.cri.type eq 'NC' ? 'selected' : '' }" />>상품명 or 제조사</option>
					  </select>
					  <input type="text" name="keyword" value="${pageMaker.cri.keyword }">
					  <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					  <input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					  <button type="button" id="btnSearch" class="btn btn-link">Search</button>
  					</form>
  <table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col">번호</th>
	      <th scope="col">상품명</th>
	      <th scope="col">가격</th>
	      <th scope="col">등록일</th>
	      <th scope="col">판매여부</th>
	      <th scope="col">수정</th>
	      <th scope="col">삭제</th>
	    </tr>
	  </thead>
	  <tbody>
	    <c:forEach items="${productList }" var="productVO">
	    <!-- BoardVO클래스의 필드명으로 코딩했지만, 호출은 getter메서드가 사용됨. -->
	    <tr>
	      <td scope="row"><c:out value="${productVO.pdt_num }" /></td>
	      <td>
	      	<img src="/admin/product/displayFile?folderName=${productVO.pdt_img_folder }&fileName=s_${productVO.pdt_img }" 
	      		alt="" style="width: 80px;height: 80px;" onerror="this.onerror=null; this.src='/image/no_images.png'">
	      	<a class="move" href="${productVO.pdt_num }"><c:out value="${productVO.pdt_name }" escapeXml="true" /></a>
	      </td>
	      <td><c:out value="${productVO.pdt_price }" /></td>	      
	      <td><fmt:formatDate value="${productVO.pdt_date_sub }" pattern="yyyy-MM-dd hh:mm" /></td>
	      <td><c:out value="${productVO.pdt_buy }" /></td>
	      <td><button type="button" name="btnProductEdit" data-pdt_num="${productVO.pdt_num }" class="btn btn-link">Edit</button></td>
	      <td>
          <input type="hidden" name="pdt_img_folder" value="${productVO.pdt_img_folder }">
          <input type="hidden" name="pdt_img" value="${productVO.pdt_img }">
          <button type="button" name="btnProductDelete" data-pdt_num="${productVO.pdt_num }"  class="btn btn-link">Delete</button>
        </td>
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
		<form id="actionForm" action="/board/list" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
			<input type="hidden" name="type" value="${pageMaker.cri.type}">
			<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
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

    // btnProductDelete
    //2)상품삭제버튼 클릭
    $("button[name='btnProductDelete']").on("click", function(){
      //console.log("상품코드: " + $(this).data("pdt_num"));

      if(!confirm($(this).data("pdt_num") + " 번 상품을 삭제하겠습니까?")) return;

      //상품코드를 자식으로 추가
      actionForm.append("<input type='hidden' name='pdt_num' value='" + $(this).data("pdt_num")   + "'>");
      //날짜폴더추가
      //delete버튼->td->자식선택자 찾기
      let pdt_img_folder = $(this).parent().children("input[name='pdt_img_folder']").val();
      actionForm.append("<input type='hidden' name='pdt_img_folder' value='" + pdt_img_folder   + "'>");

      let pdt_img = $(this).parent().children("input[name='pdt_img']").val();
      actionForm.append("<input type='hidden' name='pdt_img' value='" + pdt_img   + "'>");
   
    

      //파일이름추가


      actionForm.attr("method", "get");
      actionForm.attr("action", "/admin/product/productDelete");
      actionForm.submit();
    });


    //3)페이지번호 클릭
    $("ul.pagination li a.page-link").on("click", function(e){

      e.preventDefault(); // <a>태그의 링크기능 무력화

      let pageNum = $(this).attr("href");

      actionForm.find("input[name='pageNum']").val(pageNum);

      //pageNum 필드는 actionForm에 수동으로 작업되어 있어, 추가하는 것이 아니라, 참조하여 값을 변경한다.

      actionForm.attr("method", "get");
      actionForm.attr("action", "/admin/product/productList");
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