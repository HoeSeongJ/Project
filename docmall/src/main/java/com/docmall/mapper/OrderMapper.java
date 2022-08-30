package com.docmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.domain.CartOrderInfo;
import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;

public interface OrderMapper {

	//장바구니 주문목록 : 장바구니 테이블의 상품여러개.
	List<CartOrderInfo> cartOrderList(String mem_id);
	
	//직접 주문목록 : 상품 1개
	List<CartOrderInfo> directOrderList(@Param("pdt_num") Integer pdt_num, @Param("odr_amount") int odr_amount);
	
	//주문하기 기능 : 주문테이블, 주문상세테이블, 장바구니테이블
	// 1)주문테이블 저장하기
	void orderSave(OrderVO vo); // odr_code 필드가 시퀀스의 값이 채워진다.
	
	//2)주문상세테이블 저장하기.
	void orderDetailSave(@Param("odr_code") Long odr_code, @Param("mem_id") String mem_id);
	
	//3)결제정보 저장하기
	void paymentSave(PaymentVO p_vo);
	
}
