package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {

	// 주문테이블
	// odr_code, mem_id, odr_name, odr_zipcode, odr_addr, odr_addr_d, odr_phone, odr_total_price, odr_date
	private Long odr_code;  // db : 시퀀스사용
	private String mem_id;  // 세션: 로그인아이디
	private String odr_name; // 입력데이타
	private String odr_zipcode; //입력데이타
	private String odr_addr; //입력데이타
	private String odr_addr_d; //입력데이타
	private String odr_phone; //입력데이타
	private int	odr_total_price; //입력데이타
	private Date odr_date; // db : 기본값. sysdate
	private String odr_message; // 입력데이타
	
	private String odr_status; // 주문상태 : 배송상태
	private String payment_status; // 결제상태 : 입금상태
	private String cs_status; //cs상태 : 교환및환불,취소상태
	
}
