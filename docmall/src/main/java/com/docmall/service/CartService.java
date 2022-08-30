package com.docmall.service;

import java.util.List;

import com.docmall.domain.CartVO;
import com.docmall.domain.CartVOList;

public interface CartService {

	void cart_add(CartVO vo);
	
	//장바구니 목록
	List<CartVOList> cart_list(String mem_id);
	
	void cart_amount_update(Long cart_code, int cart_amount);
	
	//장바구니 삭제
	void cart_delete(Long cart_code);
	
	//장바구니 비우기
	void cart_empty(String mem_id);
}
