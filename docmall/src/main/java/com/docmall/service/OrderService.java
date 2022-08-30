package com.docmall.service;

import java.util.List;

import com.docmall.domain.CartOrderInfo;
import com.docmall.domain.OrderVO;
import com.docmall.domain.PaymentVO;

public interface OrderService {

	List<CartOrderInfo> cartOrderList(String mem_id); 
	
	List<CartOrderInfo> directOrderList(Integer pdt_num, int odr_amount);
	
	void orderbuy(OrderVO vo, PaymentVO p_vo);
}
