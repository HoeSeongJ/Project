package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentVO {

	// pay_code, odr_code, pay_method, pay_date, pay_tot_price, pay_rest_price, pay_nobank_price, pay_nobank_user, pay_nobank
	private Integer pay_code; // 시퀀스
	private Long odr_code;  // 주문번호
	private String pay_method; // 사용자선택및 입력
	private Date pay_date; // sql: sysdate
	private int pay_tot_price;
	private int pay_rest_price;
	private int pay_nobank_price;
	private String pay_nobank_user;
	private String pay_nobank;
}
