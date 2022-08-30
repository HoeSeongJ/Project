<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>DocMall Shopping</title>

<meta name="theme-color" content="#563d7c">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
	
   
    <%@include file="/WEB-INF/views/include/common.jsp" %>

  </head>
  <body>
    
<%@include file="/WEB-INF/views/include/header.jsp" %>
<!-- 1차카테고리 메뉴 -->
<%@include file="/WEB-INF/views/include/categoryMenu.jsp" %>

<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
  <h1 class="display-4">${cate_name}</h1>
  <p class="lead">Quickly build an effective pricing table for your potential customers with this Bootstrap example. It’s built with default Bootstrap components and utilities with little customization.</p>
</div>

<div class="container">

      <div class="row">
        <c:forEach items="${productList }" var="productVO">
        <div class="col-md-4">
          <div class="card mb-4 shadow-sm">
            <!-- <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg> -->
            <!-- 상품이미지 -->
            <a class="move" href="${productVO.pdt_num }">
            	<img src="/user/product/displayFile?folderName=${productVO.pdt_img_folder }&fileName=s_${productVO.pdt_img }" 
	      			alt="" class="bd-placeholder-img card-img-top" width="100%" height="225" onerror="this.onerror=null; this.src='/image/no_images.png'">
	      	</a>

            <div class="card-body">
              <p class="card-text">
	              ${productVO.pdt_name }<br>
	              ${productVO.pdt_company }<br>
	              <fmt:setLocale value="ko_KR"/><fmt:formatNumber type="currency" value="${productVO.pdt_price }" pattern="###,###,###,###"></fmt:formatNumber>
	              <br>
              </p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" name="btnBuyCart" data-pdt_num="${productVO.pdt_num }" class="btn btn-sm btn-outline-secondary">Buy & Cart</button>                  
                </div>
                <small class="text-muted">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        </c:forEach>
      </div>
      <div class="row">
      	<div class="col-12">
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
				<form id="actionForm" action="" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
					<input type="hidden" name="type" value="${pageMaker.cri.type}">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
					<input type="hidden" name="cate_code" value="${cate_code}">
					<input type="hidden" name="cate_name" value="${cate_name}">
				</form>
			</nav>
      	</div>
      </div>
      
      <%@include file="/WEB-INF/views/include/footer.jsp" %>
    </div>

	<!--  Modal 대화상자 : 상품상세보기 -->
	<div class="modal fade" id="modal_productDetail" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">New message</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <div class="row">
            <div class="col-md-6">
              <img src="" id="modal_detail_image" 
	      		alt="" class="bd-placeholder-img card-img-top" width="100%" height="225" onerror="this.onerror=null; this.src='/image/no_images.png'">
            </div>
            <div class="col-md-6">
              <form>
                <div class="form-group row">
                  <label for="pdt_name" class="col-form-label col-3">상품이름</label>
                  <div class="col=9">
                  	<input type="text" class="form-control" id="pdt_name" readonly>
                  	<input type="hidden" class="form-control" id="pdt_num">
                  </div>
                </div>
                <div class="form-group row">
                  <label for="pdt_price" class="col-form-label col-3">판매가격</label>
                  <div class="col=9">
                  	<input type="text" class="form-control" id="pdt_price" readonly>
                  </div>
                </div>
                <div class="form-group row">
                  <label for="pdt_company" class="col-form-label col-3">제조사</label>
                  <div class="col=9">
                  	<input type="text" class="form-control" id="pdt_company" readonly>
                  </div>
                </div> 
                <div class="form-group row">
                  <label for="pdt_amount" class="col-form-label col-3">수량</label>
                  <div class="col=9">
                  	<input type="number" class="form-control" id="pdt_amount" min="1" value="1">
                  </div>
                </div>              
              </form>
            </div>
          </div>
          
	      </div>
	      <div class="modal-footer">
	        <button type="button" name="btnModalBuy" class="btn btn-primary">BUY IT NOW</button>
	        <button type="button" name="btnModalCart" class="btn btn-primary">ADD TO CART</button>
	      </div>
	    </div>
	  </div>
	</div>

  <script>

    $(function(){

      $("button[name='btnBuyCart']").on("click", function(){

        $("#modal_productDetail").modal('show');

        let url = "/user/product/productDetail/" + $(this).data("pdt_num");
        
        $.getJSON(url, function(result) {

          //모달 대화상자에서 상품상세정보 표시
          //console.log("상품상세정보" + result.pdt_num);
			
          //상품코드
          $("div#modal_productDetail input#pdt_num").val(result.pdt_num);
          //상품이름
          $("div#modal_productDetail input#pdt_name").val(result.pdt_name);
          //판매가격
          $("div#modal_productDetail input#pdt_price").val(result.pdt_price);
          //제조사
          $("div#modal_productDetail input#pdt_company").val(result.pdt_company);
          //상품이미지
          // /user/product/displayFile?folderName=${productVO.pdt_img_folder }&fileName=s_${productVO.pdt_img }
          let url = "/user/product/displayFile?folderName=" + result.pdt_img_folder + "&" + "fileName=" + result.pdt_img;
          
          //console.log("이미지파일경로: " + url);
          $("div#modal_productDetail img#modal_detail_image").attr("src", url);
          

        });

      });

      //장바구니 담기. ajax
      $("button[name='btnModalCart']").on("click", function(){

        $.ajax({
          url : '/user/cart/cart_add',
          data: { pdt_num : $("div#modal_productDetail input#pdt_num").val(), cart_amount : $("div#modal_productDetail input#pdt_amount").val()},
          dataType: 'text',
          beforeSend : function(xmlHttpRequest) {
        		console.log("ajax xmlHttpRequest check"); 
        		xmlHttpRequest.setRequestHeader("AJAX", "true");
          },			
          success: function(result) {
            if(result == "success") {
              alert("장바구니에 추가되었습니다.");
              if(confirm("장바구니로 이동하시겠습니까?")) {
                location.href = "/user/cart/cart_list";
              }

            }
          },
          // ajax호출하여, 스프링에서 respone.sendError(400); 처리시.
          error: function(xhr, status, error) {
        	  
        	  console.log("ajax error");
        	  console.log("status: " + status);
        	  //location.href = "/member/login";
        	 
        	  if(xhr.status == 400) {
        		  
        		  location.href = "/member/login";
        	  }
        	  
          }
        });
      });

      // 직접구매 버튼 클릭시. non-ajax
      $("button[name='btnModalBuy']").on("click", function(){

        let pdt_num = $("div#modal_productDetail input#pdt_num").val(); // 구매상품코드
        let odr_amount = $("div#modal_productDetail input#pdt_amount").val(); // 구매수량

        let url = "/user/order/orderListInfo?pdt_num="+pdt_num+"&odr_amount="+odr_amount+"&type=direct";
        //console.log("직접구매 주소: " + url);
        location.href = url;
        
      });

      
      //actionForm 참조 : 1)페이지번호 클릭 2)검색버튼 클릭
      let actionForm = $("#actionForm");
      
    //3)페이지번호 클릭
      $("ul.pagination li a.page-link").on("click", function(e){

        e.preventDefault(); // <a>태그의 링크기능 무력화

        let pageNum = $(this).attr("href");

        actionForm.find("input[name='pageNum']").val(pageNum);

        //pageNum 필드는 actionForm에 수동으로 작업되어 있어, 추가하는 것이 아니라, 참조하여 값을 변경한다.
        
        let url = "/user/product/productList/${cate_code}/" + encodeURIComponent("${cate_name}");

        actionForm.attr("method", "get");
        actionForm.attr("action", url);
        actionForm.submit();

       
      });

      
      let searchForm = $("#searchForm");
      //검색버튼 클릭시 pageNum 초기화
      $("#btnSearch").on("click", function(){

        searchForm.find("input[name='pageNum']").val(1);
        searchForm.submit();
      });

      //상품이미지, 상품제목 클릭
      $("div.container a.move").on("click", function(e){
        e.preventDefault();

        let pdt_num = $(this).attr("href");

        actionForm.attr("method", "get");
        actionForm.attr("action", "/user/product/productDetail");

        actionForm.find("input[name='pdt_num']").remove();
        

        actionForm.append("<input type='hidden' name='pdt_num' value='" + pdt_num + "'>");
        actionForm.submit();

      });
      
    });  //jquery ready이벤트 끝

  </script>  
  </body>
</html>
    