package com.docmall.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.docmall.kakaopay.ApproveResponse;
import com.docmall.kakaopay.ReadyResponse;

import lombok.extern.log4j.Log4j;

//인터페이스 없이 단독 클래스 구성.

@Log4j
@Service
public class KakaoPayServiceImpl {

	/*
	 카카오 Pay API
  - HttpURLConnection 를 이용한 HTTP통신
  - RestTemplate 를 이용한 작업. 
	 */
	
	
	// 1)카카오 Pay에서 요청하는 정보
	public ReadyResponse payReady(String itemName, int quantity, String mem_id, int totalAmount) {
		
		String order_id = "100";
		//String itemName = "테스트상품"; // 상품이름
		//int quantity = 1;
		
		//카카오 pay가 요청한 결제요청 request정보를 구성한다.
		// 컬렉션의 Map인터페이스의 특징은 key, value 구성.  key 1개당 value 1개라는 특징. ex> map.put("key","value")
		// MultiValueMap : key1개당 여러개의 value를 갖는 구조의 스프링프레임워크에서 제공하는 map.
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("cid", "TC0ONETIME"); // 테스트 가맹점ID
		parameters.add("partner_order_id", order_id); // 가맹점 주문번호.
		parameters.add("partner_user_id", mem_id); // 가맹점 회원Id
		parameters.add("item_name", itemName); // 상품명  체크  예>땡땡땡상품외 2건
		parameters.add("quantity", String.valueOf(quantity)); // 상품수량  체크
		parameters.add("total_amount", String.valueOf(totalAmount)); // 상품총액
		parameters.add("tax_free_amount", "0"); // 상품비과세금액
		parameters.add("approval_url", "http://localhost:9090/user/order/orderApproval");  // 결제요청 성공시 redirect url
		parameters.add("cancel_url", "http://localhost:9090/user/order/orderCancel"); // 결제 최소시 redirect url
		parameters.add("fail_url", "http://localhost:9090/user/order/orderFail"); // 결제 실패시 redirect url
		
		
		// HttpEntity<T> 클래스 : HttpHeader와HttpBody를 포함하는 클래스.
		// HttpEntity<T> 클래스를 상속받아 구현한 클래스가 있다. RequestEntity,ResponseEntity
		// ResponseEntity 클래스 : HttpHeader, HttpBody, HttpStatus 3가지정보를 관리한다.
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		
		// RestTemplate?Spring 3.0에서 지원. http통신에 사용하는 기능을 제공한다.
		//Http서버와 통신을 단순화하고, Restfull 원칙을 지킨다.
		// 특징? 기계적이고, 반복적인 코드를 최대한 줄여줌. Restful형식, json or xml 형태로 응답을 받아, 쉽게 사용이 가능.
		
		//외부 URL통신
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";
		
		// 첫번째요청
		//메인(핵심).  카카오페이에서 응답해준 json데이타를 객체로 변환해서 받음.
		ReadyResponse readyResponse = template.postForObject(url, requestEntity, ReadyResponse.class);
		
		return readyResponse;
	}
	
	
	// 두번째 요청 : 결제 승인요청 메서드
	public ApproveResponse payApprove(String tid, String pgToken, String mem_id) {
		
		log.info("tid: " + tid);
		log.info("pgToken:" + pgToken);
		
		
		String order_id = "100";
		
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("cid", "TC0ONETIME"); // 테스트 가맹점ID
		parameters.add("tid", tid); // 카카오 페이에서 보내준 결재고유 ID
		parameters.add("partner_order_id", order_id); // 주문번호
		parameters.add("partner_user_id", mem_id); // 주문자
		parameters.add("pg_token", pgToken); // 
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		
		//외부 URL통신
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";
		
		// 두번째요청(결제승인)
		//메인(핵심).  카카오페이에서 응답해준 json데이타를 객체로 변환해서 받음.
		ApproveResponse readyResponse = template.postForObject(url, requestEntity, ApproveResponse.class);
		
		return readyResponse;
		
		
	}
	
	// Kakao Pay request의 두번사용시 공통 Header정보 설정.
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK d570f84ebeb8c1e9bb0935be388d1dcc");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		return headers;
	}
	
}
