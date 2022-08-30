<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

    
    <!-- Custom styles for this template -->
    <link href="pricing.css" rel="stylesheet">
  </head>
  <body>
    
<%@include file="/WEB-INF/views/include/header.jsp" %>

<h3>회원가입</h3>

<div class="container">
  <div class="mb-3 text-center">
	  <form id="joinForm" action="join" method="post">
		  <div class="form-group row">
		    <label for="mem_id" class="col-sm-2 col-form-label">아이디</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="mem_id" name="mem_id" placeholder="아이디를  8~15이내로 입력">
		    </div>
		    <div class="col-sm-3">
		      <button type="button" class="form-control btn btn-info" id="btnIDCheck">ID중복체크</button>
		    </div>
		    <label class="col-sm-3 col-form-label" style="display:none;" id="idCheckStatus">중복체크결과</label>
		  </div>
		  <div class="form-group row">
		    <label for="mem_pw" class="col-sm-2 col-form-label" >비밀번호</label>
		    <div class="col-sm-7">
		      <input type="password" class="form-control" id="mem_pw" name="mem_pw" placeholder="비밀번호는 영문,숫자,특수문자를 혼합하여  8~15자리로 입력해주세요.">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="mem_pw_2" class="col-sm-2 col-form-label">비밀번호확인</label>
		    <div class="col-sm-7">
		      <input type="password" class="form-control" id="mem_pw_2">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="mem_name" class="col-sm-2 col-form-label">이름</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="mem_name" name="mem_name">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="mem_nick" class="col-sm-2 col-form-label">닉네임</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="mem_nick" name="mem_nick">
		    </div>
		    <div class="col-sm-3">
		      <button type="button" class="form-control btn btn-info" id="NickCheck">닉네임 중복체크</button>
		    </div>
		    <label class="col-sm-3 col-form-label" style="display:none;" id="NickCheckStatus">중복체크결과</label>
		  </div>
		  <div class="form-group row">
		    <label for="mem_email" class="col-sm-2 col-form-label">이메일주소</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="mem_email" name="mem_email">
		    </div>
		    <div class="col-sm-3">
		      <button type="button" class="form-control btn btn-info" id="btnAuthcode">메일인증요청</button>
		    </div>
		  </div>
		  <div class="form-group row">
		  	<label for="mem_authcode" class="col-sm-2 col-form-label">메일인증코드</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="mem_authcode" name="mem_authcode">
		    </div>
		    <div class="col-sm-3">
		      <button type="button" class="form-control btn btn-info" id="btnConfirmAuthcode">메일인증확인</button>
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="mem_phone" class="col-sm-2 col-form-label">휴대폰 번호</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="mem_phone" name="mem_phone" placeholder=" ( - )를 제외하여 입력해주세요">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="sample2_postcode" class="col-sm-2 col-form-label">우편번호</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="sample2_postcode" name="mem_zipcode" readonly="readonly">
		    </div>
		    <div class="col-sm-3">
		      <input type="button" class="form-control btn btn-info" onclick="sample2_execDaumPostcode()" value="우편번호 찾기">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="sample2_address" class="col-sm-2 col-form-label">주소</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="sample2_address" name="mem_addr" readonly="readonly">
		    </div>
		  </div>
		  <div class="form-group row">
		    <label for="sample2_detailAddress" class="col-sm-2 col-form-label">상세주소</label>
		    <div class="col-sm-7">
		      <input type="text" class="form-control" id="sample2_detailAddress" name="mem_addr_d">
		      <input type="hidden" id="sample2_extraAddress" placeholder="참고항목">
		    </div>
		  </div>
		  <div class="form-group row">
		      <label class="form-check-label col-sm-2" for="mem_accept_e">메일 수신동의</label>
			  <div class="col-sm-10 text-left">
			  	<input class="form-check-input" type="checkbox" id="mem_accept_e" name="mem_accept_e">
			  </div>			
		  </div>
		  <div class="form-group row">
			  <div class="col-sm-12 text-center">
			  	<button type="button" class="btn btn-dark" id="btnJoin">회원가입</button>
			  </div>			
		  </div>
	 </form>
  </div>


  <!--  footer.jsp -->
  <%@include file="/WEB-INF/views/include/footer.jsp" %>
</div>

<%@include file="/WEB-INF/views/include/common.jsp" %>
   
   <!--<script type="text/javascript" src="/js/member/join.js"></script>-->

	<script>

		/* 회원가입 */
	//html문서와 내용을 브라우저가 읽고 난 이후에 동작되는 특징
	$(document).ready(function(){

		let joinForm = $("#joinForm");

		//회원정보 저장하기.   <button type="button" id="btnJoin">
		$("#btnJoin").on("click", function(){

			console.log("회원정보 저장하기");
			
			let kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크

			let num = /[0-9]/g;
			let eng = /[a-z]/ig;
			let spe = /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi;


			//유효성 검사작업 해야 함.
			// 폼입력양식 태그 데이타입력여부, 아이디, 비밀번호 제약조건

			//아이디 중복체크 사용여부.  isIDCheck
			
			if(!isIDCheck) {
				alert("아이디 중복체크를 해야 합니다.");
				return;
			}
			
			//메일인증확인 여부.  isAuthCode
			/*
			if(!isAuthCode) {
				alert("메일인증확인을 해야 합니다.");
				return;
			}
			*/
			
			//비밀번호 유효성 검사 Start------------------------------
	        let mem_pw = $("#mem_pw").val();
	        /*  let num = mem_pw.search(/[0-9]/g);
	         let eng = mem_pw.search(/[a-z]/ig);
	         let spe = mem_pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi); */
	        if(mem_pw.length < 8 || mem_pw.length > 15){
	           alert("비밀번호는 8자리에서 15자리 이내로 입력해주세요.");
	           $("#mem_pw").focus();
	           return;
	           
	        }else if(mem_pw.search(/\s/) != -1){
	           alert("비밀번호는 공백 없이 입력해주세요.");
	           $("#mem_pw").focus();
	           return;
	           
	        }else if(mem_pw.search(num) < 0 || mem_pw.search(eng) < 0 || mem_pw.search(spe) < 0 ){
	           alert("비밀번호는 영문,숫자, 특수문자를 혼합하여 입력해주세요.");
	           $("#mem_pw").focus();
	           return;
	        }
	        
	        //비밀번호 확인 일치
	        let pw_chck = $("#mem_pw_2").val();
	        let pw = $("#mem_pw").val();
	        if(pw != pw_chck){
	           alert("비밀번호 확인이 일치하지 않습니다.");
	           return;
	        }  
	        //비밀번호 유효성 검사 End--------------------------------------
	        
	         let mem_name = $("#mem_name").val();
            //이름 입력 확인
            if(mem_name == "") {
               alert("이름을 입력하세요");
               $("#mem_name").focus();
               return;
            }

	        
	        if(!isNickCheck) {
				alert("닉네임 중복체크를 해야 합니다.");
				return;
			}
	        
	        if(!isAuthCode){
	               alert("메일 인증확인을 해주세요")
	               return;
	        }
	        
	        
	        
                     
            
            
            //주소 유효성검사 Start------------------------
            let mem_addr = $('input[name=mem_addr]').val()
            let mem_addr_d = $('input[name=mem_addr_d]').val()
            if(mem_addr == "") {
               alert("우편번호를 입력하세요");
               return;
            }
            
            if(mem_addr_d == "") {
               alert("상세주소를 입력하세요");
               $("#mem_addr_d").focus();
               return;
            }
            //주소 유효성검사

			joinForm.submit();
		});
		

		let ismem_addr_d = false;
		
		let isIDCheck = false; // ID중복체크 사용여부

		// ID중복체크
		$("#btnIDCheck").on("click", function(){

			if($("#mem_id").val() == "") {
				alert("아이디를 입력하세요");
				$("#mem_id").focus();
				return;
			}

			$.ajax({
				url: '/member/idCheck',
				type: "get",
				dataType: 'text',
				data: { mem_id : $("#mem_id").val()},
				success: function(result) {

					console.log(result);
					$("#idCheckStatus").css({'display':'inline', 'color':'#666666'});
					
					if(result == "yes") {
						$("#idCheckStatus").html("<b>" + $("#mem_id").val() + " 사용 가능 합니다</b>");
						isIDCheck = true;
					}else{
						
						$("#idCheckStatus").html("<b>" + $("#mem_id").val() + " 사용 불가능 합니다</b>");
						isIDCheck = false;
					}
				}
			});

			

		});
		
		
		
		let isNickCheck = false;
		// 닉네임중복체크
		$("#NickCheck").on("click", function(){

			if($("#mem_nick").val() == "") {
				alert("닉네임을 입력하세요");
				$("#mem_nick").focus();
				return;
			}

			$.ajax({
				url: '/member/NickCheck',
				type: "get",
				dataType: 'text',
				data: { mem_nick : $("#mem_nick").val()},
				success: function(result) {

					console.log(result);
					$("#NickCheckStatus").css({'display':'inline', 'color':'#666666'});
					
					if(result == "yes") {
						$("#NickCheckStatus").html("<b>" + $("#mem_nick").val() + " 사용 가능 합니다</b>");
						isNickCheck = true;
					}else{
						
						$("#NickCheckStatus").html("<b>" + $("#mem_nick").val() + " 사용 불가능 합니다</b>");
						isNickCheckk = false;
					}
				}
			});

			

		});
		

		//메일 인증코드 요청
		$("#btnAuthcode").on("click", function(){

			if($("#mem_email").val() == "") {
				alert("메일주소를 입력하세요.");
				return;
			}
			
			let mbrEmlAddr = $("#mem_email").val();
            let exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
            //메일 유효성 검사
            if(mbrEmlAddr.search(exptext) < 0 ){
               alert("이메일형식이 올바르지 않습니다.");
               $("#mem_email").focus();
               return;
            }

			$.ajax({
				url : '/email/send',
				type : 'get',
				dataType : 'text',
				data : {receiveMail : $("#mem_email").val()},
				success : function(result) {
					if(result == "success"){
						alert("메일이 발송되어, 인증코드를 확인바랍니다.");
					}else{
						alert("메일발송이 실패되어, 메일주소 확인 또는 관리자에게 문의바랍니다.");
					}
				}
			});
		});

		let isAuthCode = false; // 메일인증확인 여부
		//메일 인증확인
		$("#btnConfirmAuthcode").on("click", function(){

			let authCode = $("#mem_authcode").val();

			$.ajax({
				url: '/member/confirmAuthCode',
				type: 'post',
				dataType: 'text',
				data: {uAuthCode: authCode},
				success : function(result){
					if(result == "success") {
						alert("인증이 일치합니다.");
						isAuthCode = true;
					}else if(result == "fail") {
						alert("인증이 불일치합니다. \n 아니면 메일인증요청이 다시해주세요.");
						isAuthCode = false;
					}
				}
			});
		});

		
	});


	</script>

   
   <!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
	<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
	<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
	</div>
	
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
	    // 우편번호 찾기 화면을 넣을 element
	    var element_layer = document.getElementById('layer'); //  <div id="layer"> 태그가 현재 실행코드보다 앞에 작성이 되어 있어야 한다.
	
	    function closeDaumPostcode() {
	        // iframe을 넣은 element를 안보이게 한다.
	        // 태그참조변수.style.css속성명령어 = '값';
	        element_layer.style.display = 'none';
	    }
	
	    function sample2_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수
	
	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }
	
	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("sample2_extraAddress").value = extraAddr;
	                
	                } else {
	                    document.getElementById("sample2_extraAddress").value = '';
	                }
	
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('sample2_postcode').value = data.zonecode;
	                document.getElementById("sample2_address").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("sample2_detailAddress").focus();
	
	                // iframe을 넣은 element를 안보이게 한다.
	                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
	                element_layer.style.display = 'none';
	            },
	            width : '100%',
	            height : '100%',
	            maxSuggestItems : 5
	        }).embed(element_layer);
	
	        // iframe을 넣은 element를 보이게 한다.
	        element_layer.style.display = 'block';
	
	        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
	        initLayerPosition();
	    }
	
	    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
	    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
	    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
	    function initLayerPosition(){
	        var width = 300; //우편번호서비스가 들어갈 element의 width
	        var height = 400; //우편번호서비스가 들어갈 element의 height
	        var borderWidth = 5; //샘플에서 사용하는 border의 두께
	
	        // 위에서 선언한 값들을 실제 element에 넣는다.
	        element_layer.style.width = width + 'px';
	        element_layer.style.height = height + 'px';
	        element_layer.style.border = borderWidth + 'px solid';
	        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
	        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
	        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
	    }
	    //aa
	</script> 
  </body>
</html>
    
