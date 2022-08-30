package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data // getter,setter,toString 메서드 자동생성
public class MemberVO {

	/* 테이블컬럼 : mem_id, mem_name, mem_pw, mem_email, mem_zipcode, mem_addr, mem_addr_d, 
	mem_phone, mem_nick, mem_accept_e, mem_point, mem_date_sub, mem_date_up, mem_date_last, 
	mem_authcode
	 
	 */
	
	private String mem_id;
	private String mem_name;
	private String mem_pw;
	private String mem_email;
	private String mem_zipcode;
	private String mem_addr;
	private String mem_addr_d;
	private String mem_phone;
	private String mem_nick;
	private String mem_accept_e; // 메일수신여부
	private int mem_point;  // 포인트적립
	private Date mem_date_sub; // 등록날짜
	private Date mem_date_up;  // 수정날짜
	private Date mem_date_last; // 최신 로그인날짜
	private String mem_authcode; // 인증
	
}
