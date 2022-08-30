/* 회원가입 */
//html문서와 내용을 브라우저가 읽고 난 이후에 동작되는 특징
$(document).ready(function(){

	let joinForm = $("#joinForm");

	//회원정보 저장하기
	$("#joinSend").on("click", function(){

		console.log("회원정보 저장하기");

		//유효성 검사작업 해야 함.
		

		joinForm.submit();
	});


	// ID중복체크
	$("#btnIDCheck").on("click", function(){

		if($("#mem_id").val() == "") {
			alert("아이디를 입력하세요");
			$("#mem_id").focus();
			return;
		}

		

	});

});