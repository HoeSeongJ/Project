package com.docmall.domain;

import lombok.Data;

@Data
public class OrderDetailVO {

	
	// -주문페이지에서 데이타를 받아오는 방법
	// -장바구니테이블에서 데이타를 처리하는 방법.(진행)
	
	// 주문상세테이블
	// odr_code, pdt_num, odr_amount, odr_price
	private Long odr_code; //db: 시퀀스값 참조. 
	private Integer pdt_num;
	private int odr_amount;
	private int odr_price;  // 상품가격 * 수량
	
}
