<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">DocMall</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <!-- 
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
       -->
      <c:forEach items="${mainCategoryList }" var="categoryVO">
      <li class="nav-item dropdown">
        
        <!-- 1차카테고리 -->
        <a class="nav-link dropdown-toggle" href="${categoryVO.cate_code }" role="button" data-toggle="dropdown" aria-expanded="false">
          ${categoryVO.cate_name }
        </a>
        
        <!-- 2차카테고리. 1차카테고리 선택된 next() -->
        <div class="dropdown-menu subCateogory"></div>
      </li>
      </c:forEach>
      <!-- 
      <li class="nav-item">
        <a class="nav-link disabled">Disabled</a>
      </li>
       -->
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>
<script>
  // $(document).ready(function(){  });
  $(function(){

    //1차카테고리 클릭
    $("ul.navbar-nav li.nav-item a").on("click", function(){

      // ajax작업 구문 사용시 이전에 필요한 선택자의 정보를 변수에 미리 저장해서 사용해야 한다.
      let selectedCategory = $(this);
      let url = "/user/product/subCategoryList/" + $(this).attr("href");

      //console.log("2차카테고리: " + url);

      // result : 2차카테고리 정보
      $.getJSON(url, function(result){
		   //2차카테고리 추가작업.

       
       let subCategoryList = selectedCategory.next();
       subCategoryList.children().remove(); // <a>태그 모두제거.

       let subCategoryStr = "";
       for(let i=0; i<result.length; i++){
        
        // 직접주소작업
        //subCategoryStr += "<a class='dropdown-item' href='/product/productList/" + result[i].cate_code + "'>" + result[i].cate_name +  "</a>";
        
        // jQuery 문법을 사용하여, 이벤트 적용을 통한 주소요청작업
        subCategoryStr += "<a class='dropdown-item' href='" + result[i].cate_code + "'>" + result[i].cate_name +  "</a>";
       }

       subCategoryList.append(subCategoryStr);

      });
      
    });

    //2차 카테고리 클릭.
    $("ul.navbar-nav li.nav-item div.subCateogory").on("click", "a", function(e){
      e.preventDefault();  // <a href=""></a> 링크기능제거, <input type="submit"> 전송기능 제거

      let cate_code = $(this).attr("href");
      let cate_name = $(this).html();
      // get방식으로 특수문자 데이터가 서버로 보내질때 문제되는 경우 : 인코딩할 것.  검색어 : mdn url 인코딩
      location.href = "/user/product/productList/" + cate_code + "/" +  encodeURIComponent(cate_name);
    });
  });


</script>